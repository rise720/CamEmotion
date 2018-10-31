package com.cac.CamEmotion.dao;

import com.cac.CamEmotion.model.SequenceTbl;

public interface SequenceTblMapper {
    int deleteByPrimaryKey(String key);

    int insert(SequenceTbl record);

    int insertSelective(SequenceTbl record);

    SequenceTbl selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(SequenceTbl record);

    int updateByPrimaryKey(SequenceTbl record);
}