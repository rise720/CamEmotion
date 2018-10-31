package com.cac.CamEmotion.dao;

import java.util.List;

import com.cac.CamEmotion.model.EmotiondataSummaryTbl;

public interface EmotiondataSummaryTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmotiondataSummaryTbl record);

    int insertSelective(EmotiondataSummaryTbl record);

    EmotiondataSummaryTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmotiondataSummaryTbl record);

    int updateByPrimaryKey(EmotiondataSummaryTbl record);
    
    /**
     * 
     * 
     * 方法功能说明: 根据学科类型等查询对应评价标准
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年4月25日
     * @param record
     * @return
     *
     */
    List<EmotiondataSummaryTbl> selectByModel(EmotiondataSummaryTbl record);
}