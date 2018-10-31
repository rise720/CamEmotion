package com.cac.CamEmotion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
 * 类说明: 注解类-用于表单重复提交控制
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2016年11月14日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	// 默认方式处理重复提交的请求
	public final static int DEFAULT_PROCESS = 0;
	
	// 由目标函数处理重复提交的请求
	public final static int SELF_PROCESS = 1;
	
	/**
	 * 方法说明: 生成一个Token值
	 * <P>
	 *     这是一个随机数，并页存入session当中，并且支持分布式部署环境
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月14日
	 * @return
	 */
	boolean create() default false;
	
	/**
	 * 
	 * 
	 * 方法说明: 检查是否重复提交
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月14日
	 * @return
	 */
	boolean avoidDuplication() default false;
	
	
	/**
	 * 
	 * 
	 * 方法说明: 重复提交后的由谁处理错误
	 * <P>
	 *     可选方式有两种:
	 *     0、handle = 0 ，系统默认处理方式，抛出异常或是返回json格式的错误消息，返回数据格式由目标函数决定
	 *     1、handle = 1，由目标函数处理重复提交的请求，会在request参数增加"Duplicate_Submit"参数，值为true
	 *    
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月17日
	 * @return
	 */
	int handler() default DEFAULT_PROCESS;
}
