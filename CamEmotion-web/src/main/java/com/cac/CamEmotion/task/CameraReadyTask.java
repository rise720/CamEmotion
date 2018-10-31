package com.cac.CamEmotion.task;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cac.CamEmotion.service.CameraReadyCheckService;

/**
 * 
 * 类功能说明:springMvc定时任务类
 * <P>
 * 
 * </P>
 *
 * @author zhangfei
 * @data 2016年12月20日
 */
@Component("CameraReadyTask")
public class CameraReadyTask{
	
	@Resource
	CameraReadyCheckService cameraReadyCheckService;
	
	/**
	 * 
	 * 方法功能说明: 获取所有设备状态 将启动信号发送给各个设备
	 * <P>
	 * cron表达式：* * * * * *（共6位，使用空格隔开，具体如下） cron表达式：*(秒0-59) *(分钟0-59) *(小时0-23)
	 * *(日期1-31) *(月份1-12或是JAN-DEC) *(星期1-7或是SUN-SAT) 注意： 30 * * * * *
	 * 表示每分钟的第30秒执行，而（*斜杠30）表示每30秒执行
	 * </P>
	 * 0/10 * * * * 每隔10秒check一次
	 * @author zhangfei
	 * @date 2016年12月20日
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void DeleteOrderTask() {
		cameraReadyCheckService.CheckCameraReady();
	}
}