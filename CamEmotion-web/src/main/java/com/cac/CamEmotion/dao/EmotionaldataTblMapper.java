package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmotionaldataTbl;

public interface EmotionaldataTblMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmotionaldataTbl record);

    int insertSelective(EmotionaldataTbl record);

    EmotionaldataTbl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmotionaldataTbl record);

    int updateByPrimaryKey(EmotionaldataTbl record);
    
    /**
     * 方法说明：获取当前课程的数据分析合计
     * @param id
     * @return
     */
    EmotionaldataTbl selectEmotionaldataTblTotal(Integer id);
    
    /**
     * 方法说明：获取某一节课的所有数据（ 过滤七种情绪为零以及求和后的数据）
     * @param EmotionaldataTbl
     */
    List<EmotionaldataTbl> selectEmotionaldataList(@Param("curriculumTbl")CurriculumTbl curriculumTbl,@Param("studentId")int studentId);
    
    /**
     * 
     * 
     * 方法功能说明: 获取某一节课的所有数据（过滤七种情绪为零后的原始数据）
     * <P>
     *     
     * </P>
     * 
     * @author Chenyang
     * @date 2017年11月21日
     * @param emotionaldatatbl
     * @return
     *
     */
    List<EmotionaldataTbl> selectEmotionaldataListSource(CurriculumTbl curriculumTbl);
    
    /**
     * 
     * 
     * 方法功能说明: 获取某一节课的所有数据
     * <P>
     *     
     * </P>
     * 
     * @author Chenyang
     * @date 2017年11月21日
     * @param emotionaldatatbl
     * @return
     *
     */
    List<EmotionaldataTbl> selectEmotionaldataListReal(CurriculumTbl curriculumTbl);
    
    /**
     * 
     * 
     * 方法功能说明: 获取某一节课的所有数据，包括Json
     * <P>
     *     
     * </P>
     * 
     * @author Chenyang
     * @date 2017年11月21日
     * @param emotionaldatatbl
     * @return
     *
     */
    List<EmotionaldataTbl> selectEmotionaldataListJson(CurriculumTbl curriculumTbl);
    
    /**
     * 
     * 
     * 方法功能说明: 按课堂id删除对应采集的数据
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年4月4日
     * @param emotionaldatatbl
     * @return
     *
     */
    int deleteByCurriculumId(EmotionaldataTbl emotionaldatatbl);
    
    /**
     * 
        * @Title: selectStudentActionList  
        * @Description: 获取各个学生的每秒的行为数据
        * @param @param curriculumTbl
        * @param @return    参数  
        * @return List<EmotionaldataTbl>    返回类型  
        * @throws
     */
    List<EmotionaldataTbl> selectStudentActionList(@Param("curriculumTbl")CurriculumTbl curriculumTbl,@Param("studentId")int studentId);
    
}