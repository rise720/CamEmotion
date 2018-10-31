package com.cac.CamEmotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.model.CameraReadyModel;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.model.FaceIdentificationConfigExtend;
import com.cac.CamEmotion.model.SystemConfig;
import com.cac.CamEmotion.service.CameraReadyCheckService;

@Service
public class CameraReadyCheckServiceImpl implements CameraReadyCheckService {
	@Resource
	private CurriculumTblMapper curriculumDao;
	
	private Logger logger = LogManager.getLogger(CameraReadyCheckServiceImpl.class);
	private static List<CameraReadyModel> listCameraReadyModel = new ArrayList<CameraReadyModel>();

	@Override
	public void PushCameraReadyModel(CameraReadyModel model) {
		//初始化设置为上课状态 0
		SendCameraReadyInfo(model, "0");
		synchronized(listCameraReadyModel){
			listCameraReadyModel.add(model);
			Constants.LISTCAMERAREADYMODEL.add(model);
		}
	}

	@Override
	public void CheckCameraReady() {
		synchronized (listCameraReadyModel) {
			if (listCameraReadyModel.size() > 0) {
				for (int i = 0; i < listCameraReadyModel.size(); i++) {
					boolean res = CheckCameraReady(listCameraReadyModel.get(i));
					if (res) {
						listCameraReadyModel.remove(i);
						i--;
					}
				}
			}
		}
	}
	
	private boolean CheckCameraReady(CameraReadyModel model) {

		List<FaceAnalysisConfigExtend> hostList = model.getHostList();
		SystemConfig systemConfig = model.getSystemConfig();
		// 检查所有主机是否已不是 “未准备完毕” 0 状态
		for (FaceAnalysisConfigExtend faceAnalysisConfigExtend : hostList) {
			String key = GetKey(model.getCurriculumId(), faceAnalysisConfigExtend.getCameraip(), systemConfig.getCamerareadykey());
			Object obj = faceAnalysisConfigExtend.getMemcacheUtil().getKey(key);
			if (obj != null && obj instanceof String) {
				if (Integer.parseInt(obj.toString().trim()) == 0)
					return false;
			} else {
				return false;
			}
		}
		// 全部准备好后，设置为上课状态 2
		if (!SendCameraReadyInfo(model, "2")) {
			return false;
		}
		
		// 更新上课时间
		try {
			logger.info("------------更新上课时间-----------------" + model.getCurriculumId());
			Constants.isInClassing = true;
			CurriculumTbl curriculumTbl = new CurriculumTbl();
			//
			curriculumTbl.setId(model.getCurriculumId());
			curriculumTbl.setStarttime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
			curriculumTbl.setClassstatus(0);
			curriculumDao.updateByClassStatus(curriculumTbl);
		} catch (Exception e) {
			logger.error("更新上课时间异常：" + e);
		}
		return true;
	}
	
	/***
	 * 获取拼接后的Memcached key
	 * @param curriculunId 课程Id
	 * @param cameraIp 摄像头Ip
	 * @param key 
	 * @return
	 */
	private String GetKey(Integer curriculunId, String cameraIp, String key) {
		String keys = key + "_" + curriculunId + "_" + cameraIp;
		return keys;
	}

	/**
	 * 向Memcached发送摄像机准备状态
	 * 
	 * 方法功能说明: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年7月31日
	 * @param model
	 * @param mValue
	 * @return
	 *
	 */
	private boolean SendCameraReadyInfo(CameraReadyModel model, String mValue) {
		for (FaceAnalysisConfigExtend faceAnalysisConfigExtend : model.getHostList()) {
			String key = GetKey(model.getCurriculumId(), faceAnalysisConfigExtend.getCameraip(), model.getSystemConfig().getCamerareadykey());
			if (!faceAnalysisConfigExtend.getMemcacheUtil().setKey(key, 60 * 60 * 24, mValue)) {
				logger.error("向Ip地址为：" + faceAnalysisConfigExtend.getHostip() + " 的主机发送摄像机准备状态为" + mValue + "失败");
				if (mValue.equals("2")) {
					return false;
				}
			}
			for (FaceIdentificationConfigExtend faceIdentificationConfigExtend : faceAnalysisConfigExtend.getListFaceIdentificationConfig()) {
				if (!faceIdentificationConfigExtend.getMemcacheUtil().setKey(key, 60 * 60 * 24, mValue)) {
					logger.error("向Ip地址为：" + faceAnalysisConfigExtend.getHostip() + " 的主机发送摄像机准备状态为" + mValue + "失败");
					if (mValue.equals("2")) {
						return false;
					}
				}
			}
		}
		return true;
	}
	

}
