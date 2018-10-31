package com.cac.CamEmotion.paging;

import java.util.List;

/**
 * 
 * 类功能说明:分页查询
 * <P>
 *     
 * </P>
 *
 * @author zhangsh
 * @data 2017年05月20日
 * @param <T>
 */
public class PageUtil<T>{
	/**
	 * 
	 * 
	 * 方法说明: 分页查询方法
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2017年05月20日
	 * @param callback
	 * @return
	 */
	public PageList<T> getListByPage(PagingCallback<T> callback){
		return callback.pageList();
	}
}