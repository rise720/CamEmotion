package com.cac.CamEmotion.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.model.FaceIdentificationConfig;
import com.cac.CamEmotion.model.SystemConfig;
import com.cac.CamEmotion.service.CameraService;
import com.cac.CamEmotion.service.EmotionServerClient;
import com.cac.CamEmotion.service.FaceAnalysisConfigService;
import com.cac.CamEmotion.service.FaceIdentificationConfigService;

/**
 * 
 * 类功能说明: 对参数配置信息的控制操作
 * <P>
 * 
 * </P>
 *
 * @author jinwh
 * @data 2018年1月9日
 */
@RestController
@RequestMapping("/rest/SettingData")
public class SettingController {
	private Logger logger = LogManager.getLogger(SettingController.class);

	@Resource
	private FaceAnalysisConfigService faceAnalysisConfigService;
	@Resource
	private FaceIdentificationConfigService faceIdentificationConfigService;

	@Resource
	private CameraService cameraService;

	@Resource
	private EmotionServerClient emotionServerClient;

	/**
	 * 
	 * 方法功能说明: 查询
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2018年1月9日
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getSettingInfo", method = RequestMethod.POST)
	public Response<List<FaceAnalysisConfigExtend>> getSettingInfo(HttpServletRequest request) {
		List<FaceAnalysisConfigExtend> faceAnalysisConfigExtendList = emotionServerClient.GetFaceAnalysisConfigList();
		return new Response<List<FaceAnalysisConfigExtend>>().success(faceAnalysisConfigExtendList);
	}

	/**
	 * 
	 * 方法功能说明: 新增及修改
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2018年1月9日
	 * @param request
	 * @param `
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/saveSettingInfo", method = RequestMethod.POST)
	public Response<String> saveSettingInfo(HttpServletRequest request, @RequestBody FaceAnalysisConfigExtend faceAnalysisConfigExtend) {
		try {
			if (faceAnalysisConfigExtend == null)
				return new Response<String>().failure("缺少保存对象！");
			
			faceAnalysisConfigExtend.setClassroomId(1);
			SystemConfig sysConfig = emotionServerClient.GetSystemConfig();
//			String shareAddr = "\\\\" + faceAnalysisConfigExtend.getHostip() + "\\" + sysConfig.getSharename();
			String shareAddr = "\\\\" + sysConfig.getWebip() + "\\" + sysConfig.getSharename();
			faceAnalysisConfigExtend.setShareaddr(shareAddr);

			int res = emotionServerClient.CheckConfigOperation(faceAnalysisConfigExtend);
			switch (res) {
			case 1:
				return new Response<String>().failure("该教室正在上课");
			case 2:
				return new Response<String>().failure("该摄像头正在上课");
			}
			if (!emotionServerClient.checkConfirmUpdateHost(faceAnalysisConfigExtend))
				return new Response<String>().failure("保存异常，主机名称或摄像机Ip地址重复！");
			String error_msg = "";
			int row = 0;
			int saveType = 0;
			// 根据hostNo判断保存类型。 为0的是新增，非0的是修改
			if (faceAnalysisConfigExtend.getHostno() == 0) {
				row = faceAnalysisConfigService.insertSelective(faceAnalysisConfigExtend);
				error_msg = "新增数据失败";
			} else {
				row = faceAnalysisConfigService.updateByPrimaryKeySelective(faceAnalysisConfigExtend);
				error_msg = "更新失败，请确认该条数据是否存在";
				saveType = 1;
			}
			if (row == 0) {
				return new Response<String>().failure(error_msg);
			}
			// 保存特征提取服务器配置
			row = SaveFaceIdentificationConfig(faceAnalysisConfigExtend, saveType);
			if (row <= 0) {
				return new Response<String>().failure("保存特征提取服务器配置失败");
			}
			// 更新主机配置
			boolean result = emotionServerClient.initHostData();
			if(result)
				return new Response<String>().success(String.valueOf(row));
			else
				return new Response<String>().failure("保存成功,更新配置失败");

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<String>().failure("保存失败");
		}
	}

	/**
	 * 
	 * 方法功能说明: 删除
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2018年1月9日
	 * @param request
	 * @param systemConfig
	 * @return
	 */
	@RequestMapping(value = "/deleteSettingInfo", method = RequestMethod.POST)
	public Response<String> deleteSettingInfo(HttpServletRequest request, @RequestBody FaceAnalysisConfigExtend faceAnalysisConfigExtend) {
		if (faceAnalysisConfigExtend == null || faceAnalysisConfigExtend.getHostno() == null || faceAnalysisConfigExtend.getHostno() <= 0)
			return new Response<String>().failure("缺少删除对象！");
		faceAnalysisConfigExtend = emotionServerClient.GetFaceAnalysisConfig(faceAnalysisConfigExtend.getHostno());
		if (faceAnalysisConfigExtend == null) {
			return new Response<String>().failure("未找到该配置");
		}
		int res = emotionServerClient.CheckConfigOperation(faceAnalysisConfigExtend);
		switch (res) {
		case 1:
			return new Response<String>().failure("该教室正在上课");
		case 2:
			return new Response<String>().failure("该摄像头正在上课");
		}
		int row = faceAnalysisConfigService.deleteByPrimaryKey(faceAnalysisConfigExtend.getHostno());
		if (row == 0) {
			return new Response<String>().failure("请确认该条数据是否存在");
		}
		
		//删除特征提取服务器配置
		row = faceIdentificationConfigService.deleteByPrimaryAnalysisNo(faceAnalysisConfigExtend.getHostno());
		if (row == 0) {
			return new Response<String>().failure("删除特征提取服务器配置失败");
		}
		
		// 更新主机配置
		boolean result = emotionServerClient.initHostData();
		if(result)
			return new Response<String>().success(String.valueOf(row));
		else
			return new Response<String>().failure("删除成功,更新配置失败");
	}

	/**
	 * 
	 * 方法功能说明: 配置检测
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author jinwh
	 * @date 2018年1月9日
	 * @param request
	 * @param systemConfig
	 * @return
	 */
	@RequestMapping(value = "/testConnect", method = RequestMethod.POST)
	public Response<String> testConnect(HttpServletRequest request, @RequestBody FaceAnalysisConfigExtend faceAnalysisConfigExtend) {
		if (faceAnalysisConfigExtend == null)
			return new Response<String>().failure("缺少删除对象！");
		try {
			// 摄像头连接检查
			if (!InetAddress.getByName(faceAnalysisConfigExtend.getCameraip()).isReachable(2000))
				return new Response<String>().failure("摄像头连接失败");
			// 主机连接检查
			if (!InetAddress.getByName(faceAnalysisConfigExtend.getHostip()).isReachable(2000))
				return new Response<String>().failure("主机连接失败");
		} catch (IOException e) {
			return new Response<String>().failure("Ping主机IO错误！");
		}
		// 主码流连接检查
		if (!cameraService.isConnect(faceAnalysisConfigExtend.getCamaddrm()))
			return new Response<String>().failure("主码流连接失败");
		// 辅码流连接检查
		if (!cameraService.isConnect(faceAnalysisConfigExtend.getCamaddra()))
			return new Response<String>().failure("辅码流连接失败");
		return new Response<String>().success("成功");
	}

	/**
	 * 保存特征提取服务器配置
	 * @param model 分析服务器配置
	 * @param saveType 保存类型 0新增 1修
	 * @return 影响条数
	 */
	private int SaveFaceIdentificationConfig(FaceAnalysisConfigExtend model, int saveType) {
		FaceIdentificationConfig faceIdentificationConfig = new FaceIdentificationConfig();
		faceIdentificationConfig.setHostname(model.getHostname() + "_人脸识别");
		if (model.getIdentificationFlag() == null || model.getIdentificationFlag() == 0) {
			faceIdentificationConfig.setHostip(model.getHostip());
		} else {
			faceIdentificationConfig.setHostip(Constants.SYSTEM_IDEBTIFICATION);
		}
		faceIdentificationConfig.setKmeanorfaceid(Constants.SYSTEM_KMEABORFACEID);
		faceIdentificationConfig.setAnalysisNo(model.getHostno());
		faceIdentificationConfig.setAnalysisIp(model.getHostip());
		int resRow = 0;
		if (saveType == 0) {
			resRow = faceIdentificationConfigService.insertSelective(faceIdentificationConfig);
		} else {
			resRow = faceIdentificationConfigService.updateByPrimaryAnalysisNoSelective(faceIdentificationConfig);
		}
		return resRow;
	}

}