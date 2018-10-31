package com.cac.CamEmotion.dao;

import java.util.List;

import com.cac.CamEmotion.model.StatisticalStandardTbl;

public interface StatisticalStandardTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StatisticalStandardTbl record);

    int insertSelective(StatisticalStandardTbl record);

    StatisticalStandardTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StatisticalStandardTbl record);

    int updateByPrimaryKey(StatisticalStandardTbl record);
    
    /**
     * 
     * 
     * 方法功能说明: 查询评价指标
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年4月23日
     * @param record
     * @return
     *
     */
    List<StatisticalStandardTbl> selectByModel(StatisticalStandardTbl record);
} 