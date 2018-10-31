package com.cac.CamEmotion.common;

import java.util.ArrayList;
import java.util.List;

import com.cac.CamEmotion.model.CameraReadyModel;
import com.cac.CamEmotion.util.LoadProperties;

/**
 * 
 * 类功能说明: 常量定义类
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年4月14日
 */
public class Constants {
	public static final String ENCODING_UTF8 = "UTF-8";	// 字符编码
	
	public static final String ENCODING_GBK = "GBK";	// GBK字符编码
	
	public static final String SESSIONUSERNAME = "userName";	// 存储用户信息的key
	
	public static final String UU_SESSION_ID = "uu_session_id";	// 全局的用户id
	
	public static final String SCRIP = "scrip";			// 临时凭证
	
	public static final long SYNCHRONIZETIME = Long.valueOf(LoadProperties.getText("sys.synchronizeTime"));	//服务器时间同步时，可允许的误差时间（毫秒）
			
	/* 后端管理平台，允许未登录调用的url，这些方法一般都是登录的url，将要执行登录操作，并缓存用户信息 */
	public static final String[] ALLOWMETHODLIST = {"/user/login"};
	
	/* 前端cookie管理，要求不读取cookie中的用户信息的方法，这些一般都是用户初次登录，将要写cookie的方法 */
	public static final String[] ACT_USER_BIND = {"/loginOnlineStore"};	
	
	public static final String TEMP_PATH = "Tmp/";	// 临时目录
	public static final String TEMP_PATH_END = "--";	// 临时目录
	
	public static final String MD5_KEY = LoadProperties.getText("system.md5key");	// 加密key
	
	public static final String HTTP = "http";	// 非加密协议
	public static final String HTTPS = "https";	// 加密协议
	
	public static final String SYSTEM_LOGGER = "SYSTEM";	// 表示输出系统日志
	
	public static final String BI_LOGGER = "BI";	// 表示输出业务级日志

	public static final int  MAXLENGTH = 50;//文件名长度
	
	public static final long PAGERECORDERS = 10L;	// 每页显示记录数
	
	public static final int  MAX_CONTENT_ENGTH = 5000;//内容项长度
	
	public static final long SYS_WORKERID = Long.valueOf(LoadProperties.getText("sys.workerId"));			// 工作区域编号
	
	public static final long SYS_DATACENTERID = Long.valueOf(LoadProperties.getText("sys.datacenterId"));	// 机器编号
	
	public static final String NOCREATEKEY = "NOCREATEKEY";	// Map 中key为此名字的对象，生成http请求参数时，不会生成key
	
	public static final String DUPLICATE_SUBMIT = "Duplicate_Submit";						// 重复提交标志
		
	/********************************* Token过期时间 Start *********************************/
	
	public static final int TOKEN_OUTTIME=Integer.parseInt(LoadProperties.getText("token.outTime"));//token.outTime token过期时间  用于表单重复提交
	
	public static final int REFRESHTOKEN_OUTTIME=Integer.parseInt(LoadProperties.getText("token.refreshTokenOutTime"));//RefreshToken过期时间
	
	public static final int ACCESSTOKEN_OUTTIME=Integer.parseInt(LoadProperties.getText("token.accessTokenOutTime"));//AccessToken过期时间
	
	
	/********************************* 其他参数 *********************************/
		
	public static boolean isInClassing = false;
	
	public static final int calEmotionToDB = Integer.parseInt(LoadProperties.getText("emotion.caltoDB"));
	
	public static final int FACE_COUNT_SS = Integer.parseInt(LoadProperties.getText("face.count.SS"));//抬头，低头人脸数分界标准,如：每帧中face数大于2的为抬头听课的状态，2即为该参数值
	
	public static final int FRAME_COUNT_PERCENT = Integer.parseInt(LoadProperties.getText("frame.count.percent"));//抬头，低头人脸数所占的比例
	
	public static final int SPEAK_COUNT_SS = Integer.parseInt(LoadProperties.getText("speak.count.SS"));//说话讨论分界标准,如：每帧中张嘴的face数大于4的为生生讨论状态，4即为该参数值
	
	public static final int SPEAK_COUNT_PERCENT = Integer.parseInt(LoadProperties.getText("speak.count.percent"));//单位时间（如1分钟）内，生生讨论状态的秒数比率，大于此值，则该单位时间为生生讨论状态
	
	public static final int STUDENT_FACECOUNT_PERCENT = Integer.parseInt(LoadProperties.getText("student.facecount.percent"));//学生个人听课行为，抬头比率的阈值
	
	public static final int STUDENT_SPEAK_PERCENT = Integer.parseInt(LoadProperties.getText("student.speak.percent"));//学生个人讨论行为，张嘴比率的阈值
	
	public static final int REPORT_FLAG = Integer.parseInt(LoadProperties.getText("report.flag")); //报表导出类型,0:只导出活跃度，互动频度，课堂情景分析报表；1：导出所有报表（即：包含喜悦，惊讶，困惑，皱眉，无表情）
	
	public static final int EMOTION_TABLE_COUNT = Integer.parseInt(LoadProperties.getText("emotion.table.count")); //分表数

	public static int FRAME_FACE_COUNT = 10;
	
	public static String SHARE_PATH = LoadProperties.getText("sharePath");		//资源共享目录
	
	public static String DOWNLOAD_PATH = LoadProperties.getText("download.path");	// 资源下载地址
	
	public static String DOWNLOAD_TEMP = LoadProperties.getText("download.temp");	// 资源下载临时地址
	
	public static String SYSTEM_RESOURCE_PATH = LoadProperties.getText("system.resource.path"); // 系统资源文件地址
	
	public static String SYSTEM_NAME = LoadProperties.getText("system.name"); // 系统名称
	
	public static String UPLOAD_PATH = LoadProperties.getText("system.homepath");		// 资源上传地址
	
	public static final String SYSTEM_WEBURL = LoadProperties.getText("system.webUrl");//前台工程地址
	
	/**
	 * 特征提取服务器IP
	 */
	public static String SYSTEM_IDEBTIFICATION = LoadProperties.getText("system.identification");
	
	/**
	 * 人脸识别方案标识 0kmean 1faceId
	 */
	public static Integer SYSTEM_KMEABORFACEID = Integer.parseInt(LoadProperties.getText("system.kmeanOrFaceId"));
	
	public static final int SOCKET_QUEUE_TIME = Integer.parseInt(LoadProperties.getText("socket.queue.time"));//socket队列延迟时间
	
	public static List<CameraReadyModel> LISTCAMERAREADYMODEL = new ArrayList<CameraReadyModel>();
	
	public static String CURR_CAMERAIP = "127.0.0.1";
	
	public static String PROJECT_PATH = "";
}
