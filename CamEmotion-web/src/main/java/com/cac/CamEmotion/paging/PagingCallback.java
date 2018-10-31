package com.cac.CamEmotion.paging;

import java.util.List;

/**
 * 
 * 
 * 类功能说明: 分页回调参数类
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年5月20日
 */
public abstract class PagingCallback<T> {
	// 当前页号, 每页显示记录数
	private long currentPage, pageRecorders;
	
	// 排序字段, 排序规则
	private String expression, rule;
	
	// 查询条件
	private T model;
	
	/**
	 * 
	 * 构造函数: 带指定参数的构造函数
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年5月20日
	 * @param currentPage 当前页号
	 * @param pageRecorders 每页显示记录数
	 */
	public PagingCallback(long currentPage, long pageRecorders){
		this.currentPage = currentPage;
		this.pageRecorders = pageRecorders;
	}
	
	/**
	 * 
	 * 
	 * 构造函数: 
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年5月20日
	 * @param currentPage
	 * @param pageRecorders
	 * @param expression
	 * @param rule
	 */
	public PagingCallback(T model, long currentPage, long pageRecorders, String expression, String rule){
		this.model = model;
		this.currentPage = currentPage;
		this.pageRecorders = pageRecorders;
		this.expression = expression;
		this.rule = rule;
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 获取查询结果的总记录数
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年5月20日
	 * @param model 查询条件
	 * @return 总记录数
	 */
	public abstract int getCount(T model); 
	
	/**
	 * 
	 * 
	 * 方法功能说明: 获取分页查询结果列表
	 * <P>
	 *     该方法的实现类必须支持分页查询,分页查询所需要分页参数将由本类计算后传入
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年5月20日
	 * @param model 查询条件
	 * @param offset 偏移量
	 * @param rows 显示行数
	 * @param expression 排序字段
	 * @param rule 排序规则
	 * @return 分页结果集PageList<?>
	 */
	public abstract List<T> queryList(T model, long offset, long rows, String expression, String rule);
	
	/**
	 * 
	 * 
	 * 方法功能说明: 分页查询
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年5月20日
	 * @return
	 */
	public PageList<T> pageList(){
		// 注入总记录数,当前页,每页记录数,计算查询结果的偏移量
		PageList<T> pageList = new PageList<T>(getCount(model), currentPage, pageRecorders);
		
		pageList.setObjList(
				queryList(model, 
							pageList.getNextStartRecorders(), 
								pageList.getPageRecorders(), 
								expression, 
								rule)
				);
		return pageList;
	}
}
