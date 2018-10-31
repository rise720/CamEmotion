package com.cac.CamEmotion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 类说明: 权限代码
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月19日
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCode {
	
	/**
	 * 
	 * 方法说明: 权限代码
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @return
	 */
	public String value() default "";
	
	/**
	 * 
	 * 方法说明: 权限代码描述
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月19日
	 * @return
	 */
	public String description() default "";
}
