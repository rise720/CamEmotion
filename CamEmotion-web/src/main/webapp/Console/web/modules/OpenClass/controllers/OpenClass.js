/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
	el : '#openclassApp',
	data : {
		curriculumTbl : {	// 当前课程信息
			id : 0,
			name : "",
			createdate : "",
			teacherid : 0,
			teachername : "",
			// gradeno : "",
			// classno : "",
			schoolid : 0,
			school : "",
			classstatus : 1,	// 0正在上课，1已下课 2 待上课
			// coursename : "",
			// education:"",
			// classNature: "新授课",
			coursecontents : "",
			searchcontent : "",
			notemsg:""
		},
		teacherInfo:{},
		teacherList : [],		// 所有教师列表
		schoolList : [],		// 所有学校列表
		gradeList:[],			// 所有年级列表
		//classList:[],			// 所有班级列表
		//subjectList:{},			// 所有学科列表
		statusInterval: null,	// 检查主机运行状态的定时器
		hostStatusList:[],		// 主机运行状态列表
		CONST_EDUCATION_NAME_LIST:["小学", "初中", "高中", "中专", "大专", "本科"],
		CONST_GRADE_LIST:["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级", "十年级"],
		CONST_CLASS_NATURE_LIST:["新授课", "复习课", "练习课", "实验课", "其他"]
	},
	watch: {
		/**
		 * 监控学校id，根据该id，区分教育级别等显示方式
		 */ 
		"curriculumTbl.schoolid": function(){
			let that = this;
			that.initSchoolInfo();
		},
		/**
		 * 监控上课标志，开关主机运行状态的定时器
		 */
		"curriculumTbl.classstatus": function(){
			let that =this;
			var statusInterval = sessionStorage.getItem('statusInterval');
			if(statusInterval){
				clearInterval(statusInterval);
				sessionStorage.removeItem('statusInterval');
			}
			that.hostStatusList = null;
			if(that.curriculumTbl.classstatus == 0){
				that.setStatusInterval(that.curriculumTbl);
			}
		}
	},
	computed: {
		searchcontent: function(){
			let that = this;
			that.curriculumTbl.searchcontent = that.curriculumTbl.teachername + 
											//"|" + that.curriculumTbl.classno +
											//"|" + that.curriculumTbl.coursename +
											"|" + that.curriculumTbl.coursecontents +
											"|" + that.curriculumTbl.school ;
											//"|" + that.curriculumTbl.classNature +
											//"|" + that.curriculumTbl.gradeno;
			return that.curriculumTbl.searchcontent;
		}
	},
	mounted: function() {
		common.checkUrl();
		console.log('------------------/classvideoApp');
		$('#page-wrapper').css('min-height', $(window).height() - 100 + 'px');
		$('.video-box').height($(window).height() - 170 + 'px');
		$('.cac-chart').height($(window).height() - 140 + 'px');

		let that = this;
		// 数据初始化
		that.initData();
//		that.test();
	},
	components: {
	    //主机状态 显示组件
	    'host_status': {
	    	props:['hostName', 'status', 'processType'],
	    	template: '<div class="col-lg-4" style="padding:2px"><div class="equipment-item">' +
            '<div v-if="status == 0" class="equipment-type-running"></div>' +
            '<div v-if="status == -1" class="equipment-type-wraning"></div>' +
            '<div v-if="status > 0" class="equipment-type-error animation-class"></div>' +
            '<div class="equipment-img">'+
            '<img v-show="processType == 0" src="../images/VideoList/camera.png" style="width: 1.8rem;"/>'+
            '<img v-show="processType == 2" src="../images/VideoList/service.png" style="width: 1.8rem;"/>'+
            '</div>' +
            '<div style="text-align: center;">{{hostName}}</div>' +
            '</div></div>'
	    },
	    //主机日志 显示组件
	    'host_log': {
	    	props:['status','status_log'],
	    	template: '<a v-if="status != -1" class="btn btn-block btn-social btn-pinterest" :title=status_log><i class="fa fa-times"></i>{{status_log}}</a>' +
	    	'<a v-else-if="status == -1" class="btn btn-block btn-social btn-openid" :title=status_log><i class="fa fa-times"></i>{{status_log}}</a>'
	    },
	  },
	methods : {
		test: function(){
			var json1 = JSON.parse('{"hostName":"111","processType":"0","status":1,"logList":["2222","33333","44444"]}'); 
			var json2 = JSON.parse('{"hostName":"bbb","processType":"0","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json3 = JSON.parse('{"hostName":"bbb","processType":"0","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json4 = JSON.parse('{"hostName":"bbb","processType":"0","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json5 = JSON.parse('{"hostName":"bbb","processType":"2","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json6 = JSON.parse('{"hostName":"aaa","processType":"2","status":-1,"logList":["cccc","ddddd","eeeee"]}');
			var json7 = JSON.parse('{"hostName":"bbb","processType":"2","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json8 = JSON.parse('{"hostName":"bbb","processType":"2","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json9 = JSON.parse('{"hostName":"bbb","processType":"2","status":0,"logList":["cccc","ddddd","eeeee"]}');
			var json=[];
			json.push(json1, json2,json3,json4,json5,json6,json7,json8,json9);
			for (var int = 0; int < json.length; int++) {
				var a = json[int];
				this.hostStatusList.push(a);
			}
		},
		/**
		 * 数据初始化
		 */
		initData: function(){
			let that = this;
			that.initCurriculumTbl();
			// 获取上课课程、所有的教师、学校的信息。获取后执行页面初始化赋值
			$.when(that.getInClassInfoList(), that.getTeacherInfoList(),
					that.getSchoolInfoList()).then(function() {
				that.initViewData();
			},function(){
				that.curriculumTbl.classstatus = 2;	//获取上课
			});
		},
		/**
		 * 初始化学校班级等信息
		 */
		initSchoolInfo: function(){
			let that = this;
			if(!that.schoolList || that.schoolList.length == 0)
				return;
			for(school of that.schoolList){
				if(school.id != that.curriculumTbl.schoolid)
					continue;
				// 赋值学校名称
				that.curriculumTbl.school = school.schoolname;
				// 赋值教育级别
				// that.curriculumTbl.education = that.CONST_EDUCATION_NAME_LIST[school.educationlevel - 1];
				// 赋值年级列表
				// that.gradeList = that.CONST_GRADE_LIST.slice(0,school.schoolyears);
				// 赋值班级列表
				//that.classList.splice(0,that.classList.length);// 清空数组
				// for(var i=1; i <= school.classcount; i++){
				// 	let classno = (school.classnamedrule == 0 ? i : String.fromCharCode(64 + parseInt(i))) + "班";
				// 	that.classList.push(classno);
				// }
				// 赋值学科列表
				//that.subjectList = JSON.parse(school.subject);
			}
			//如果下课状态，在变更学校时，初始化年级班级学科
			// if(that.curriculumTbl.classstatus == 1){
			// 	// 初始化年级
			// 	that.curriculumTbl.gradeno = that.gradeList[0];
			// 	// 初始化班级
			// 	that.curriculumTbl.classno = that.classList[0];
			// 	// 初始化学科
			// 	that.curriculumTbl.coursename = that.subjectList[0].name;
			// }
		},
		/**
		 * 初始化课程对象
		 */
		initCurriculumTbl: function(){
			let that = this;
			that.curriculumTbl.id = 0;
			that.curriculumTbl.name = "";
			that.curriculumTbl.teacherid = 0;
			that.curriculumTbl.teachername = "";
			// that.curriculumTbl.gradeno = "";
			// that.curriculumTbl.classno = "";
			that.curriculumTbl.schoolid = 0;
			that.curriculumTbl.school = "";
			that.curriculumTbl.classstatus = 1;
			// that.curriculumTbl.coursename = "";
			// that.curriculumTbl.classNature = "新授课";
			that.curriculumTbl.coursecontents = "";
			that.curriculumTbl.searchcontent = "";
			that.curriculumTbl.notemsg = "";
		},
		
		/**
		 * 教师下拉框选项变更，修改教师ID的同时，获取教师名称
		 */
		getTeacherName:function(e){
			this.curriculumTbl.teachername = e.target.selectedOptions[0].innerText;
		},
		
		/**
		 * 下拉框选项变更，修改教师ID的同时，获取教师名称
		 */
		getSchoolName:function(e){
			this.curriculumTbl.school = e.target.selectedOptions[0].innerText;
		},
		
		/**
		 * 获取正在上课的课程信息
		 */
		getInClassInfoList : function() {
			let that = this;
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.findInClassInfo).then(
						function(data) {
							if (data.meta.success) {
								if (data.data && data.data.length > 0)
									that.curriculumTbl = data.data[0];
								deferred.resolve();
							} else {
								bootAlert.alertDanger(data.meta.message);
								deferred.reject();
							}
						}, function(response) {
							bootAlert.alertDanger("报告信息请求错误");
							deferred.reject();
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
		},
		
		/**
		 * 获取教师信息
		 */
		getTeacherInfoList : function() {
			let that = this;
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.findTeacherAll).then(
						function(data) {
							if (data.meta.success) {
								if(data.data && data.data.length > 0){
									that.teacherList = data.data;
									deferred.resolve();
								}else{
									bootAlert.alertDanger("录制前，请先登录演讲者信息");
									deferred.reject();
								}
							} else {
								if (data.meta.message)
									bootAlert.alertDanger(data.meta.message);
								else
									bootAlert.alertDanger("录制前，请先登录演讲者信息");
								deferred.reject();
							}
						}, function(response) {
							bootAlert.alertDanger("演讲者信息请求错误");
							deferred.reject();
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
		},
		
		/**
		 * 获取学校信息
		 */
		getSchoolInfoList : function() {
			let that = this;
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.findSchoolAll).then(
						function(data) {
							if (data.meta.success) {
								if(data.data && data.data.length > 0){
									that.schoolList = data.data;
									deferred.resolve();
								}else{
									bootAlert.alertDanger("录制前，请先登录报告厅信息");
									deferred.reject();
								}
							} else {
								if (data.meta.message)
									bootAlert.alertDanger(data.meta.message);
								else
									bootAlert.alertDanger("录制前，请先登录报告厅信息");
								deferred.reject();
							}
						}, function(response) {
							bootAlert.alertDanger("报告厅信息请求错误");
							deferred.reject();
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
		},
		/**
		 * 查询单个教师信息
		 */
        GetTeacherInfo:function(){
          var that = this;
          if(that.curriculumTbl.teacherid <= 0)
        	  return false;
    	  var params = {'id':that.curriculumTbl.teacherid};
	      try {
	      	requsestSer.post(camEmotion.findTeacherInfo, params).then(function (data) {
	              if (data.meta.success) {
	            	  that.teacherInfo= {};
	            	  that.teacherInfo = data.data;
	                  $('#teacherModal').modal('show');
	              } else {
                  	  bootAlert.alertDanger(data.meta.message);
	              }
	          },function (response) {
              	   bootAlert.alertDanger("请求错误");
	          });
	      } catch (e) {
         	   bootAlert.alertDanger(e.message);
	      }
        },
		/**
		 * 请求开始上课
		 */
		RequestLessonStart : function() {
			let that = this;
			$('#com-loading').modal('show');	//开启loading模态框
			let deferred = $.Deferred();
			try {
				debugger
				requsestSer.post(camEmotion.CreateCurriculumData, that.curriculumTbl).then(
						function(data) {
							let msg = "";
							if (!data.meta.success) {
				                msg = data.meta.message;
				                if(!msg){
				                	msg = data.meta.retCode;
				                }
				                bootAlert.alertDanger(msg);
				                deferred.reject();
				            }else{
				            	let classnoModel = data.data;
				            	
				            	if(classnoModel.status != 0){
				            		msg = classnoModel.log ? classnoModel.log : "开始录制异常！";
				            		bootAlert.alertDanger(msg);
				            		deferred.reject();
				            	}else{
				            		that.curriculumTbl = classnoModel.curriculum;
				            		bootAlert.alertSuccess("开始录制成功");
				            		deferred.resolve();
				            	}	
				            }
							$('#com-loading').modal('hide');	//关闭loading模态框
						}, function(response) {
							bootAlert.alertDanger("开始录制请求错误");
							deferred.reject();
							$('#com-loading').modal('hide');	//关闭loading模态框
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
				$('#com-loading').modal('hide');	//关闭loading模态框
			}
			return deferred.promise();
		},
		
		/**
		 * 请求结束上课
		 */
		RequestLessonEnd : function() {
			let that = this;
			$('#com-loading').modal('show');	//开启loading模态框
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.CloseCurriculumData, that.curriculumTbl).then(
						function(data) {
							let msg = "";
							if (!data.meta.success) {
				                msg = data.meta.message;
				                if(!msg){
				                	msg = data.meta.retCode;
				                }
				                bootAlert.alertDanger(msg);
				                deferred.reject();
				            }else{
				            	if(data.data == "1")
				            		bootAlert.alertDanger("其他用户已经结束录制，请刷新画面");
				            	else
				            		bootAlert.alertSuccess("录制成功");
				            	deferred.resolve();
				            }
							$('#com-loading').modal('hide');	//关闭loading模态框
						}, function(response) {
							bootAlert.alertDanger("开始录制请求错误");
							deferred.reject();
							$('#com-loading').modal('hide');	//关闭loading模态框
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
				$('#com-loading').modal('hide');	//关闭loading模态框
			}
			return deferred.promise();
		},
		
		/**
		 * 初始化页面信息
		 */
		initViewData : function() {
			let that = this;
			// 初始化学校值
			if(that.curriculumTbl.schoolid == 0){

				that.curriculumTbl.schoolid = that.schoolList[0].id;
				that.curriculumTbl.teacherid = that.teacherList[0].id;
				that.curriculumTbl.teachername = that.teacherList[0].teachername;
			}else
				that.initSchoolInfo();
		},

		/**
		 * 点击开始上课按钮事件
		 */
		clickLessonStart: function(){
			let that = this;
			if(that.checkLessonStart())				
				that.RequestLessonStart();	// 发起上课请求
		},
		
		/**
		 * 点击下课按钮事件
		 */
		clickLessonEnd: function(){
			let that = this;
			if(that.checkLessonEnd())		
				that.RequestLessonEnd().then(function(){	// 发起下课请求，成功后初始化页面
					that.initData();
				});	
		},
		
		/**
		 * 开始上课前的检查
		 */
		checkLessonStart: function(){
			let that = this;
            if(!that.checkParams('报告主题',that.curriculumTbl.coursecontents, 300, 'vachar','notNull'))
            	return false;
            return true;
		},
		
		/**
		 * 结束上课前的检查
		 */
		checkLessonEnd: function(){
            return true;
		},
		
		/**
		 * 检验字段
		 */
	    checkParams: function(paramname,param,length,type,checkStatus){
			
	    	 if(checkStatus == 'null' && (param === undefined || param.replace(/(^\s*)|(\s*$)/g, "").length == 0))
	    		 return true;
	    	 let result = common.checkParams(paramname, param, length, type);
	    	 if(result == true)
	    		 return true;
	    	 bootAlert.alertDanger(result);
	    	 return false;	 
	     },
	     
	     /**
	      * 开启主机运行状态检查的定时器，每10秒检查1次
	      */
	     setStatusInterval: function (curriculumTbl) {
	    	 let that =this;
             that.statusInterval = setInterval(function () {
                 that.checkSBStatus(curriculumTbl);
             }, 10000);
             
             sessionStorage.setItem('statusInterval', that.statusInterval); 
			 that.checkSBStatus(curriculumTbl);
	     },
	     
	     /**
	      * 检查主机运行状态
	      */
	     checkSBStatus: function (curriculumTbl) {
	    	 let that = this;
	    	 let deferred = $.Deferred();
			 try {
				 requsestSer.post(camEmotion.getMemcacheStatus, curriculumTbl).then(
					 function(data) {
						 let msg = "";
						 if (!data.meta.success) {
			                 msg = data.meta.message;
			                 if(!msg){
		                     	msg = data.meta.retCode;
			                 }
			                 bootAlert.alertDanger(msg);
			                 deferred.reject();
			             }else{
			             	 that.hostStatusList = data.data;
			            	 deferred.resolve();
			             }
					 }, function(response) {
						 bootAlert.alertDanger("检查摄像头运行状态请求错误");
						 deferred.reject();
					 });
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
     }
	}
});