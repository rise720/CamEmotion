package com.cac.CamEmotion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 类说明: sql 注入检查
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月16日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiSQL {
	public static final String ALL = "ALL";
	
	/**
	 * 
	 * 方法说明: 全字段检查
	 * <P>
	 *     当name=ALL，检查所有字段是否在禁止范围内
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @return
	 */
	public abstract String name() default "";
	
	/**
	 * 
	 * 方法说明: 指定字段检查
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @return
	 */
	public abstract Field[] fields() default {};
}
