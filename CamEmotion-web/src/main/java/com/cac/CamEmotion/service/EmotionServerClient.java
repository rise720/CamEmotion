package com.cac.CamEmotion.service;

import java.util.List;
import java.util.Map;

import com.cac.CamEmotion.jsonModel.ClassnoModel;
import com.cac.CamEmotion.jsonModel.MemcacheStatus;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.FaceAnalysisConfigExtend;
import com.cac.CamEmotion.model.SystemConfig;

/**
 * 
 * 类说明: 远程操作服务
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @date 2017年9月1日
 */
public interface EmotionServerClient {
	/**
	 * 方法说明: 开始服务
	 * @param courseId 进程Id
	 * @param frameFaceCount	分析工具最大抓取脸数
	 * @param emotionTableName	录制工具保存数据库表名
	 * @return
	 */
	public ClassnoModel start(CurriculumTbl curriculum);
	
	/**
	 * 
	 * 方法说明: 结束服务
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @date 2017年9月1日
	 * @param courseId
	 */
	public boolean stop(Integer courseId);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 检查memcache是否联通
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author Chenyang
	 * @date 2017年10月13日
	 * @return
	 *
	 */
	public List<MemcacheStatus> checkMemcacheStatus(CurriculumTbl curriculumTbl);
	
	/**
	 * 
	 * 方法功能说明: 初始化主机数据
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年1月24日
	 * @return
	 */
	public boolean initHostData();
	
	/**
	 * 
	 * 方法功能说明: 检测分析服务器主机名称、摄像机IP是否重复
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2018年2月1日
	 * @param host
	 * @return
	 */
	public boolean checkConfirmUpdateHost(FaceAnalysisConfigExtend systemConfig);
	
	/**
	 * 检测配置是否在操作中
	 * @param model
	 * @return 0没有使用 1该配置摄像头正在使用中 2该配置教室正在使用中
	 */
	public int CheckConfigOperation(FaceAnalysisConfigExtend model);
	
	/**
	 * 获取内存中的配置信息
	 * @return
	 */
	public List<FaceAnalysisConfigExtend> GetFaceAnalysisConfigList();
	
	/**
	 * 根据主机编号获取内存中对应的配置信息
	 * @return
	 */
	public FaceAnalysisConfigExtend GetFaceAnalysisConfig(int hostNo);
	
	/**
	 * 课程结束
	 * @param courseId 课程Id
	 */
	public boolean CloseCourse(Integer courseId);
	
	/**
	 * 对比进程健康性检查
	 * @param contrastIp 对比进程IP
	 * @param currentTime 保存最新的时间戳
	 */
	public boolean ContrastStatus(String contrastIp, Long currentTime);
	
	/**
	 * 获取基础配置
	 * @return
	 */
	public SystemConfig GetSystemConfig();
	
	/**
	 * 方法说明: 开始服务
	 * 
	 * @return
	 */
	public int start();
	
	/**
	 * 获取帧解析数据
	 * 
	 * @return
	 */
	public Map<String, String> GetFrameData();

	/**
	 * 获取摄像头像素值
	 * 
	 * @return
	 */
	public List<Integer> GetPixelMap(String ameraIp);
}
