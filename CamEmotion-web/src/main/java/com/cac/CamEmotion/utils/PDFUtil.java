/**
 * 
 */
package com.cac.CamEmotion.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.PDFModel;
import com.cac.CamEmotion.jsonModel.ReportData;
import com.cac.CamEmotion.model.CurriculumTbl;
import com.cac.CamEmotion.model.EmotiondataSummaryTbl;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * 类功能说明:
 * <P>
 * 
 * </P>
 *
 * @author chenyang
 * @data 2017年11月29日
 */
public class PDFUtil {

	private static float AXIS_W = 285f;// 气泡图，折线图图片大小：宽
	private static float AXIS_H = 110f;// 气泡图，折线图图片大小：高
	private static float ANNULAR_W = 145f;// 环形图图片大小：宽
	private static float ANNULAR_H = 110f;// 环形图图片大小：高
	private static float JIANJU = 2f;// 间距
	private static float INDENTATION_LEFT = 80f;// 文本左边间距

	private static float INDENTATION8 = 430f;// 整个页面显示区域的宽度

	private static float fengexian = 645f; // 蓝色分割线位置

	private static float baobiao_top = 10f; // 报表图片上方纯色白条高度

	// 报表图片位置
	private static float baobiao0 = 450f;
	private static float baobiao1 = 250f;

	private static float baobiao2 = 750f;

	private static float baobiao3 = 465f;
	private static float baobiao4 = 225f;

	private static float baobiao5 = 710f;
	private static float baobiao6 = 470f;
	private static float baobiao7 = 225f;

	// 波动率，最大值 最小值 平均值相对间距
	private static float rightIcon1 = 25f;
	private static float rightIcon2 = 60f;

	private static float rightIcon3 = 80f;
	private static float rightIcon4 = 60f;

	private static float rightIcon5 = 25f;
	private static float rightIcon6 = 20f;

	private static float rightIcon7 = 80f;
	private static float rightIcon8 = 20f;
	// 波动率，最大值 最小值 平均值中的文字和图标间隔
	private static float rightIconAndText0 = 20f;
	private static float rightIconAndText1 = 10f;
	private static float rightIconAndText2 = 0f;

	// private static float tableTotalWidth = 700f;

	// 字体大小
	private static float titleSize = 18f;
	private static float zwSize = 10f;
	private static Image image;

	// 第一个字段和第二个字段之间的总间隔字符数
	private static int charCount1 = 24;
	// 第二个字段和第三个字段之间的总间隔字符数
	private static int charCount2 = 24;

	private static int textLength = 20;
	private static int textLength2 = 8;

	// 一个document.add(Chunk.NEWLINE)上下间距15f
	// 一个中文字 占 两个空格

	public static void pdf(CurriculumTbl curriculumTbl, PDFModel pdfModel, List<EmotiondataSummaryTbl> eSummaryTbls ,String pdfPath) {
		try {
			boolean totalB = true;
			
			String school = "学校：" + (curriculumTbl.getSchool() == null ? "" : curriculumTbl.getSchool().trim());
			String gradeno = "年级：" + (curriculumTbl.getGradeno() == null ? "" : curriculumTbl.getGradeno().trim());
			String classno = (curriculumTbl.getClassno() == null ? "" : curriculumTbl.getClassno().trim());
			String teachername = "教师："
					+ (curriculumTbl.getTeachername() == null ? "" : curriculumTbl.getTeachername().trim());
			String coursename = "学科："
					+ (curriculumTbl.getCoursename() == null ? "" : curriculumTbl.getCoursename().trim());
			String coursecontents = "课程内容："
					+ (curriculumTbl.getCoursecontents() == null ? "" : curriculumTbl.getCoursecontents().trim());
			String courseType = "课堂类型："
					+ (curriculumTbl.getClassNature() == null ? "" : curriculumTbl.getClassNature().trim());

			String noteMsg = "备注：" + (curriculumTbl.getNotemsg() == null ? "" : curriculumTbl.getNotemsg().trim());

			if (classno != null && classno.trim().length() > 0) {
				gradeno = gradeno + "(" + classno + ")";
			}

			if (teachername.length() > textLength2) {
				teachername = teachername.substring(0, textLength2) + "...";
			}
			if (school.length() > textLength) {
				school = school.substring(0, textLength) + "...";
			}
			if (coursecontents.length() > textLength) {
				coursecontents = coursecontents.substring(0, textLength) + "...";
			}
			if (noteMsg.length() > textLength) {
				noteMsg = noteMsg.substring(0, textLength) + "...";
			}
			
			if(eSummaryTbls == null || eSummaryTbls.size() == 0 || pdfModel.getcEvaluation() == null){
				totalB = false;
				baobiao0 = 545;
				baobiao1 = 305;
				baobiao2 = 840;
			}else{
				totalB = true;
				baobiao0 = 450f;
				baobiao1 = 210f;
				baobiao2 = 750f;
			}
			
			// Step 1: 实例化文档对象，设置文档背景，大小等
			Rectangle rectPageSize = new Rectangle(PageSize.A4);// A4纸张
			rectPageSize.setBackgroundColor(BaseColor.WHITE);// 文档的背景色
			// 创建文件
			// 创建一个文档对象，设置初始化大小和页边距
			Document document = new Document(rectPageSize, INDENTATION_LEFT, INDENTATION_LEFT, 50, 50);// 上、下、左、右间距
			// 建立一个书写器
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
			// 打开文件
			document.open();
			PdfContentByte pcb = writer.getDirectContent();
			// 中文字体,解决中文不能显示问题
			BaseFont bfChinese = BaseFont.createFont(Constants.SYSTEM_RESOURCE_PATH + "simsun.ttc,1",
					BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			// 黑色字体
			Font titleFontB = new Font(bfChinese);
			titleFontB.setColor(BaseColor.BLACK);
			titleFontB.setStyle(Font.BOLD);
			titleFontB.setSize(titleSize);

			Font zwtextFontB = new Font(bfChinese);
			zwtextFontB.setColor(BaseColor.BLACK);
			zwtextFontB.setStyle(Font.BOLD);
			zwtextFontB.setSize(zwSize);

			Font zwtextFont = new Font(bfChinese);
			zwtextFont.setColor(BaseColor.BLACK);
			zwtextFont.setSize(zwSize);

			Font zwtextBigFont = new Font(bfChinese);
			zwtextBigFont.setColor(BaseColor.RED);
			zwtextBigFont.setSize((float) (zwSize * 1.5));

			Font zwtextSmallFont = new Font(bfChinese);
			zwtextSmallFont.setColor(BaseColor.BLACK);
			zwtextSmallFont.setSize((float) (zwSize * 0.1));
			// *************************************** 标题开始 *******************************************
			// 段落文本
			Paragraph paragraphTitle = new Paragraph(Constants.SYSTEM_NAME, titleFontB);
			paragraphTitle.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphTitle);
			// ************************************** 标题结束 *******************************************
			document.add(Chunk.NEWLINE);
			// ************************************ 课程信息开始 *****************************************
			// 教师
			StringBuffer classinfoText1 = new StringBuffer();
			classinfoText1.append(teachername);

			for (int i = 0; i < charCount1 - getTextLength(teachername); i++) {
				classinfoText1.append(" ");
			}
			// 年级 班级
			classinfoText1.append(gradeno);
			for (int i = 0; i < charCount2 - getTextLength(gradeno); i++) {
				classinfoText1.append(" ");
			}
			// 学校
			classinfoText1.append(school);

			// 学科
			StringBuffer classinfoText2 = new StringBuffer();
			classinfoText2.append(coursename);
			for (int i = 0; i < charCount1 - getTextLength(coursename); i++) {
				classinfoText2.append(" ");
			}
			// 课程性质
			classinfoText2.append(courseType);
			for (int i = 0; i < charCount2 - getTextLength(courseType); i++) {
				classinfoText2.append(" ");
			}
			// 课程内容
			classinfoText2.append(coursecontents);

			StringBuffer classinfoText3 = new StringBuffer();
			// 备注
			classinfoText3.append(noteMsg);

			Paragraph classinfo1 = new Paragraph(classinfoText1.toString(), zwtextFont);
			document.add(classinfo1);
			document.add(Chunk.NEWLINE);
			Paragraph classinfo2 = new Paragraph(classinfoText2.toString(), zwtextFont);
			document.add(classinfo2);
			document.add(Chunk.NEWLINE);
			Paragraph classinfo3 = new Paragraph(classinfoText3.toString(), zwtextFont);
			document.add(classinfo3);
			document.add(Chunk.NEWLINE);
			// ************************************ 课程信息结束 ******************************************
			document.add(Chunk.NEWLINE);
			// ************************************ 蓝色分界线开始 ****************************************
			image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "cs_blue.png");
			// 设置图片位置的x轴和y轴
			image.setAbsolutePosition(INDENTATION_LEFT, fengexian);
			// 设置图片的宽度和高度
			image.scaleAbsolute(INDENTATION8, 0.1f);
			// 将图片1添加到pdf文件中
			document.add(image);
			// document.add(Chunk.NEWLINE);
			// ************************************* 蓝色分界线结束 ***************************************
			
			// ************************************* 综合评价开始 *****************************************
			if(totalB){
				Paragraph ztTitleP = new Paragraph("整体评价", zwtextFontB);
				document.add(ztTitleP);
				document.add(Chunk.NEWLINE);
				
				List<String> allpjDatas = new ArrayList<>();
				allpjDatas.add("指标类型");
				allpjDatas.add("评价");
//				allpjDatas.add("星级");
				
				allpjDatas.add("课堂气氛活跃程度");
				allpjDatas.add(wholEvaluateConvert("activityRate", pdfModel.getcEvaluation().getActivityLevel()));
				allpjDatas.add("课堂参与度");
				allpjDatas.add(wholEvaluateConvert("participation", pdfModel.getcEvaluation().getEmotionCHLevel()));
				allpjDatas.add("授课类型");
				allpjDatas.add(wholEvaluateConvert("tActionRate", pdfModel.getcEvaluation().getTeachTypeLevel()));
				
				float[] cellPersP = new float[] { 1, 1 };
				
				PdfPTable pjtable = setPdfPTable0(cellPersP, allpjDatas, zwtextFont);
				document.add(pjtable);
				
				//五角星
//				for (int i = 1; i <= 5; i++) {
//					if(pdfModel.getcEvaluation().getActivityLevel() >= i){
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_yellow.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 560);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}else{
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_grey.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 560);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}
//					
//					if(pdfModel.getcEvaluation().getInteractionFrequencyLevel() >= i){
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_yellow.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 545);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}else{
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_grey.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 545);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}
//					
//					if(pdfModel.getcEvaluation().getSenceRatioLevel() >= i){
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_yellow.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 530);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}else{
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "star_grey.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(410 + ((i-1) * 12), 530);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(10, 10);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//					}
//				}
			}
			//************************************ 综合评价结束 ******************************************
			List<String> cellDatas;
			float[] cellPers = null;
			List<String[]> cellDatas2;
			int dataLength = pdfModel.getDatas().size() - 2;
			for (int i = 0; i < dataLength; i++) {
				cellDatas = new ArrayList<>();
				cellDatas2 = new ArrayList<>();
				
				//报表分类title
				Paragraph classtify = new Paragraph(switchStr(i), zwtextFontB);
				document.add(classtify);
				//报表解说
				Paragraph jieduP = new Paragraph(switchJDStr(i), zwtextFont);
				document.add(jieduP);
				//换行（放报表图片）
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				document.add(Chunk.NEWLINE);
				if( i != 1){
//					//指定光标所在页码
//					 document.newPage();
//					 document.setPageCount(1);
					document.add(Chunk.NEWLINE);
				}
				if(!totalB && i == 1){
					document.add(Chunk.NEWLINE);
				}

				float baobiaoF = 0;
				ReportData reportData;
				File file;
				// 底色
				String puzAv = "";
				String camAv = "";
				String exiAv = "";
				
				String wareAv = "";
				String fuAv = "";
				String umAv = "";
				String avgAv = "";
				
				String lisAv = "";
				String paAv = "";
				String disAv = "";
				
				for (EmotiondataSummaryTbl  eSummaryTbl : eSummaryTbls) {
					if(eSummaryTbl.getCharttype().equals("valencePie") && eSummaryTbl.getLevels() == 0){
						puzAv = eSummaryTbl.getPercent() + "%";
					}
					if(eSummaryTbl.getCharttype().equals("valencePie") && eSummaryTbl.getLevels() == 1){
						camAv = eSummaryTbl.getPercent() + "%";
					}
					if(eSummaryTbl.getCharttype().equals("valencePie") && eSummaryTbl.getLevels() == 2){
						exiAv = eSummaryTbl.getPercent() + "%";
					}
					
//					if(eSummaryTbl.getCharttype().equals("waveRate") && eSummaryTbl.getLevels() == 0){
//						wareAv = eSummaryTbl.getPercent() + "%";
//					}
//					if(eSummaryTbl.getCharttype().equals("valenceFluctuationDeviation") && eSummaryTbl.getLevels() == 0){
//						fuAv = eSummaryTbl.getPercent();
//					}
//					if(eSummaryTbl.getCharttype().equals("uniformFluctuation") && eSummaryTbl.getLevels() == 0){
//						umAv = eSummaryTbl.getPercent();
//					}
//					if(eSummaryTbl.getCharttype().equals("valenceAverage") && eSummaryTbl.getLevels() == 0){
//						avgAv = eSummaryTbl.getPercent();
//					}
					
					if(eSummaryTbl.getCharttype().equals("faceCountPie") && eSummaryTbl.getLevels() == 0){
						disAv = eSummaryTbl.getPercent();
					}
					if(eSummaryTbl.getCharttype().equals("faceCountPie") && eSummaryTbl.getLevels() == 1){
						paAv = eSummaryTbl.getPercent();
					}
					if(eSummaryTbl.getCharttype().equals("faceCountPie") && eSummaryTbl.getLevels() == 2){
						lisAv = eSummaryTbl.getPercent();
					}
				}
				
				if (i == 0) {
					cellDatas.add("右图参数说明");
					cellDatas.add("分析结果");
					
					if(totalB){
						cellDatas.add("参考值");
						cellPers = new float[] { 1, 1,1 };
					}else{
						cellPers = new float[] { 1,1 };
					}
					
					reportData = pdfModel.getDatas().get(i + 1);
					baobiaoF = baobiao0;
					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "cs_yellow.png");
					
					cellDatas.add("思考（蓝色）");
					cellDatas.add(reportData.getDataVal1() + "%");
					if(totalB){
						cellDatas.add(puzAv);
					}
					
					
					cellDatas.add("倾听（绿色）");
					cellDatas.add(reportData.getDataVal2() + "%");
					if(totalB){
						cellDatas.add(camAv);
					}
					
					
					cellDatas.add("活跃（红色）");
					cellDatas.add(reportData.getDataVal3() + "%");
					if(totalB){
						cellDatas.add(exiAv);
					}
					
					
				} else if (i == 1) {
//					cellDatas.add("右图参数说明");
//					cellDatas.add("分析结果");
//					
//					if(totalB){
//						cellDatas.add("参考值");
//						cellDatas.add("评价");
//						cellPers = new float[] { 1,1,1,1 };
//					}else{
//						cellPers = new float[] { 1,1 };
//					}
//					
//					
//					reportData = pdfModel.getDatas().get(i + 1);
					baobiaoF = baobiao1;
					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "cs_green.png");
//
//					cellDatas.add("波动率");
//					cellDatas.add(reportData.getDataVal1() + "%");
//					
//					if(totalB){
//						cellDatas.add(wareAv);
//						cellDatas.add(wholEvaluateConvert("waveRate", pdfModel.getcEvaluation().getWaveRateLevel()));
//					}
//					
//					
//					cellDatas.add("波动偏差");
//					cellDatas.add(reportData.getDataVal3());
//					if(totalB){
//						cellDatas.add(fuAv);
//						cellDatas.add(wholEvaluateConvert("fluctuationDeviation",pdfModel.getcEvaluation().getFluctuationDeviationLevel()));
//					}
//					
//					
//					cellDatas.add("波动均衡");
//					cellDatas.add(reportData.getDataVal4() + "%");
//					if(totalB){
//						cellDatas.add(umAv + "%");
//						cellDatas.add(wholEvaluateConvert("uniformFluctuation",pdfModel.getcEvaluation().getUniformFluctuationLevel()));
//					}
//					
//					
//					cellDatas.add("平均值");
//					cellDatas.add(reportData.getDataVal2());
//					if(totalB){
//						cellDatas.add(avgAv);
//						cellDatas.add(" ");
//					}
					

				} else if (i == 2) {
					cellDatas.add("右图参数说明");
					cellDatas.add("分析结果");
					if(totalB){
						cellDatas.add("参考值");
						cellPers = new float[] { 1,1,1 };
					}else{
						cellPers = new float[] { 1,1 };
					}
					
					
					reportData = pdfModel.getDatas().get(i + 2);
					baobiaoF = baobiao2;
					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "cs_orange.png");
					
					cellDatas.add("讨论");
					cellDatas.add(reportData.getDataVal1() + "%");
					if(totalB){
					cellDatas.add(disAv + "%");
					}
					
					cellDatas.add("练习");
					cellDatas.add(reportData.getDataVal2() + "%");
					if(totalB){
						cellDatas.add(paAv + "%");
					}
					
					cellDatas.add("听课");
					cellDatas.add(reportData.getDataVal3() + "%");
					if(totalB){
					cellDatas.add(lisAv + "%");
					}

				} else {
					baobiaoF = baobiao7;
					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "cs_green.png");
				}

				// ************************************** 报表开始 *******************************************
				
				if(i == 1) {
					// 设置图片的宽度和高度
					image.scaleAbsolute(AXIS_W + 145, baobiao_top);
				}else {
					// 设置图片的宽度和高度
					image.scaleAbsolute(AXIS_W, baobiao_top);					
				}
				// 设置图片位置的x轴和y轴
				image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF);
				// 将图片1添加到pdf文件中
				document.add(image);
				
				
				// 第二次
				if(i != 1) {
					// 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + JIANJU, baobiaoF);
					// 设置图片的宽度和高度
					image.scaleAbsolute(ANNULAR_W, baobiao_top);
					// 将图片1添加到pdf文件中
					document.add(image);	
				}

				// 报表图片
				if (i == 0) {
					reportData = pdfModel.getDatas().get(i);
					file = reportData.getImageFile();

					image = Image.getInstance(file.toURI().toURL());
					// // 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF - AXIS_H + baobiao_top);
					// 设置图片的宽度和高度
					
//					AXIS_H = AXIS_W * image.getHeight() / image.getWidth();
					
					image.scaleAbsolute(AXIS_W, AXIS_H);
					// 将图片1添加到pdf文件中
					document.add(image);

					reportData = pdfModel.getDatas().get(i + 1);
					file = reportData.getImageFile();

					image = Image.getInstance(file.toURI().toURL());
					// // 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + JIANJU, baobiaoF - AXIS_H + baobiao_top);
					// 设置图片的宽度和高度
					
//					ANNULAR_H = ANNULAR_W * image.getHeight() / image.getWidth();
					
					image.scaleAbsolute(ANNULAR_W, ANNULAR_H);
					// 将图片1添加到pdf文件中
					document.add(image);

				} else if (i == 1) {
					reportData = pdfModel.getDatas().get(i + 1);
					file = reportData.getImageFile();

					image = Image.getInstance(file.toURI().toURL());
					// // 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF - AXIS_H + baobiao_top);
					// 设置图片的宽度和高度
					
//					AXIS_H = AXIS_W * image.getHeight() / image.getWidth();
					
					image.scaleAbsolute(AXIS_W + 160f, AXIS_H);
					// 将图片1添加到pdf文件中
					document.add(image);

//					pcb.beginText();
//					pcb.setRGBColorFill(255, 255, 255);// 设定文本颜色
//					// pcb.setColorFill(BaseColor.WHITE);
//					pcb.setFontAndSize(bfChinese, 5);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + JIANJU + 5, baobiaoF + 3);
//					pcb.showText("情绪指数");
//					pcb.endText();
//					pcb.resetRGBColorFill();
//
//					// 自定义报表的图片文字
//					// 波动率
//					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-1.png");
//					// 设置图片位置的x轴和y轴
//					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon1,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon2);
//					// 设置图片的宽度和高度
//					image.scaleAbsolute(16, 16);
//					// 将图片1添加到pdf文件中
//					document.add(image);
//
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 5);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon1 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon2 + rightIconAndText1);
//					pcb.showText("波动率");
//					pcb.endText();
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 8);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon1 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon2 + rightIconAndText2);
//					pcb.showText(reportData.getDataVal1() + "%");
//					pcb.endText();
//
//					// 波动偏差
//					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-3.png");
//					// 设置图片位置的x轴和y轴
//					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon3,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon4);
//					// 设置图片的宽度和高度
//					image.scaleAbsolute(16, 16);
//					// 将图片1添加到pdf文件中
//					document.add(image);
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 5);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon3 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon4 + rightIconAndText1);
//					pcb.showText("波动偏差");
//					pcb.endText();
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 8);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon3 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon4 + rightIconAndText2);
//					pcb.showText("" + reportData.getDataVal3());
//					pcb.endText();
//
//					// 波动均衡
//					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-4.png");
//					// 设置图片位置的x轴和y轴
//					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon5,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon6);
//					// 设置图片的宽度和高度
//					image.scaleAbsolute(16, 16);
//					// 将图片1添加到pdf文件中
//					document.add(image);
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 5);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon5 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon6 + rightIconAndText1);
//					pcb.showText("波动均衡");
//					pcb.endText();
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 8);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon5 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon6 + rightIconAndText2);
//					pcb.showText(reportData.getDataVal4() + "%");
//					pcb.endText();
//
//					// 平均值
//					image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-2.png");
//					// 设置图片位置的x轴和y轴
//					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon7,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon8);
//					// 设置图片的宽度和高度
//					image.scaleAbsolute(16, 16);
//					// 将图片1添加到pdf文件中
//					document.add(image);
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 5);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon7 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon8 + rightIconAndText1);
//					pcb.showText("平均值");
//					pcb.endText();
//					pcb.beginText();
//					pcb.setFontAndSize(bfChinese, 8);
//					pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon7 + rightIconAndText0,
//							baobiaoF - AXIS_H + baobiao_top + rightIcon8 + rightIconAndText2);
//					pcb.showText("" + reportData.getDataVal2());
//					pcb.endText();

				} else if (i == 2) {
					reportData = pdfModel.getDatas().get(i + 1);
					file = reportData.getImageFile();

					image = Image.getInstance(file.toURI().toURL());
					// // 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF - AXIS_H + baobiao_top);
					// 设置图片的宽度和高度
					
//					AXIS_H = AXIS_W * image.getHeight() / image.getWidth();
					
					image.scaleAbsolute(AXIS_W, AXIS_H);
					// 将图片1添加到pdf文件中
					document.add(image);

					reportData = pdfModel.getDatas().get(i + 2);
					file = reportData.getImageFile();

					image = Image.getInstance(file.toURI().toURL());
					// // 设置图片位置的x轴和y轴
					image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + JIANJU, baobiaoF - AXIS_H + baobiao_top);
					// 设置图片的宽度和高度
					
//					ANNULAR_H = ANNULAR_W * image.getHeight() / image.getWidth();
					
					image.scaleAbsolute(ANNULAR_W, ANNULAR_H);
					// 将图片1添加到pdf文件中
					document.add(image);

				} else {
					if (i == 3) {
						reportData = pdfModel.getDatas().get(i + 2);
						file = reportData.getImageFile();

						image = Image.getInstance(file.toURI().toURL());
						// // 设置图片位置的x轴和y轴
						image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF - AXIS_H + baobiao_top);
						// 设置图片的宽度和高度
						
//						AXIS_H = AXIS_W * image.getHeight() / image.getWidth();
						
						image.scaleAbsolute(AXIS_W, AXIS_H);
						// 将图片1添加到pdf文件中
						document.add(image);

						pcb.beginText();
						pcb.setRGBColorFill(255, 255, 255);// 设定文本颜色
						// pcb.setColorFill(BaseColor.WHITE);
						pcb.setFontAndSize(bfChinese, 5);
						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + JIANJU + 5, baobiaoF + 3);
						pcb.showText("情绪指数");
						pcb.endText();
						pcb.resetRGBColorFill();

					} 
					else {
//						reportData = pdfModel.getDatas().get(i + 2);
//						file = reportData.getImageFile();
//
//						image = Image.getInstance(file.toURI().toURL());
//						// // 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(INDENTATION_LEFT, baobiaoF - AXIS_H + baobiao_top);
//						// 设置图片的宽度和高度
//						
////						AXIS_H = AXIS_W * image.getHeight() / image.getWidth();
//						
//						image.scaleAbsolute(AXIS_W, AXIS_H);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//
//						// 自定义报表的图片文字
//						// 波动率
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-1.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon1,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon2);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(16, 16);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 5);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon1 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon2 + rightIconAndText1);
//						pcb.showText("波动率");
//						pcb.endText();
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 8);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon1 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon2 + rightIconAndText2);
//						pcb.showText("" + reportData.getDataVal1());
//						pcb.endText();
//
//						// 平均值
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-2.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon3,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon4);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(16, 16);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 5);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon3 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon4 + rightIconAndText1);
//						pcb.showText("平均值");
//						pcb.endText();
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 8);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon3 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon4 + rightIconAndText2);
//						pcb.showText("" + reportData.getDataVal2());
//						pcb.endText();
//
//						// 最高值
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-3.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon5,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon6);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(16, 16);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 5);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon5 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon6 + rightIconAndText1);
//						pcb.showText("波动偏差1");
//						pcb.endText();
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 8);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon5 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon6 + rightIconAndText2);
//						pcb.showText("" + reportData.getDataVal3());
//						pcb.endText();
//
//						// 最低值
//						image = Image.getInstance(Constants.SYSTEM_RESOURCE_PATH + "bo-4.png");
//						// 设置图片位置的x轴和y轴
//						image.setAbsolutePosition(INDENTATION_LEFT + AXIS_W + rightIcon7,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon8);
//						// 设置图片的宽度和高度
//						image.scaleAbsolute(16, 16);
//						// 将图片1添加到pdf文件中
//						document.add(image);
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 5);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon7 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon8 + rightIconAndText1);
//						pcb.showText("波动均衡");
//						pcb.endText();
//						pcb.beginText();
//						pcb.setFontAndSize(bfChinese, 8);
//						pcb.setTextMatrix(INDENTATION_LEFT + AXIS_W + rightIcon7 + rightIconAndText0,
//								baobiaoF - AXIS_H + baobiao_top + rightIcon8 + rightIconAndText2);
//						pcb.showText("" + reportData.getDataVal4());
//						pcb.endText();
//
					}

				}

				// ************************************** 报表结束 ********************************************

				// ************************************** 表格开始 ********************************************
				if (i == 3) {
					PdfPTable pdfPTable = setPdfPTableDiy(cellDatas2, zwtextFont);
					document.add(pdfPTable);
				} else {
					PdfPTable pdfPTable = setPdfPTable0(cellPers, cellDatas, zwtextFont);
					document.add(pdfPTable);
				}

				document.add(Chunk.NEWLINE);
				// ************************************* 表格结束 *********************************************

			}

			// 关闭文档
			document.close();
			// 关闭书写器
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static String switchStr(int i) {
		String str = "";
		switch (i) {
		case 0:
			str = "活跃度分析";
			break;
		case 1:
			str = "学生情绪分析";
			break;
		case 2:
			str = "教学行为分析";
			break;
		case 3:
			str = "基础表情分析";
			break;
		case 4:
			str = "惊讶分析";
			break;
		case 5:
			str = "困惑分析";
			break;
		case 6:
			str = "皱眉分析";
			break;
		case 7:
			str = "无表情分析";
			break;
		case 8:
			str = "皱眉分析";
			break;
		case 9:
			str = "无表情分析";
			break;
		default:
			break;
		}

		return str;
	}

	private static String switchJDStr(int i) {
		String str = "";
		switch (i) {
		case 0:
			str = "解读方法：时序图：观察课堂上每个时间点（单位为15秒），学生处于思考 、倾听 、活跃的人               数比例。\n"
					+ "          环状图：观察整节课，思考、倾听、活跃的分布比例（人数*时间）。";
			break;
		case 1:
//			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、轻松，虚线下方代处于思考状态、有困惑               情况。可以观察曲线是否波动均匀，是否前后区别较大。\n"
//					+ "          波动率：通过学生的课堂情绪及行为，观察教学节奏。一节课45分钟则频率最大值为               45。\n"
//					+ "          波动偏差：反应整堂课的节奏起伏程度，值越大，说明节奏起伏越大。\n" + ""
//					+ "          波动均衡：整堂课前后波动是否均匀，越接近于1，说明波动越均衡。";
			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、活跃，虚线下方代处于思考状态、有困惑               情况。曲线反应学生情绪起伏的波动变化。\n";
			break;
		case 2:
			str = "解读方法：时序图：观察课堂上每个时间点（分钟为单位），学生是在讨论、听课还是练习。每              次听课及练习的持续时间。\n"
					+ "          环状图 ：观察整节课，讨论、听课和练习的时间分布比例。";
			break;
		case 3:
			str = "解读方法：时序图：厌恶，皱眉，喜悦，惊讶四种表情在整节课过程中的变化波动情况。可以观               察曲线是否波动均匀，是否前后区别较大。\n"
					+ "          波动率：通过学生课堂情绪及行为，观察教学节奏 。一节课45分钟则频率最大为45。\n"
					+ "          峰谷值：对于不在0附近峰谷值，可以比对视频，了解课堂上学生困惑或者轻松的原因。";
			break;
		case 4:
			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、轻松，虚线下方代处于思考状态、有困                 惑情况。可以观察曲线是否波动均匀，是否前后区别较大。\n"
					+ "          波动率：通过学生课堂情绪及行为，观察教学节奏 。一节课45分钟，则频率最大\n"
					+ "          峰谷值：对于不在0附近峰谷值，可以比对视频，了解课堂上学生困惑或者轻松的原因。";
			break;
		case 5:
			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、轻松，虚线下方代处于思考状态、有困                 惑情况。可以观察曲线是否波动均匀，是否前后区别较大。\n"
					+ "          波动率：通过学生课堂情绪及行为，观察教学节奏 。一节课45分钟，则频率最大\n"
					+ "          峰谷值：对于不在0附近峰谷值，可以比对视频，了解课堂上学生困惑或者轻松的原因。";
			break;
		case 6:
			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、轻松，虚线下方代处于思考状态、有困                 惑情况。可以观察曲线是否波动均匀，是否前后区别较大。\n"
					+ "          波动率：通过学生课堂情绪及行为，观察教学节奏 。一节课45分钟，则频率最大\n"
					+ "          峰谷值：对于不在0附近峰谷值，可以比对视频，了解课堂上学生困惑或者轻松的原因。";
			break;
		case 7:
			str = "解读方法：时序图：红色虚线上方代表学生情绪平静、轻松，虚线下方代处于思考状态、有困                 惑情况。可以观察曲线是否波动均匀，是否前后区别较大。\n"
					+ "          波动率：通过学生课堂情绪及行为，观察教学节奏 。一节课45分钟，则频率最大\n"
					+ "          峰谷值：对于不在0附近峰谷值，可以比对视频，了解课堂上学生困惑或者轻松的原因。";
			break;
		default:
			break;
		}

		return str;
	}

	public static PdfPTable setPdfPTable(float[] cellPers, List<String> datas, Font fontChinese) {
		float cellMinimumHeight = 16f;
		PdfPTable table = null;

		table = new PdfPTable(cellPers);// 2列的表格以及单元格的宽度。
		table.setTotalWidth(INDENTATION8);// 表格整体宽度
		table.setLockedWidth(true);
		PdfPCell cell;
		cell = new PdfPCell(new Paragraph("右图参数说明", fontChinese));
		cell.setBackgroundColor(new BaseColor(75, 172, 198));
		cell.setMinimumHeight(cellMinimumHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(15);
		table.addCell(cell);
		cell = new PdfPCell(new Paragraph("分析结果", fontChinese));
		cell.setBackgroundColor(new BaseColor(75, 172, 198));
		cell.setMinimumHeight(cellMinimumHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(15);
		table.addCell(cell);
		for (int i = 0; i < datas.size(); i++) {
			cell = new PdfPCell(new Paragraph(datas.get(i), fontChinese));
			cell.setMinimumHeight(cellMinimumHeight);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseAscender(true);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(15);
			table.addCell(cell);
		}
		return table;
	}
	public static PdfPTable setPdfPTable3(float[] cellPers, List<String> datas, Font fontChinese) {
		float cellMinimumHeight = 16f;
		PdfPTable table = null;

		table = new PdfPTable(cellPers);// 2列的表格以及单元格的宽度。
		table.setTotalWidth(INDENTATION8);// 表格整体宽度
		table.setLockedWidth(true);
		PdfPCell cell;
		cell = new PdfPCell(new Paragraph("指标类型", fontChinese));
		cell.setBackgroundColor(new BaseColor(75, 172, 198));
		cell.setMinimumHeight(cellMinimumHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(15);
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("评价", fontChinese));
		cell.setBackgroundColor(new BaseColor(75, 172, 198));
		cell.setMinimumHeight(cellMinimumHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(15);
		table.addCell(cell);
		
		cell = new PdfPCell(new Paragraph("星级", fontChinese));
		cell.setBackgroundColor(new BaseColor(75, 172, 198));
		cell.setMinimumHeight(cellMinimumHeight);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setUseAscender(true);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setFixedHeight(15);
		table.addCell(cell);
		
		
		for (int i = 0; i < datas.size(); i++) {
			cell = new PdfPCell(new Paragraph(datas.get(i), fontChinese));
			cell.setMinimumHeight(cellMinimumHeight);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseAscender(true);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(15);
			table.addCell(cell);
		}
		return table;
	}
	
	public static PdfPTable setPdfPTable0(float[] cellPers, List<String> datas, Font fontChinese) {
		float cellMinimumHeight = 16f;
		PdfPTable table = null;

		table = new PdfPTable(cellPers);// 2列的表格以及单元格的宽度。
		table.setTotalWidth(INDENTATION8);// 表格整体宽度
		table.setLockedWidth(true);
		PdfPCell cell;
		for (int i = 0; i < datas.size(); i++) {
			cell = new PdfPCell(new Paragraph(datas.get(i), fontChinese));
			if(i < cellPers.length){
				cell.setBackgroundColor(new BaseColor(75, 172, 198));
			}
			cell.setMinimumHeight(cellMinimumHeight);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setUseAscender(true);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(15);
			table.addCell(cell);
		}
		return table;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 自定义表格
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年2月1日
	 * @param datas
	 * @param fontChinese
	 * @return
	 *
	 */
	public static PdfPTable setPdfPTableDiy(List<String[]> datas, Font fontChinese) {
		PdfPTable table = null;
		try {
			table = RowSpanInTable.createRowSpan(datas, fontChinese);
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
		return table;
	}

	/**
	 * 设置相关参数
	 * 
	 * @param document
	 * @return
	 */
	public static Document setParameters(Document document, String title, String subject, String keywords,
			String author, String creator) {
		// 设置标题
		document.addTitle(title);
		// 设置主题
		document.addSubject(subject);
		// 设置作者
		document.addKeywords(keywords);
		// 设置作者
		document.addAuthor(author);
		// 设置创建者
		document.addCreator(creator);
		// 设置生产者
		document.addProducer();
		// 设置创建日期
		document.addCreationDate();

		return document;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 获取字符真实长度
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年1月29日
	 * @param text
	 * @return
	 *
	 */
	public static int getTextLength(String text) {
		int length = 0;
		try {
			length = new String(text.getBytes("gb2312"), "iso-8859-1").length();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 
	 * 
	 * 方法功能说明: 评价说明
	 * <P>
	 * 
	 * </P>
	 * 
	 * @author chenyang
	 * @date 2018年4月24日
	 * @param type
	 * @param levelVal
	 * @return
	 *
	 */
	private static String wholEvaluateConvert(String type, int levelVal) {
		String msg = "未知";
		switch (type) {
		case "activityRate":
			switch (levelVal) {
			case 1:
				msg = "沉闷";
				break;
			case 2:
				msg = "较沉闷";
				break;
			case 3:
				msg = "一般";
				break;
			case 4:
				msg = "较活跃";
				break;
			case 5:
				msg = "活跃";
				break;
			}
			break;
		case "waveRate":
			switch (levelVal) {
			case 1:
				msg = "节奏频率平缓";
				break;
			case 2:
				msg = "节奏频率较平缓";
				break;
			case 3:
				msg = "节奏频率一般";
				break;
			case 4:
				msg = "节奏频率较紧凑";
				break;
			case 5:
				msg = "节奏频率紧凑";
				break;
			}
			break;
		case "fluctuationDeviation":
			switch (levelVal) {
			case 1:
				msg = "节奏起伏小";
				break;
			case 2:
				msg = "节奏起伏较小";
				break;
			case 3:
				msg = "节奏起伏一般";
				break;
			case 4:
				msg = "节奏起伏较大";
				break;
			case 5:
				msg = "节奏起伏大";
				break;
			}
			break;
		case "uniformFluctuation":
			switch (levelVal) {
			case 1:
				msg = "波动均衡失衡";
				break;
			case 2:
				msg = "波动均衡较失衡";
				break;
			case 3:
				msg = "波动均衡一般";
				break;
			case 4:
				msg = "波动均衡较平衡";
				break;
			case 5:
				msg = "波动均衡平衡";
				break;
			}
			break;
		case "interactionFrequency":
			switch (levelVal) {
			case 1:
				msg = "弱";
				break;
			case 2:
				msg = "较弱";
				break;
			case 3:
				msg = "一般";
				break;
			case 4:
				msg = "较好";
				break;
			case 5:
				msg = "好";
				break;
			}
			break;
		case "senceRatio":
			switch (levelVal) {
			case 1:
				msg = "不均衡";
				break;
			case 2:
				msg = "较不均衡";
				break;
			case 3:
				msg = "一般";
				break;
			case 4:
				msg = "较均衡";
				break;
			case 5:
				msg = "均衡";
				break;
			}
			break;
		case "participation":
			switch(levelVal){
			case 1:
				msg = "较差";
				break;
			case 2:
				msg = "一般";
				break;
			case 3:
				msg = "良好";
				break;
			case 4:
				msg = "高效";
				break;
			}
			break;
		case "tActionRate":
			switch(levelVal){
			case 1:
				msg = "练习型";
				break;
			case 2:
				msg = "讲授型";
				break;
			case 3:
				msg = "对话型";
				break;
			case 4:
				msg = "混合型";
				break;
			}
			break;
		default:
			break;
		}
		return msg;

	}

	public static void main(String[] args) {
		String a = "你好123";
		System.out.println(a.length());// 5
		try {
			a = new String(a.getBytes("utf-8"), "iso-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(a.length());// 7

	}
}
