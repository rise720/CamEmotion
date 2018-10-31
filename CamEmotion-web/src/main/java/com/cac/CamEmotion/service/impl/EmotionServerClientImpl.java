package com.cac.CamEmotion.service.impl;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.dao.CurriculumTblMapper;
import com.cac.CamEmotion.dao.FaceAnalysisConfigMapper;
import com.cac.CamEmotion.dao.FaceContrastConfigMapper;
import com.cac.CamEmotion.dao.FaceIdentificationConfigMapper;
import com.cac.CamEmotion.dao.SystemConfigMapper;
import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.exception.BIException;
import com.cac.CamEmotion.exception.SysException;
import com.cac.CamEmotion.jsonModel.ClassnoModel;
import com.cac.CamEmotion.jsonModel.MemcacheStatus;
import com.cac.CamEmotion.model.CameraReadyModel;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.FaceAnalysisConfig;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.model.FaceContrastConfigExtend;
import com.cac.CamEmotion.model.FaceIdentificationConfigExtend;
import com.cac.CamEmotion.model.SystemConfig;
import com.cac.CamEmotion.service.CameraReadyCheckService;
import com.cac.CamEmotion.service.CurriculumService;
import com.cac.CamEmotion.service.EmotionServerClient;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.cac.CamEmotion.util.IpUtil;
import com.cac.CamEmotion.util.LoadProperties;
import com.cac.CamEmotion.util.MemcacheUtil;
import com.google.gson.Gson;

@Service
public class EmotionServerClientImpl implements EmotionServerClient {

	private static Logger logger = LogManager.getLogger(EmotionServerClientImpl.class);
	private static Gson gson = new Gson();

	@Resource
	private SystemConfigMapper systemConfigDao;
	@Resource
	private FaceContrastConfigMapper faceContrastConfigDao;
	@Resource
	private FaceAnalysisConfigMapper faceAnalysisConfigDao;	
	@Resource
	private FaceIdentificationConfigMapper faceIdentificationConfigDao;
	@Resource
	private CurriculumTblMapper curriculumDao;
	@Resource
	private CurriculumService curriculumService;
	@Resource
	private EmotionalDataService emotionalDataService;
	@Resource
	private CameraReadyCheckService cameraReadyCheckService;

	/**
	 * 基础配置类
	 */
	public SystemConfig systemConfig;
	/**
	 * 所有主机列表
	 */
	private List<FaceAnalysisConfigExtend> hostList = new ArrayList<>();
	/**
	 * 所有对比服务器列表
	 */
	public List<FaceContrastConfigExtend> faceContrastConfigList = new ArrayList<>();
	
	/**
	 * 主机列表 key：课程Id value：主机列表
	 */
	private Map<Integer, List<FaceAnalysisConfigExtend>> hostListMap = new HashMap<>();
	/**
	 * 课程上下课状态 key：课程Id value：教室Id|上下课状态
	 */
	private Map<Integer, String> curriculumStateMap = new HashMap<>();
	
	/**
	 * c端请求的时间 key:对比服务器Ip value：c端最新请求时间戳
	 */
	private Map<String, Long> currentTimeMap = new HashMap<>();	
	
	//摄像机像素
	private Map<String, List<Integer>> pixelMap = new HashMap<>();

	
	/**
	 * 
	 * 方法说明: 服务初始化
	 * <P>
	 * 
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年9月1日
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	@PostConstruct
	private void init() throws MalformedURLException, RemoteException, NotBoundException {
		
		Constants.isInClassing = false;
		
		Constants.DOWNLOAD_PATH = LoadProperties.getText("download.path"); // 资源下载地址

		Constants.DOWNLOAD_TEMP = LoadProperties.getText("download.temp"); // 资源下载临时地址

		Constants.SYSTEM_RESOURCE_PATH = LoadProperties.getText("system.resource.path"); // 系统资源文件地址

		Constants.UPLOAD_PATH = LoadProperties.getText("upload.path"); // 素材上传地址

		// 获取tomcat所在的绝对地址
		String systemp = System.getProperty("user.dir").replace("bin", "");
		
		Constants.PROJECT_PATH = systemp;
		logger.info("-----------PROJECT_PATH:" + Constants.PROJECT_PATH);
		
		File file = null;
		Constants.SYSTEM_RESOURCE_PATH = systemp + "" + Constants.SYSTEM_RESOURCE_PATH;
		file = new File(Constants.SYSTEM_RESOURCE_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.info("-----------SYSTEM_RESOURCE_PATH:" + Constants.SYSTEM_RESOURCE_PATH);

		Constants.DOWNLOAD_PATH = systemp + Constants.DOWNLOAD_PATH;
		file = new File(Constants.DOWNLOAD_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.info("-----------DOWNLOAD_PATH:" + Constants.DOWNLOAD_PATH);

		Constants.DOWNLOAD_TEMP = systemp + Constants.DOWNLOAD_TEMP;
		file = new File(Constants.DOWNLOAD_TEMP);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.info("-----------DOWNLOAD_TEMP:" + Constants.DOWNLOAD_TEMP);

		Constants.UPLOAD_PATH = systemp + Constants.UPLOAD_PATH;
		file = new File(Constants.UPLOAD_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.info("-----------UPLOAD_PATH:" + Constants.UPLOAD_PATH);

		try {
//			String webIp = IpUtil.getLocalIP();
			systemConfig = systemConfigDao.selectSystemConfig();
			if (systemConfig == null) {
//				logger.error("获取Ip：" + webIp + " SystemConfig表中数据有误！");
				 logger.error("获取SystemConfig表中数据有误！");
				return;
			}
			logger.info("-----------SystemConfig INIT");
			InitFaceContrastConfigList();
			logger.info("-----------FaceContrastConfigList INIT");
			initHostData();
			logger.info("-----------FaceAnalysisConfigList INIT");

			// 查询所有已经在上课的课程信息
			List<CurriculumTbl> lessoningList = curriculumDao.selectInClassInfo(0);
			for (CurriculumTbl curriculumTbl : lessoningList) {
				curriculumTbl.setClassroomId(1);
				List<FaceAnalysisConfigExtend> list = GetConfigListByClassroomId(curriculumTbl.getClassroomId());
				// 保存已经在上课的课程信息
				SaveCurriculumInfo(curriculumTbl, list);
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	@Override
	public ClassnoModel start(CurriculumTbl curriculum) {
		
		ClassnoModel classnoModel = new ClassnoModel();
		List<FaceAnalysisConfigExtend> listFaceAnalysisConfigExtend = GetConfigListByClassroomId(curriculum.getClassroomId());
		if (listFaceAnalysisConfigExtend == null || listFaceAnalysisConfigExtend.size() <= 0) {
			classnoModel.setStatus(7);
			classnoModel.setLog("未配置摄像头，不允许上课！");
			return classnoModel;
		}
		try {
			// 检查时间是否同步
			if (!checkTimeSynchronization(listFaceAnalysisConfigExtend)) {
				classnoModel.setStatus(1);
				classnoModel.setLog("主机时间与服务器时间不同步，请调整主机时间！");
				return classnoModel;
			}
			// 检查共享盘符是否可以访问
			if (!CheckShareDir(listFaceAnalysisConfigExtend)) {
				classnoModel.setStatus(2);
				classnoModel.setLog("主机不能访问视频存放目录，请确认网络连接及目录设置是否正确！");
				return classnoModel;
			}
			
			//检测该教室是否已经在上课
			boolean res = CheckClassroom(curriculum.getClassroomId());
			if (!res) {
				classnoModel.setStatus(3);
				classnoModel.setLog("开课失败，该教室还未下课！");
				return classnoModel;
			}
			if(curriculum.getId() == 0){
				// 新增课程
				curriculum = curriculumService.newCurriculum(curriculum);

				// 检查是否可以开课
				if (curriculum == null || curriculum.getId() == 0) {
					classnoModel.setStatus(3);
					classnoModel.setLog("开课失败，保存课程信息失败！");
					return classnoModel;
				}
			}else if(curriculum.getClassstatus() == 2){
				CurriculumTbl cTbl = new CurriculumTbl();
				cTbl.setId(curriculum.getId());
				cTbl.setStarttime(DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD_HH_MM_SS));
				cTbl.setClassstatus(0);
				
				curriculumService.updateCurriculum(cTbl);
			}
			
			
			//修改配置表的课程id
			FaceAnalysisConfig faceAnalysisConfig = new FaceAnalysisConfig();
			faceAnalysisConfig.setClassroomId(curriculum.getClassroomId());
			faceAnalysisConfig.setCourseId(curriculum.getId());
			int updateCount = faceAnalysisConfigDao.updateByPrimaryClassroomId(faceAnalysisConfig);
			if (updateCount <= 0 || updateCount != listFaceAnalysisConfigExtend.size()) {
				classnoModel.setStatus(3);
				classnoModel.setLog("开课失败，更新配置表失败！");
				return classnoModel;
			}

			// 向判断摄像机是否准备完毕线程发送机器信息
			CameraReadyModel model = new CameraReadyModel();
			model.setHostList(listFaceAnalysisConfigExtend);
			model.setSystemConfig(systemConfig);
			model.setCurriculumId(curriculum.getId());
			cameraReadyCheckService.PushCameraReadyModel(model);

			// 保存已经在上课的课程信息
			SaveCurriculumInfo(curriculum, listFaceAnalysisConfigExtend);
			
			// 通知C端上课
			res = SendCurriculumInfo(listFaceAnalysisConfigExtend);
			if (!res) {
				classnoModel.setStatus(4);
				classnoModel.setLog("访问主机异常，请确认网络连接是否正常！");
				return classnoModel;
			}
			
			classnoModel.setStatus(0);
			classnoModel.setCurriculum(curriculum);			
		} catch (BIException e) {
			logger.error(e);
			classnoModel.setStatus(5);
			classnoModel.setLog(e.getErrCode());
		} catch (SysException e) {
			logger.error(e);
			classnoModel.setStatus(5);
			classnoModel.setLog(e.getMessage());
		} catch (Exception e) {
			logger.error(e);
			classnoModel.setStatus(6);
			classnoModel.setLog(e.getMessage());
		}
		return classnoModel;
	}

	@Override
	public boolean stop(Integer courseId) {
		List<FaceAnalysisConfigExtend> list = hostListMap.get(courseId);
		synchronized (curriculumStateMap) {
			String value = curriculumStateMap.get(courseId);
			value = value.substring(0, value.length() - 1) + "0";
			curriculumStateMap.put(courseId, value);
			
			Constants.LISTCAMERAREADYMODEL.clear();
		}
		// 通知C端下课
		try {
			return SendCurriculumInfo(list);
		} catch (BIException e) {
			logger.error(e);
			return false;
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
	}

	@Override
	public List<MemcacheStatus> checkMemcacheStatus(CurriculumTbl curriculumTbl) {
		List<FaceAnalysisConfigExtend> list = hostListMap.get(curriculumTbl.getId());
		List<MemcacheStatus> mStatusList = new ArrayList<>();
		try {
			if(list == null || list.size() <= 0 ){
				return null;
			}
			for (FaceAnalysisConfigExtend host : list) {
				MemcacheStatus mStatus = new MemcacheStatus();
				mStatus.setProcessType(mStatus.GetProcessType(host));
				mStatus.setHostName(host.getHostname());
				mStatus.setHostIp(host.getHostip());
				mStatus.setDatetime(DateUtil.getTime(new Date()));
				String key = GetKey(curriculumTbl.getId(), host.getCameraip(), systemConfig.getCamerareadykey());
				Object cameraready = host.getMemcacheUtil().getKey(key);
				
				if (cameraready == null) {
					List<String> logs = new ArrayList<>();
					logs.add("获取主机:" + host.getHostname() + "准备状态异常");
					mStatus.setLogList(logs);
					// 状态置为1
					mStatus.setStatus(1);
					mStatusList.add(mStatus);
					continue;
				}

				if (Integer.parseInt(cameraready.toString().trim()) != 2) {
					// 设备准备中
					List<String> logs = new ArrayList<>();
					logs.add("主机:" + host.getHostname() + "设备准备中...");
					mStatus.setLogList(logs);
					// 状态置为99
					mStatus.setStatus(-1);
					mStatusList.add(mStatus);
					continue;
				}
					
				// 循环主机列表，检查各主机进程状态，有异常则提示错误信息
				String analysismainkey = GetKey(curriculumTbl.getId(), host.getCameraip(), systemConfig.getAnalysismainkey());
				List<String> logList = checkStatusString(host.getHostname(), host.getMemcacheUtil().getKey(analysismainkey), 0);
				for (FaceIdentificationConfigExtend faceIdentificationConfigExtend : host.getListFaceIdentificationConfig()) {
					logList.addAll(checkStatusString(faceIdentificationConfigExtend.getHostname(), faceIdentificationConfigExtend.getMemcacheUtil().getKey(analysismainkey), 1));
				}
				
				mStatus.setLogList(logList);
				
				// 如果有错误信息，则状态为异常1，否则为正常0
				for (String log : mStatus.getLogList()) {
					if (log.indexOf("准备中") != -1) {
						mStatus.setStatus(-1);
						logList.clear();
						logList.add(log);
						mStatus.setLogList(logList);
						break;
					}
				}
				if (mStatus.getStatus() != -1) {
					mStatus.setStatus(mStatus.getLogList().size() > 0 ? 1 : 0);
				}
				mStatusList.add(mStatus);
			}
			
			//对比服务监控
//			List<MemcacheStatus> mStatusLists = GetContrastStatus();
//			mStatusList.addAll(mStatusLists);
			
		} catch (Exception e) {
			logger.error("获取memcache异常");
			logger.error(e);
		}
		return mStatusList;
	}

	@Override
	public boolean initHostData() {
		try { 			
			InitFaceAnalysisConfigList();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean checkConfirmUpdateHost(FaceAnalysisConfigExtend systemConfig) {
		for (FaceAnalysisConfigExtend host : hostList) {
			if (host.getHostno() == systemConfig.getHostno())
				continue;
			if (host.getHostname().equals(systemConfig.getHostname()))
				return false;
			if (host.getCameraip().equals(systemConfig.getCameraip()))
				return false;
		}
		return true;
	}
	
	@Override
	public int CheckConfigOperation(FaceAnalysisConfigExtend model) {
		synchronized (hostListMap) {
			Iterator<Map.Entry<Integer, List<FaceAnalysisConfigExtend>>> it = hostListMap.entrySet().iterator();
			while (it.hasNext()) {
				List<FaceAnalysisConfigExtend> list = it.next().getValue();
				for (FaceAnalysisConfigExtend faceAnalysisConfigExtend : list) {
					if (model.getCameraip().equals(faceAnalysisConfigExtend.getCameraip())) {
						return 1;
					}
					if (model.getClassroomId() == faceAnalysisConfigExtend.getClassroomId()) {
						return 2;
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public List<FaceAnalysisConfigExtend> GetFaceAnalysisConfigList() {
		synchronized (hostList) {
			return hostList;
		}
	}
	
	@Override
	public FaceAnalysisConfigExtend GetFaceAnalysisConfig(int hostNo) {
		synchronized (hostList) {
			for (FaceAnalysisConfigExtend host : hostList) {
				if (host.getHostno() == hostNo) {
					return host;
				}
			}
			return null;
		}
	}
	
	@Override
	public boolean CloseCourse(Integer courseId) {
		try {
			synchronized (curriculumStateMap) {
				curriculumStateMap.remove(courseId);
			}
			synchronized (hostListMap) {
				hostListMap.remove(courseId);
			}
			emotionalDataService.getEmojiReportData(new EmojiQueryModel(0, courseId, 0));
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	@Override
	public boolean ContrastStatus(String contrastIp, Long currentTime) {
		try {
			synchronized (currentTimeMap) {
				currentTimeMap.put(contrastIp, currentTime);
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}

	@Override
	public SystemConfig GetSystemConfig() {
		return systemConfig;
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
	 * 获取对比服务器健康状态
	 */
	private List<MemcacheStatus> GetContrastStatus(){
		List<MemcacheStatus> lists = new ArrayList<>();
		int sleep = 15; //对比服务器健康性检测允许等待时间 单位秒
		try {
			long newCurrentTime = new Date().getTime(); 
			synchronized (faceContrastConfigList) {
				for (FaceContrastConfigExtend faceContrastConfigExtend : faceContrastConfigList) {
					Long currentTime = null;
					synchronized (currentTimeMap) {
						currentTime = currentTimeMap.get(faceContrastConfigExtend.getHostip());
					}
					currentTime = currentTime == null ? -1L : currentTime;
					MemcacheStatus m = new MemcacheStatus();
					m.setHostIp(faceContrastConfigExtend.getHostip());
					m.setHostName(faceContrastConfigExtend.getHostname());
					m.setDatetime(DateUtil.getTime(new Date()));
					m.setProcessType(m.GetProcessType(faceContrastConfigExtend));
					if (newCurrentTime - currentTime > sleep * 1000) {
						m.setStatus(1);
						List<String> logs = new ArrayList<>();
						logs.add("对比服务器:" + faceContrastConfigExtend.getHostname() + "状态异常");
						m.setLogList(logs);
					} else {
						m.setStatus(0);
					}
					lists.add(m);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return lists;
	}
	
	/**
	 * 根据教室Id获取配置列表
	 * @param classroomId
	 * @return
	 */
	private List<FaceAnalysisConfigExtend> GetConfigListByClassroomId(int classroomId) {
		List<FaceAnalysisConfigExtend> listFaceAnalysisConfigExtend = new ArrayList<>();
		synchronized (hostList) {
			for (FaceAnalysisConfigExtend faceAnalysisConfigExtend : hostList) {
				if (faceAnalysisConfigExtend.getClassroomId() == classroomId) {
					listFaceAnalysisConfigExtend.add(faceAnalysisConfigExtend);
				}
			}
		}
		return listFaceAnalysisConfigExtend;
	}

	/**
	 * 初始化对比服务器配置集合
	 * @return
	 */
	private boolean InitFaceContrastConfigList() {
		synchronized (faceContrastConfigList) {
			// 清空
			if (faceContrastConfigList != null) {
				faceContrastConfigList.clear();
			}
			faceContrastConfigList = faceContrastConfigDao.findList(null);
			if (faceContrastConfigList == null || faceContrastConfigList.size() <= 0) {
				logger.error("获取对比服务器失败！");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 初始化分析服务器配置集合
	 * @return
	 */
	private boolean InitFaceAnalysisConfigList(){
		synchronized (hostList) {
			// 清空
			if (hostList != null) {
				for (FaceAnalysisConfigExtend host : hostList) {
					host.getMemcacheUtil().close();
					for (FaceIdentificationConfigExtend faceIdenti : host.getListFaceIdentificationConfig()) {
						faceIdenti.getMemcacheUtil().close();
					}
					host.getListFaceIdentificationConfig().clear();
				}
				hostList.clear();
			}
			// 初始化主机列表数据
			hostList = faceAnalysisConfigDao.findList(null);
			if (hostList == null || hostList.size() <= 0)
				return false;
			for (FaceAnalysisConfigExtend host : hostList) {
				// 初始化主机对应的memcache配置
				// 采集人脸数
				Constants.FRAME_FACE_COUNT = host.getMaxnumfaces();
				try {
					
					List<FaceIdentificationConfigExtend> listFaceIdentificationConfig = faceIdentificationConfigDao.findList(host.getHostno());
					if (listFaceIdentificationConfig == null || listFaceIdentificationConfig.size() <= 0)
						return false;
					for (FaceIdentificationConfigExtend faceIdentificationConfig : listFaceIdentificationConfig) {
						//甄别是否是默认人脸识别服务器
						if (host.getHostip().equals(faceIdentificationConfig.getHostip())) {
							host.setIdentificationFlag(0);
						} else {
							host.setIdentificationFlag(1);
						}
						faceIdentificationConfig.setMemcacheUtil(new MemcacheUtil(faceIdentificationConfig.getHostip(), systemConfig.getMemcachedport()));
					}
					host.setListFaceIdentificationConfig(listFaceIdentificationConfig);
					host.setMemcacheUtil(new MemcacheUtil(host.getHostip(), systemConfig.getMemcachedport()));
				} catch (Exception e) {
					logger.error("获取memcache的ip列表错误");
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 保存课程信息
	 * @param curriculum 课程实体
	 * @param list 配置集合
	 */
	private void SaveCurriculumInfo(CurriculumTbl curriculum,List<FaceAnalysisConfigExtend> list){
		synchronized (hostListMap) {
			hostListMap.put(curriculum.getId(), list);
		}
		
		synchronized (curriculumStateMap) {
			String value = curriculum.getClassroomId() + "|" + "1";
			curriculumStateMap.put(curriculum.getId(), value);
		}
	}

	/**
	 * 向c端发送上下课信息
	 * @param list 配置集合
	 * @param type 1上课 2下课
	 * @return
	 */
	private boolean SendCurriculumInfo(List<FaceAnalysisConfigExtend> list){
		String json = "";
		logger.info("----- 向c端发送上下课信息 -------");
		synchronized (curriculumStateMap) {
			json = gson.toJson(curriculumStateMap);
		}
		for (FaceAnalysisConfigExtend host : list) {
			String mKey = systemConfig.getStartoffkey();
			boolean mResult = host.getMemcacheUtil().setKey(mKey, 60 * 60 * 24, json);
			if (!mResult) {
				throw new BIException("向分析服务器：" + host.getHostname() + " 发送上下课命令失败");
			}
			for (FaceIdentificationConfigExtend lists : host.getListFaceIdentificationConfig()) {
				if (!lists.getMemcacheUtil().setKey(systemConfig.getStartoffkey(), 60 * 60 * 24, json)) {
					throw new BIException("向人脸识别服务器：" + lists.getHostname() + " 发送上下课命令失败");
				}
			}
		}
		return true;
	}

	/**
	 * 检测该教室是否已经在上课
	 * @param classroomId 教室Id
	 * @return
	 */
	private boolean CheckClassroom(int classroomId){
		synchronized (hostListMap) {
			Iterator<Map.Entry<Integer, List<FaceAnalysisConfigExtend>>> it = hostListMap.entrySet().iterator();
			while (it.hasNext()) {
				List<FaceAnalysisConfigExtend> list = it.next().getValue();
				if (list.get(0).getClassroomId() == classroomId) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 
	 * 方法功能说明: 检查状态字段
	 * <P>
	 * statusString:时间戳|0|0|000000 通过“|”符号分割成多个字段：
	 * </P>
	 * <p>
	 * Long main进程的时间戳 int(1) main进程状态，固定赋值0，备用字段 int(1) affdex进程状态（0：正常 1：异常）
	 * int(6) muxer进程相关状态
	 *
	 * 进程状态 0：正常 1：异常 抓取摄像头线程 0：正常 1：异常 转码、描点线程 0：正常 1：异常 生成MP4文件线程 0：正常 1：异常
	 * 操作数据库线程 0：正常 1：异常 操作memcache线程 0：正常 1：异常
	 * </p>
	 * 
	 * @author jinwh
	 * @date 2018年1月31日
	 * @param nowTime
	 * @return
	 */
	private List<String> checkStatusString(String hostName, Object statusObj, int type) {

		long mainTimeStamp = 0l; // main进程的时间戳
//		int mainProStatus = 0; // main进程状态
		int affProStatus = 0; // aff进程状态
		int faceIdentificationStatus = 0; // 人脸识别算法进程状态
		int muxerProStatus = 0; // muxer进程状态
		int muxerThreadStatus1 = 0; // muxer 抓取摄像头线程状态
//		int muxerThreadStatus2 = 0; // muxer 转码、描点线程状态
//		int muxerThreadStatus3 = 0; // muxer 生成MP4文件线程状态
//		int muxerThreadStatus4 = 0; // muxer 操作数据库线程状态
//		int muxerThreadStatus5 = 0; // muxer 操作memcache线程状态
		List<String> msgList = new ArrayList<String>();

		String statusString = statusObj instanceof String ? String.valueOf(statusObj) : null;
		if (statusString == null || statusString.trim().length() == 0) {
			msgList.add("主机:" + hostName + "访问出错，请确认网络连接是否正常！");
			logger.error("主机:" + hostName + "访问出错，Memcached获取异常！_" + statusString);
			return msgList;
		}
		String[] statusDim = statusString.split("\\|");
		

//		logger.info("\n\n");
//		logger.info("\n\n");
//		logger.info("----type：" + type);
//		logger.info("----hostName：" + hostName);
//		logger.info("----获取到的监控信息：" + statusString);
//		logger.info("\n\n");
//		logger.info("\n\n");
		
		
		if (statusDim == null || statusDim.length < 5) {
			msgList.add("主机:" + hostName + "访问出错，请确认网络连接是否正常！");
			logger.error("主机:" + hostName + "访问出错，获取进程状态异常！------" + statusString);
			return msgList;
		}
		try {
			mainTimeStamp = Long.parseLong(statusDim[0]);
//			mainProStatus = Integer.parseInt(statusDim[1]);
			// 如果main进程的timestamp与web服务的差值绝对值大于可容忍的时差，则认为main进程异常
			if (Math.abs(mainTimeStamp - System.currentTimeMillis()) > Constants.SYNCHRONIZETIME) {
				msgList.add("主机:" + hostName + "状态异常！");
				logger.error("主机:" + hostName + "main进程状态异常，main进程时间戳异常：" + mainTimeStamp);
			}
			
			if (type == 0) {
				affProStatus = Integer.parseInt(statusDim[2]);
				String muxerString = statusDim[3];
				if (affProStatus == -1 || muxerString == "-1") {
					msgList.add("主机:" + hostName + "设备准备中...");
					logger.info("主机:" + hostName + "设备准备中...");
					return msgList;
				}
				muxerProStatus = Integer.parseInt(muxerString.substring(0, 1));
				muxerThreadStatus1 = Integer.parseInt(muxerString.substring(1, 2));
				// muxerThreadStatus2 =
				// Integer.parseInt(muxerString.substring(2, 3));
				// muxerThreadStatus3 =
				// Integer.parseInt(muxerString.substring(3, 4));
				// muxerThreadStatus4 =
				// Integer.parseInt(muxerString.substring(4, 5));
				// muxerThreadStatus5 =
				// Integer.parseInt(muxerString.substring(5, 6));
				if (affProStatus != 0)
					msgList.add("主机:" + hostName + "运行异常，表情识别进程状态异常！");
				if (muxerProStatus != 0)
					msgList.add("主机:" + hostName + "主机运行异常，录制视频进程状态异常！");
				if (muxerThreadStatus1 != 0)
					msgList.add("主机:" + hostName + "未能抓取摄像机视频，请确认网络连接是否正常！");
			} else {
				//不监控 人脸识别
//				faceIdentificationStatus = Integer.parseInt(statusDim[4]);
//				if (faceIdentificationStatus != 0)
//					msgList.add("主机:" + hostName + "运行异常，人脸识别算法进程状态状态异常！");
			}
		} catch (Exception e) {
			msgList.add("主机:" + hostName + "访问出错，获取进程状态异常！");
			logger.error("主机:" + hostName + "访问出错，获取进程状态异常！_____" + statusString);
		}
		return msgList;
	}

	/**
	 * 
	 * 方法功能说明: 检查各服务器时间是否同步
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年1月3日
	 * @return
	 */
	private boolean checkTimeSynchronization(List<FaceAnalysisConfigExtend> list) {
		try {
			for (FaceAnalysisConfigExtend host : list) {
				long memTime = Long.parseLong(host.getMemcacheUtil().getKey(systemConfig.getHosttimekey()) + "");
				// 检查服务器时间相差是否在允许范围内
				if ((Math.abs(memTime - System.currentTimeMillis()) > Constants.SYNCHRONIZETIME)){
					throw new BIException("分析服务器：" + host.getHostname() + " 时间不同步");
				}
				for (FaceIdentificationConfigExtend lists : host.getListFaceIdentificationConfig()) {
					memTime = Long.parseLong(lists.getMemcacheUtil().getKey(systemConfig.getHosttimekey()) + "");
					if ((Math.abs(memTime - System.currentTimeMillis()) > Constants.SYNCHRONIZETIME)) {
						throw new BIException("人脸识别服务器：" + lists.getHostname() + " 时间不同步");
					}
				}
			}
		} catch (Exception e) {
			logger.error("检查时间同步出错!" + e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 方法功能说明: 检查共享目录是否正常开启
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年1月4日
	 * @return
	 */
	private boolean CheckShareDir(List<FaceAnalysisConfigExtend> list) {
		for (FaceAnalysisConfigExtend host : list) {
			CheckShareDir(host);
			for (FaceIdentificationConfigExtend model : host.getListFaceIdentificationConfig()) {
				CheckShareDir(model);
			}
		}
		return true;
	}
	
	/**
	 * 检测分析服务器共享目录
	 * @param model
	 * @return
	 */
	private boolean CheckShareDir(FaceAnalysisConfigExtend model) {
		try {
			int i = 0;
			while (i < 20) {
				Thread.sleep(100);
				i++;
				Object obj = model.getMemcacheUtil().getKey(systemConfig.getSharepathconnkey());
				if (obj == null) {
					model.getMemcacheUtil().setKey(systemConfig.getSharepathconnkey(), 60, "1");
					continue;
				}
				String status = obj.toString().trim();
				if (status.equals("1")) { // 正在检查
					continue;
				}
				if (status.equals("2")) { // 正常
					return true;
				}
				if (status.equals("3")) { // 异常
					throw new BIException("分析服务器:" + model.getHostname() + " 检测共享盘失败");
				}
			}
		} catch (InterruptedException e) {
			logger.error(e);
		}
		throw new BIException("分析服务器:" + model.getHostname() + " 检测共享盘不成功");
	}
	
	/**
	 * 检测人脸识别服务器共享目录
	 * @param model
	 * @return
	 */
	private boolean CheckShareDir(FaceIdentificationConfigExtend model) {
		try {
			int i = 0;
			while (i < 20) {
				Thread.sleep(100);
				i++;
				Object obj = model.getMemcacheUtil().getKey(systemConfig.getSharepathconnkey());
				if (obj == null) {
					model.getMemcacheUtil().setKey(systemConfig.getSharepathconnkey(), 60, "1");
					continue;
				}
				String status = obj.toString().trim();
				if (status.equals("1")) { // 正在检查
					continue;
				}
				if (status.equals("2")) { // 正常
					return true;
				}
				if (status.equals("3")) { // 异常
					throw new BIException("人脸识别服务器:" + model.getHostname() + " 检测共享盘失败");
				}
			}
		} catch (InterruptedException e) {
			logger.error(e);
		}
		throw new BIException("人脸识别服务器:" + model.getHostname() + " 检测共享盘不成功");
	}

	@Override
	public int start() {
		if (Constants.LISTCAMERAREADYMODEL.size() <=0 
				|| Constants.LISTCAMERAREADYMODEL.get(0).getHostList().size() <= 0) {
			return 4;
		}
		return 0;
	}

	@Override
	public Map<String, String> GetFrameData() {
		Map<String, String> map = new HashMap<>();
		List<FaceAnalysisConfigExtend> hostList = null;
		SystemConfig systemConfig = null;
		for (CameraReadyModel caReadyModel : Constants.LISTCAMERAREADYMODEL) {
			hostList = caReadyModel.getHostList();
			systemConfig = caReadyModel.getSystemConfig();
			
			for (FaceAnalysisConfigExtend faceAnalysisConfigExtend : hostList) {
				String data = faceAnalysisConfigExtend.getMemcacheUtil().getKey(systemConfig.getFramedatakey() 
						+ "_" + caReadyModel.getCurriculumId() + "_" + faceAnalysisConfigExtend.getCameraip()) + "";
				map.put(faceAnalysisConfigExtend.getCameraip(), data);
				List<Integer> pixelList = pixelMap.get(faceAnalysisConfigExtend.getCameraip());
				
				if (pixelList == null || pixelList.get(0) <= 1 || pixelList.get(1) <= 1) {
					pixelList = new ArrayList<>();
					
					String pixel = faceAnalysisConfigExtend.getMemcacheUtil().getKey(systemConfig.getCamerapixelkey() 
							+ "_" + caReadyModel.getCurriculumId() + "_" + faceAnalysisConfigExtend.getCameraip()) + "";
					
					if (pixel != null && !pixel.equals("null")) {
						String[] pixels = pixel.split("\\|");
						
						if (pixels.length > 1) {
							String ws = pixels[0] + ""; //2500.00000
							String hs = pixels[1] + "";
							Integer w = (int) Double.parseDouble(ws);
							Integer h = (int) Double.parseDouble(hs);
							pixelList.clear();
							pixelList.add(w);
							pixelList.add(h);
							pixelMap.put(faceAnalysisConfigExtend.getCameraip(), pixelList);
						}
					}
				}
			}
		}
		return map;
	}

	@Override
	public List<Integer> GetPixelMap(String ameraIp) {
		return pixelMap.get(ameraIp);
	}

	
}
