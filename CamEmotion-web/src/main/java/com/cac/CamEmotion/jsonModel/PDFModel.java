/**
 * 
 */
package com.cac.CamEmotion.jsonModel;

import java.util.List;

/**
 * 
 * 类功能说明:
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2017年12月1日
 */
public class PDFModel {
	private int id;
	
	private List<ReportData> datas;
	
	private ComprehensiveEvaluationJson cEvaluation;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public List<ReportData> getDatas() {
		return datas;
	}


	public void setDatas(List<ReportData> datas) {
		this.datas = datas;
	}


	public ComprehensiveEvaluationJson getcEvaluation() {
		return cEvaluation;
	}


	public void setcEvaluation(ComprehensiveEvaluationJson cEvaluation) {
		this.cEvaluation = cEvaluation;
	}
	
}
