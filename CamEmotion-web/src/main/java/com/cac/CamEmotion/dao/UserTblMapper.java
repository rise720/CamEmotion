package com.cac.CamEmotion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cac.CamEmotion.model.UserTbl;
import com.cac.CamEmotion.model.UserTblId;
/**
 * 
 * 类功能说明:用户表操作服务
 * <P>
 *     
 * </P>
 *
 * @author	zhangfei
 * @data	2016年10月20日
 */
public interface UserTblMapper {
	/**
	 * 
	 * 方法功能说明: 删除参数列表中指定的用户
	 * <P>
	 *     本方法不会删除用户已产生的业务数据
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月15日
	 * @param userIdList 用户ID列表
	 */
    int deleteByPrimaryKey(UserTblId key);
    /**
	 * 
	 * 方法功能说明: 添加用户
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月15日
	 * @param userTbl 用户信息
	 * @return
	 */
    int insert(UserTbl record);
    /**
	 * 
	 * 方法功能说明: 添加用户
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author zhangsh
	 * @date 2016年6月15日
	 * @param userTbl 用户信息
	 * @return
	 */
    int insertSelective(UserTbl record);

    /**
     * 
     * 方法功能说明:通过员工号和系统号查询员工信息
     * <P>
     *     
     * </P>
     * 
     * @author	zhangfei
     * @date	2016年10月20日
     * @param key
     * @return
     */
    UserTbl selectByPrimaryKey(UserTblId key);
    /**
     * 
     * 方法功能说明:根据系统号和用户id更新用户信息带判空条件
     * <P>
     *     
     * </P>
     * 
     * @author	zhangfei
     * @date	2016年10月20日
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UserTbl record);

    /**
     * 
     * 方法功能说明:根据系统号和用户id更新用户信息带判空条件
     * <P>
     *     
     * </P>
     * 
     * @author	zhangfei
     * @date	2016年10月20日
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserTbl record);

    /**
     * 
     * 方法功能说明:带条件查询用户列表
     * <P>
     *     
     * </P>
     * 
     * @author	zhangfei
     * @date	2016年10月20日
     * @param record
     * @param limit_offset
     * @param limit_rows
     * @param orderBy_expression
     * @param orderBy_rule
     * @return
     */
    List<UserTbl> find(@Param("user") UserTbl record, 
    		@Param("limit_offset") long limit_offset, 
    		@Param("limit_rows") long limit_rows, 
    		@Param("orderBy_expression") String orderBy_expression, 
    		@Param("orderBy_rule") String orderBy_rule);
    /**
     * 
     * 方法功能说明:查询用户总条数
     * <P>
     *     
     * </P>
     * 
     * @author	zhangfei
     * @date	2016年10月20日
     * @param record
     * @return
     */
    int findCount(UserTbl record);
}