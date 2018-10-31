package com.cac.CamEmotion.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.ComparativeAnalysisModel;
import com.cac.CamEmotion.jsonModel.ComprehensiveEvaluationJson;
import com.cac.CamEmotion.jsonModel.PDFModel;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmojiQueryModel;
import com.cac.CamEmotion.model.EmojiReportData;
import com.cac.CamEmotion.model.EmotionComparativeAnalysisData;
import com.cac.CamEmotion.service.CurriculumService;
import com.cac.CamEmotion.service.EmotionalDataService;
import com.google.gson.Gson;

@RestController
@RequestMapping("/rest/EmotionalData")
public class EmotionalDataAPI {
	private static Logger logger = LogManager.getLogger(EmotionalDataAPI.class);

	/**
	 * 表情数据分析管理
	 */
	@Resource
	private EmotionalDataService emotionalDataService;
	
	@Resource
	private CurriculumService curriculumService;
	
	/**
	 * 
	 * 方法功能说明:
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author	jinwh
	 * @date	2017年9月15日
	 * @return
	 */
	@RequestMapping(value = "/EmojiReport")
	public Response<EmojiReportData> emojiReport(HttpServletRequest request, String curriculumid){
		try{
			 List<EmojiReportData> emojiReportDataList = emotionalDataService.getEmojiReportData(new EmojiQueryModel(1, Integer.parseInt(curriculumid), 0));
			if(emojiReportDataList == null || emojiReportDataList.size() == 0)
				return new Response<EmojiReportData>().failure("数据为空"); 
			return new Response<EmojiReportData>().success(emojiReportDataList.get(0));
		}catch(Exception e){
			logger.error(e.getMessage());
			e.printStackTrace();
			return new Response<EmojiReportData>().failure("生成统计数据失败");
		}
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 生成PDF文件
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月5日
	 * @param request
	 * @param pdfModel
	 * @return
	 *
	 */
	@RequestMapping(value = "/createFile", method = RequestMethod.POST)
	public Response<String> downloadPDF(HttpServletRequest request, String params){
		String pdfName = "my.pdf";
		Gson gson = new Gson();
		PDFModel pdfModel = gson.fromJson(params, PDFModel.class);
		pdfName = emotionalDataService.createFilePDF(pdfModel);
		return new Response<String>().success(pdfName);
	}
	
	/**
	 * 
	 * 
	 * 方法功能说明: 导出文件PDF
	 * <P>
	 *     
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2017年12月5日
	 * @param request
	 * @param fileName
	 * @return
	 * @throws IOException
	 *new String("课堂评测分析报告".getBytes("UTF-8"),"ISO-8859-1")
	 */
	@RequestMapping("/exportFile")  
    public ResponseEntity<byte[]> export(HttpServletRequest request, String fileName) throws IOException {  
        HttpHeaders headers = new HttpHeaders();    
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); 
        headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(Constants.DOWNLOAD_TEMP + fileName)), 
                                              headers, HttpStatus.CREATED);    
    }  
    
    /**
     * 
     * 
     * 方法功能说明: 课程对比分析
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年1月5日
     * @param curriculumTbl
     * @return
     *
     */
    @RequestMapping("/comparativeAnalysis")
    public Response<List<EmotionComparativeAnalysisData>> lessonComparativeAnalysis(
    		@RequestBody List<ComparativeAnalysisModel> analysisModels){
    	List<EmotionComparativeAnalysisData> eAnalysisDatas = null;
    	try {
    		eAnalysisDatas = emotionalDataService.getComparativeAnalysis(analysisModels);
    		return new Response<List<EmotionComparativeAnalysisData>>().success(eAnalysisDatas);
		} catch (Exception e) {
			logger.error("生成报表错误：" + e.getMessage());
			return new Response<List<EmotionComparativeAnalysisData>>().failure("生成报表错误");
		}
    }
    /**
     * 
     * 
     * 方法功能说明: 整堂课堂评价
     * <P>
     *     
     * </P>
     * 
     * @author chenyang
     * @date 2018年4月23日
     * @param curriculumTbl
     * @return
     *
     */
    @RequestMapping("/ComprehensiveEvaluation")
    public Response<ComprehensiveEvaluationJson> classComprehensiveEvaluation(@RequestBody CurriculumTbl curriculumTbl){
    	ComprehensiveEvaluationJson cEvaluationJson = emotionalDataService.classComprehensiveEvaluation(curriculumTbl);
    	if(cEvaluationJson == null)
    		return new Response<ComprehensiveEvaluationJson>().failure("数据为空");
    	return new Response<ComprehensiveEvaluationJson>().success(cEvaluationJson);
    }
    
    
    
}