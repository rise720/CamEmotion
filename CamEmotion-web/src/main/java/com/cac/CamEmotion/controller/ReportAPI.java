package com.cac.CamEmotion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.date.DateStyle;
import com.cac.CamEmotion.date.DateUtil;
import com.cac.CamEmotion.jsonModel.ComprehensiveReportExtend;
import com.cac.CamEmotion.model.ComprehensiveReportTbl;
import com.cac.CamEmotion.paging.PageList;
import com.cac.CamEmotion.service.ReportService;
import com.cac.CamEmotion.springmvc.model.Response;
import com.csvreader.CsvWriter;

@RestController
@RequestMapping("/rest/Report")
public class ReportAPI {
	private static Logger logger = LogManager.getLogger(ReportAPI.class);
	/**
	 * 表情数据分析管理
	 */
	@Resource
	private ReportService reportService;

	/**
	 * 查询列表
	 * 
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/GetComprehensiveReportList", method = RequestMethod.POST)
	public Response<PageList<ComprehensiveReportTbl>> GetList(HttpServletRequest request, @RequestBody ComprehensiveReportExtend pe) {
		try {
			ComprehensiveReportTbl record = new ComprehensiveReportTbl();
			if (pe == null || pe.getExpression() == null || pe.getExpression() == null) {
				return new Response<PageList<ComprehensiveReportTbl>>().failure("300", "参数有误");
			}
			
			if(pe.getCurriculumLevel() != null && pe.getCurriculumLevel() >= 0){
				record.setCurriculumLevel(pe.getCurriculumLevel());
			}
			
			if(pe.getClassNature() != null)
				record.setClassNature(pe.getClassNature());
			
			if(pe.getCoursename() != null)
				record.setCoursename(pe.getCoursename());
			
			if (pe.getContext() != null && pe.getContext().length() > 0) {
				String searchcontent_sql = "";
				if (pe.getContext().indexOf(" ") != -1) {
					String[] Searchcontents = pe.getContext().split(" ");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else if (pe.getContext().indexOf(",") != -1) {
					String[] Searchcontents = pe.getContext().split(",");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else {
					searchcontent_sql += " AND INSTR(searchcontent,'" + pe.getContext() + "')>0 ";
				}
				record.setSearchcontent(searchcontent_sql);
			}
			if (pe.getCurriculumid() != null && pe.getCurriculumid() > 0) {
				record.setId(pe.getCurriculumid());
			}
			PageList<ComprehensiveReportTbl> list = reportService.GetList(record, pe.getCurrentPage(), pe.getPageRecorders(), pe.getExpression(), pe.getRule());
			return new Response<PageList<ComprehensiveReportTbl>>().success(list);
		} catch (Exception e) {
			logger.error(e);
			return new Response<PageList<ComprehensiveReportTbl>>().failure("400", "查询失败：" + e.getMessage());
		}
	}
	
	/**
	 * 导出CSV文件
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ExportCsv", method = RequestMethod.GET)
	public Response<List<ComprehensiveReportTbl>> ExportCsv(HttpServletRequest request, HttpServletResponse getResponse, String rule, String expression, String context) {
		try {
			ComprehensiveReportTbl record = new ComprehensiveReportTbl();
			if (rule == null || rule.length() <= 0 || expression == null || expression.length() <= 0) {
				return new Response<List<ComprehensiveReportTbl>>().failure("300", "参数有误");
			}
			if (context != null && context.length() > 0) {
				String searchcontent_sql = "";
				if (context.indexOf(" ") != -1) {
					String[] Searchcontents = context.split(" ");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else if (context.indexOf(",") != -1) {
					String[] Searchcontents = context.split(",");
					for (int i = 0; i < Searchcontents.length; i++) {
						if (Searchcontents[i].length() > 0) {
							searchcontent_sql += " AND INSTR(searchcontent,'" + Searchcontents[i] + "')>0 ";
						}
					}
				} else {
					searchcontent_sql += " AND INSTR(searchcontent,'" + context + "')>0 ";
				}
				record.setSearchcontent(searchcontent_sql);
			}
			List<ComprehensiveReportTbl> list = reportService.GetLists(record, 0, 100000, expression, rule);
			String fileName = "" + System.currentTimeMillis();
			// 写入临时文件
			File tempFile = File.createTempFile("vehicle", ".csv");
			CsvWriter csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("gb2312"));
			// 写表头
			String[] headers = { "课堂ID", "开课时间", "教师姓名", "学科", "类型", "课堂内容", "教育级别", "学年", 
					"班级", "讨论率", "练习率", "听课率", "思考占比", "倾听占比", "活跃占比"};
			csvWriter.writeRecord(headers);
			for (ComprehensiveReportTbl stu : list) {
				csvWriter.write(stu.getId() + "");
				String starttime = stu.getStarttime() + "";
				if (starttime.length() > 19) {
					starttime = starttime.substring(0, 19);
				}
				csvWriter.write(DateUtil.StringToString(starttime, DateStyle.YYYY_MM_DD_HH_MM_CN));
				csvWriter.write(stu.getTeachername());
				csvWriter.write(stu.getCoursename());
				csvWriter.write(stu.getClassNature());
				csvWriter.write(stu.getCoursecontents());
				csvWriter.write(EducationLevel(stu.getEducationLevel()));
				csvWriter.write(stu.getGradeno());
				csvWriter.write(stu.getClassno());
				csvWriter.write(stu.getDiscussionRate());
				csvWriter.write(stu.getPracticeRate());
				csvWriter.write(stu.getAttendanceRate());
				
				csvWriter.write(stu.getPuzzledRate());
				csvWriter.write(stu.getCalmRate());
				csvWriter.write(stu.getExcitementRate());

				csvWriter.endRecord();
			}
			csvWriter.close();
			/**
			 * 写入csv结束，写出流
			 */
			OutputStream out = getResponse.getOutputStream();
			byte[] b = new byte[10240];
			File fileLoad = new File(tempFile.getCanonicalPath());
			getResponse.reset();
			getResponse.setContentType("application/csv;charset=utf-8");
			getResponse.setHeader("content-disposition", "attachment; filename=" + new String((fileName + ".csv").getBytes(), "iso-8859-1"));
			long fileLength = fileLoad.length();
			String length1 = String.valueOf(fileLength);
			getResponse.setHeader("Content_Length", length1);
			FileInputStream in = new FileInputStream(fileLoad);
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}

	private String EducationLevel(Integer value) {

		String educationLevelZH = "小学";
		if (value == null || value <= 0) {
			return educationLevelZH;
		}
		switch (value) {
		case 1:
			educationLevelZH = "小学";
			break;
		case 2:
			educationLevelZH = "初中";
			break;
		case 3:
			educationLevelZH = "高中";
			break;
		case 4:
			educationLevelZH = "中专";
			break;
		case 5:
			educationLevelZH = "大专";
			break;
		case 6:
			educationLevelZH = "本科";
			break;
		default:
			break;
		}
		return educationLevelZH;
	}
}