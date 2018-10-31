/**
 * 数据来源
 * 
 * @type {{videoList: string, SequentialData: string, PieChartData: string}}
 */
var camEmotion = {
	CreateCurriculumData : '../../Server/start',//上课
	CloseCurriculumData : '../../Server/stop',//下课
	getCurriculumList : '../../rest/CurriculumData/getCurriculumList',//课堂列表
	lessonVideo : '../../rest/CurriculumData/curriculumVideoList',//课堂视频列表
	ReportDataUrl : '../../rest/EmotionalData/EmojiReport',//课堂分析
	findInClassInfo : '../../Server/findInClassInfo',//获取上课中的课堂信息
	getMemcacheStatus : '../../Server/getMemcacheStatus',//获取程序状态
	createFile : '../../rest/EmotionalData/createFile',//生成pdf报表文件
	downloadFile : '../../rest/EmotionalData/exportFile?fileName=',//下载pdf报表文件
	getComparativeAnalysis : '../../rest/EmotionalData/comparativeAnalysis',//报表比对
	findCurriculumTbl : '../../rest/CurriculumData/findCurriculumTbl',//获取单堂课堂信息
	deleteCurriculumTbl : '../../rest/CurriculumData/deleteCurriculumTbl',//删除课堂信息
	setCurriculumLevel : '../../rest/CurriculumData/setCurriculumLevel',//设置课堂等级 优质课
	ComprehensiveEvaluation : '../../rest/EmotionalData/ComprehensiveEvaluation',//课堂整体评价

	// *****************学生信息接口********************
	getStudentsEmotionList: '../../rest/StudentsEmotionAnalysis/getStudentsEmotionList',//学生列表
	deleteStudentEmotion: '../../rest/StudentsEmotionAnalysis/deleteStudentsEmotion',//删除学生
	getStudentAnalysis: '../../rest/StudentsEmotionAnalysis/getStudentAnalysis', //学生个体分析
	mergeStudentsEmotion: '../../rest/StudentsEmotionAnalysis/mergeStudentsEmotion', //合并学生信息


//	getStudentsEmotionList: '../data/reportData.json',//学生列表
//	deleteStudentEmotion: '../../rest/StudentsEmotionAnalysis/deleteStudentsEmotion',//删除学生
//	getStudentAnalysis:  '../data/reportData.json', //学生个体分析
//	mergeStudentsEmotion: '../../rest/StudentsEmotionAnalysis/mergeStudentsEmotion', //合并学生信息

	// *****************用户信息接口********************
	getUserInfo : '../../rest/UserData/getUserInfo', // 登录
	Userlist : '../../rest/UserData/Userlist', 		// 用户列表
	UpdateUser : '../../rest/UserData/UpdateUser', 	// 修改
	InsertUser : '../../rest/UserData/InsertUser', 	// 添加
	DeleteUser : '../../rest/UserData/DeleteUser', 	// 删除
	SelectUser : '../../rest/UserData/SelectUser',	// 获取个人信息

	// *****************参数配置接口********************
	getSettingInfo : '../../rest/SettingData/getSettingInfo', // 获取参数配置信息
	saveSettingInfo : '../../rest/SettingData/saveSettingInfo', // 保存参数配置信息
	deleteSettingInfo : '../../rest/SettingData/deleteSettingInfo', // 删除参数配置信息
	testConnect : '../../rest/SettingData/testConnect', // 测试连接

	// *****************城市列表********************
	getCityList : '../jsondata/city.json',

	// *****************学校管理********************
	findSchoolBypagination : '../../rest/SchoolInfoAPI/findBypagination',
	findSchoolAll : '../../rest/SchoolInfoAPI/findAll',
	findSchoolInfo : '../../rest/SchoolInfoAPI/findSchoolInfo',
	saveSchoolInfo : '../../rest/SchoolInfoAPI/saveSchoolInfo',
	modifySchoolInfo : '../../rest/SchoolInfoAPI/modifySchoolInfo',
	deleteSchoolInfo : '../../rest/SchoolInfoAPI/deleteSchoolInfo',
	
	// *****************老师管理********************
	findTeacherBypagination : '../../rest/TeacherInfoAPI/findBypagination',
	findTeacherAll : '../../rest/TeacherInfoAPI/findAll',
	findTeacherInfo : '../../rest/TeacherInfoAPI/findTeacherInfo',
	saveTeacherInfo : '../../rest/TeacherInfoAPI/saveTeacherInfo',
	modifyTeacherInfo : '../../rest/TeacherInfoAPI/modifyTeacherInfo',
	deleteTeacherInfo : '../../rest/TeacherInfoAPI/deleteTeacherInfo',
	// 上传
	upLoad : '../../rest/upLoadApi/upLoad',

	// *****************综合查询********************
	findComprehensiveReportList : '../../rest/Report/GetComprehensiveReportList',
	ExportCsv : '../../rest/Report/ExportCsv',

	// *****************评论********************
	CurriculumEvaluateGetList : '../../rest/CurriculumEvaluate/GetList',
	CurriculumEvaluateDeleteId : '../../rest/CurriculumEvaluate/DeleteId',
	CurriculumEvaluateSave : '../../rest/CurriculumEvaluate/Save',
		
	// ****************socket********************
	LiveStart : '../../../LiveStart',//启动socket
	ServerSocketAddress : '/CamEmotion-web/myHandler',//服务端socket地址
	rtmpAddress: '/emotionVideo/live', //rtmp拉流地址
	
	switchCameraInterval:60000,//单位毫秒
	cameraJson:[
	            
	             {
	            	 showStatus:true,
	            	 cameraip:'172.28.178.246',
	            	 rtspurl:'http://172.28.178.76:8081/rtc.html?token=token2&autoplay=1'
                     	},
		    	{
	            	 showStatus:false,
	            	 cameraip:'172.28.178.245',
	            	 rtspurl:'http://172.28.178.76:8081/rtc.html?token=token1&autoplay=1'
						},
	            ],
				
   

};