package com.cac.CamEmotion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cac.CamEmotion.common.LogType;

/**
 * 
 * 类说明: aop操作日志注解定义
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月18日
 */
@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface OperateLog {
	/**
	 * 
	 * 方法说明: 日志种类
	 * <P>
	 *      见:com.cac.scf.common.LogType, 默认：LogType.SYSLOG
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月18日
	 * @return
	 */
	public LogType logType() default LogType.SYSLOG;
	
	/**
	 * 
	 * 方法说明: 日志类型
	 * <P>
	 *     E-error；I-Information；默认：'I'
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年6月7日
	 * @return
	 */
	public String logLevel() default "I";
	
	
	/**
	 * 
	 * 方法说明: 分行Id(接收目标方法的参数序号)
	 * <P>
	 *     分行Id与公司Id两者只能填1个，当为分行Id写入银行端操作日志表，否则写入企业端操作日志表
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年6月7日
	 * @return
	 */
	public int branchIdIndex() default -1;
	
	/**
	 * 
	 * 方法说明: 公司Id(接收目标方法的参数序号)
	 * <P>
	 *     分行Id与公司Id两者只能填1个，当为分行Id写入银行端操作日志表，否则写入企业端操作日志表
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年6月7日
	 * @return
	 */
	public int companyIdIndex() default -1;
	
	/**
	 * 
	 * 方法说明: 模块代码
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月18日
	 * @return
	 */
	public String moduleCode() default "";
	
	/**
	 * 方法说明: 操作类型
	 * <P>
	 *    "ADD";  	 //新增操作
	      "DELETE";  //删除操作
	      "UPDATE";  //修改操作
	      "QUERY";   //查询操作
	 * </P>
	 *
	 * @author yudongyang
	 * @data 2017年8月4日
	 * @return
	 */
	public String OperateType() default "";
	
	/**
	 * 
	 * 方法说明: 日志内容
	 * <P>
	 * 	   	日志内容支持格式，参数取目标方法的参数值
	 *		日志格式化eg:<br>
	 *		String s =  String.format("this is a %s %s test", "Java", "C++"); <br>
	 *		输出：this is a java C++ test
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月18日
	 * @return
	 */
	public String content() default "";
	
	/**
	 * 
	 * 方法说明: 
	 * <P>
	 *     如果是保存文件操作，存储完整路径。
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年6月7日
	 * @return
	 */
	public String filePath() default "";
	
	/**
	 * 
	 * 方法说明: 获取目标方法的参数名列表
	 * <P>
	 *     用于生成内容可变的操作日志，根据这里的参数顺序向content传值<br>
	 *     日志格式 化eg:<br>
	 *     String s =  String.format("this is a %s %s test", "Java", "C++"); <br>
	 *     输出：this is a java C++ test
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年6月7日
	 * @return
	 */
	public int[] argsIndex() default -1;
}
