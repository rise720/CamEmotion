package com.cac.CamEmotion.paging;

/**
 * 
 * 类功能说明:page扩展类
 * <P>
 * 用于页面查询条件和页码存取及传输 和页面绑定的页码无关 和页面绑定的条件有关
 * </P>
 *
 * @author zhangfei
 * @data 2016年10月21日
 */
public class PageExtend {
	// 当前页号, 每页显示记录数
	private long currentPage;
	// 每页条数
	private long pageRecorders;
	// 排序字段
	private String expression;
	// 排序规则
	private String rule;

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getPageRecorders() {
		return pageRecorders;
	}

	public void setPageRecorders(long pageRecorders) {
		this.pageRecorders = pageRecorders;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}
}
