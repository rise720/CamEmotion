package com.cac.CamEmotion.dao;

import java.util.Date;

import com.cac.CamEmotion.model.TokenTbl;

public interface TokenTblMapper {
	/**
	 * 
	 * 
	 * 方法说明: 删除已使用的token
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年11月14日
	 * @param token
	 * @return
	 */
    int deleteByPrimaryKey(Long token);

    int insert(TokenTbl record);

    int insertSelective(TokenTbl record);

    TokenTbl selectByPrimaryKey(Long token);

    int updateByPrimaryKeySelective(TokenTbl record);

    int updateByPrimaryKey(TokenTbl record);
    
    /**
     * 
     * 
     * 方法说明: 删除超时指定时间未使用token
     * <P>
     *     
     * </P>
     * 
     * @author zhangsh
     * @date 2016年11月15日
     * @param time 超时时间
     */
    void deleteByTimeOut(Date timeout);
}