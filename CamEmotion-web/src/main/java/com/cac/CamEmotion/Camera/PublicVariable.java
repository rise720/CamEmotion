package com.cac.CamEmotion.Camera;

import java.io.File;
import java.util.List;

/**
 * 
 * 类功能说明: 动态变量
 * <P>
 *     
 * </P>
 *
 * @author	jinwh
 * @data	2017年8月9日
 */
public class PublicVariable {
	public static String opencv_load = null;		//OpenCV Dll加载路径
	public static String ffmpeg_load = null;		//FFmpeg Dll加载路径
	public static int camera_number = 0;			//摄像头数量
	public static int camera_width = 0;			//摄像头像辅码流素宽
	public static int camera_height = 0;			//摄像头像辅码流素高
	public static int camera_width_m = 0;			//摄像头像主码流素宽
	public static int camera_height_m = 0;			//摄像头像主码流素高
	public static int camera_filter_time = 10;		//摄像头像保存视频过滤时间
	public static List<Camera> camera_List = null;	//摄像头列表
	public static String image_path = null;			//摄像图片存放路径
	public static String video_path = null;			//摄像视频存放路径
	public static String video_temp_path = null;			//摄像视频转MP4临时存放路径
	public static String video_sharePath = null;	//摄像头视频存放共享目录路径
	public static int video_maxlength = 524288000;	//摄像视频文件大小限定
	public static boolean cameraSwitch = false;		//摄像头开关Flg
	public static int cameraStartNum = 0;		    //线程开启次数
	public static String cameraFormat = "rtsp";
	public static int cameraVideoCodec = 174;
	public static double cameraFrameRate = 20;
	public static int videoShow = 0; //0显示 1不显示
	public static int collectionRate = 20;
	// 进程文件
	// 控制子进程的开关，该文件存在 ，子进程持续运行，不存在时，子进程自动退出
	public static File pidFile = new File("./pid");		
}
