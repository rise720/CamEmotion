package com.cac.CamEmotion.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：PageList.java
 * 类功能说明: 分页查询显示Bean
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年05月20日
 */
public class PageList<T> {
	
	private long currentPage = 1l;					//当前页
	private long totalRows = 0l;					//总记录数，由构造函数传入
	private long pageRecorders = 10l;   			//每页记录数,默认10
	private List<T> objList = new ArrayList<T>();	//存放欲展示的对象列表
	
	private boolean hasNextPage = false; //是否有下一页:自己运算
	private boolean hasPreviousPage = false; //是否有前一页 :自己运算
    
	//增加上一页索引 [directly to target page]
    private Long lastPageIndex = 1L;
    //增加下一页索引 [directly to target page]
    private Long nextPageIndex = 1L;
    // 查询起始位置	
	private long nextStartRecorders = 0l;			//查询起始位置,上一条记录
	
	private int retcode = 0;	// 返回码, 小于0，表示参数通过内部自动调整
	
	private long totalPages = 0l;	// 总页数
	
	public PageList(){
		new PageList(0l);
	}
	
	public PageList(long totalRows){
		new PageList(totalRows,1l);
	}
	
	public PageList(long totalRows,long currentPage){
		new PageList(totalRows,currentPage,-1l);
	}
	
	/**
	 * PageList 构造函数
	 * @param totalRows			总记录数
	 * @param currentPage		当前页
	 * @param pageRecorders		每页记录数
	 */
	public PageList(long totalRows,long currentPage,long pageRecorders){
		this.totalRows = totalRows;
		this.currentPage = currentPage;
//		if(pageRecorders>0){			
			this.pageRecorders = pageRecorders;
//		}
		
		//如果当前页小于1，报错
		if(this.currentPage <1){
			throw new IllegalArgumentException("参数无效，当前页  < 1");
		}
		
		//上一条记录 = （当前页 - 1） * 每页记录数
		this.setNextStartRecorders(this.pageRecorders*(this.currentPage - 1));
		
		//如果上一记录位置大于总记录数报错
		if(totalRows <= nextStartRecorders){
			// throw new IllegalArgumentException("参数无效，总记录  <= 查询起始页的位置");
			
			/* MODIFY BY ZhangSH 20160425 请求的起始记录位置大于总记录数时，自动调整为最后一页*/
			this.currentPage = getTotalPages();
			
			this.setNextStartRecorders(this.pageRecorders*(this.currentPage - 1));
			
			retcode = -1;	// 调整参数标志
		}	
		
		totalPages = getTotalPages();
	}
	
	/**
	 * 
	 * 方法功能说明:返回上一页面编号
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @return
	 */
	public long getLastPageIndex() {
		return this.currentPage - 1;
	}
	
	/**
	 * 
	 * 方法功能说明:返回下一页面编号
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @return
	 */
	public long getNextPageIndex(){
		return this.currentPage + 1;
	}
	
	/**
	 * 
	 * 方法功能说明:是否有上一页
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @return true：有上一页，false：无上一页
	 */
	public boolean isHasPreviousPage(){
		return(this.currentPage > 1 ? true : false);
	}
	
	/**
	 * 
	 * 方法功能说明:是否有下一页
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @return true：有下一页，false：无下一页
	 */
	public boolean isHasNextPage(){
		return(this.currentPage < getTotalPages() ? true : false);
	}
	
	/**
	 * 
	 * 方法功能说明:计算总页数
	 * <P>
	 * 		数据库查询只提供总记录数，由总记录数计算出总页数，计算方法：<BR>
     * 		一. 总记录 <= 每页记录数，总页数=1<BR>
     * 		二. 总记录 > 每页记录数，继续计算:<BR>
     * 		总记录数/每页记录数，判断余数<BR>
     * 		1. 余数 = 0 ， 总页数=总记录数/每页记录数<BR>
     * 		2. 余数 > 0,  总页数=总记录数/每页记录数 + 1<BR>      
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @return
	 */
	public long getTotalPages(){
		if(this.totalRows < this.pageRecorders) {
			return 1l;
		}
		if(this.pageRecorders == 0) {
			return 0l;
		}
		return (this.totalRows % this.pageRecorders == 0 ? 
				this.totalRows / this.pageRecorders : this.totalRows / this.pageRecorders + 1 );
	}
	
	public long getCurrentPage() {
		return currentPage;
	}
	
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}
	
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	
	public void setLastPageIndex(Long lastPageIndex) {
		this.lastPageIndex = lastPageIndex;
	}
	
	public void setNextPageIndex(Long nextPageIndex) {
		this.nextPageIndex = nextPageIndex;
	}
	
	public long getTotalRows() {
		return totalRows;
	}
	
	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}
	
	public long getPageRecorders() {
		return pageRecorders;
	}
	public void setPageRecorders(long pageRecorders) {
		this.pageRecorders = pageRecorders;
	}
	
	public long getNextStartRecorders() {
		return nextStartRecorders;
	}
	
	public void setNextStartRecorders(long nextStartRecorders) {
		this.nextStartRecorders = nextStartRecorders;
	}
	
	public List<T> getObjList() {
		return objList;
	}
	
	public void setObjList(List<T> objList) {
		this.objList = objList;
	}
	
	public int getRetcode() {
		return retcode;
	}
	
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
}
