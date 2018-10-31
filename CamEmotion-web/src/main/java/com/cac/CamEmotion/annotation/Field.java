package com.cac.CamEmotion.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 类说明: 
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月16日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public abstract @interface Field{
	/**
	 * 
	 * 方法说明: 字段名
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @return
	 */
	public abstract String name() default "";
	
	/**
	 * 
	 * 方法说明: 允许的值范围
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @return
	 */
	public abstract String[] allowValue() default {"asc", "desc", ""};
	
	/**
	 * 
	 * 方法说明: 禁止的值范围
	 * <P>
	 *     
	 * </P>
	 *
	 * @author zhangsh
	 * @data 2017年5月16日
	 * @return
	 */
	public String[] forbidValue() default {"'", "and", "exec", "execute", "insert", "select", "delete", "update", "count", "drop",
		"*", "%", "chr", "mid", "master", "truncate", "char", "declare", "sitename", "net user", "xp_cmdshell", ";", "or", "-",
		"+", ", ", "like'", "and", "exec", "execute", "insert", "create", "drop", "table", "from", "grant", "use", "group_concat",
		"column_name", "information_schema.columns", "table_schema", "union", "where", "select", "delete", "update", "order", "by",
		"count", "*", "chr", "mid", "master", "truncate", "char", "declare", "or", ";", "-", "--", "+", ", ", "like", "//", "/", "%", "#"};
}
