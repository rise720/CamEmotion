package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.EmotiondataStatistics;

public interface EmotiondataStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmotiondataStatistics record);

    int insertSelective(EmotiondataStatistics record);

    EmotiondataStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmotiondataStatistics record);

    int updateByPrimaryKey(EmotiondataStatistics record);
    
    /**
     * 
     * 
     * 方法功能说明: 查询报表数据
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2017年12月13日
     * @param record
     * @return
     *
     */
    List<EmotiondataStatistics> getEmotiondataStatistics(EmotiondataStatistics record);
    
    /**
     * 
     * 
     * 方法功能说明:按课堂id删除对应报表数据 
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年4月4日
     * @param id
     * @return
     *
     */
    int deleteByModel(EmotiondataStatistics record);
    
    int countByModel(EmotiondataStatistics record);
    
    int insertBatch(@Param("emotionDatas") List<EmotiondataStatistics> record);
    
}