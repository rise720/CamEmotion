
package com.cac.CamEmotion.dao;

import java.util.List;

import com.cac.CamEmotion.model.FaceContrastConfig;
import com.cac.CamEmotion.model.FaceContrastConfigExtend;

public interface FaceContrastConfigMapper {
	int deleteByPrimaryKey(Integer hostno);

	int insert(FaceContrastConfig record);

	int insertSelective(FaceContrastConfig record);

	FaceContrastConfig selectByPrimaryKey(Integer hostno);

	int updateByPrimaryKeySelective(FaceContrastConfig record);

	int updateByPrimaryKey(FaceContrastConfig record);

	List<FaceContrastConfigExtend> findList(Integer hostno);
}