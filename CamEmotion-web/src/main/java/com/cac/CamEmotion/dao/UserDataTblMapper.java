package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.CurriculumVideoTbl;
import com.cac.CamEmotion.model.UserDataTbl;

public interface UserDataTblMapper {
	/**
	 * 通过 id 删除的方法
	 * @param id 用户id
	 * @return
	 */
    int deleteByPrimaryKey(Integer id);
    
    /**
	 * 通过 Useraccount 查找的方法
	 * @param id 用户id
	 * @return
	 */
    UserDataTbl selectByUseraccountKey(UserDataTbl useraccount);

    /**
     * 添加
     * @param record 用户对象
     * @return
     */
    int insert(UserDataTbl record);

    /**
     * 选择新增
     * @param record 用户对象
     * @return
     */
    int insertSelective(UserDataTbl record);

    /**
     * 通过 id 查找
     * @param id 用户id
     * @return
     */
    UserDataTbl selectByPrimaryKey(Integer id);

    /**
     * 选择修改
     * @param record 用户对象
     * @return
     */
    int updateByPrimaryKeySelective(UserDataTbl record);

    /**
     * 修改
     * @param record 用户对象
     * @return
     */
    int updateByPrimaryKey(UserDataTbl record);
    
    /**
	 * 课程视频的 分页逻辑
	 * 
	 * @param record
	 * @param limit_offset
	 *            偏离量
	 * @param limit_rows
	 *            显示行数
	 * @param orderBy_expression
	 *            排序字段
	 * @param orderBy_rule
	 *            排序规则
	 * @return
	 */
	List<UserDataTbl> find(@Param("UserData") UserDataTbl record,
			@Param("limit_offset") long limit_offset, @Param("limit_rows") long limit_rows,
			@Param("orderBy_expression") String orderBy_expression,@Param("orderBy_rule") String orderBy_rule);

	/**
	 * 合计
	 */
	int selectCurriculumCount(UserDataTbl record);
}