/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.io.Serializable;

import com.cac.CamEmotion.paging.PageExtend;

/**
 * 
 * 类功能说明: 
 * <P>
 * 
 * </P>
 *
 * @author Chenyang
 * @data 2017年9月15日
 */
public class CurriculimEvaluateExtend extends PageExtend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer curriculumid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(Integer curriculumid) {
		this.curriculumid = curriculumid;
	}
}
