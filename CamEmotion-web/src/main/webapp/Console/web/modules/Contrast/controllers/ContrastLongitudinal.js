/**
 * 横向
 */
new Vue({
    el:'#contrastApp',
    data: {
    	ColumnarFlag:true,
    	ChartCount:100,//最大查询条数
    	ChartCountView:20,//默认显示条数
    	searchData:{
    		staticType:'0',
    		schoolId:-1,
    		teacherId:0,
        	teacherName:'',
        	coursename:'',
        	gradeNo:'全部',
        	classNo:'全部',
        	classNature:'全部',
        	curriculumType:'授课',
        	coursecontents:'',
        	begintime:'',
        	endtime:'',
    	},
    	teacherInfo: {},	//单个老师信息
    	teacherList : [],	// 所有教师列表
		schoolList : [],	// 所有学校列表
    	gradeList:[],		// 所有年级列表
		classList:[],		// 所有班级列表
		CONST_EDUCATION_NAME_LIST:["小学", "初中", "高中", "中专", "大专", "本科", "硕士", "博士"],
		CONST_GRADE_LIST:["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级", "十年级"],
    	curr_content:true,
    	promptText:'',
        courseList:[],//学科列表
        curriculums:[],//所有课堂数据
        xLineData:[],//X轴数据
        yLineData:[],//Y轴数据
        classNatureList:[
                           	{text:'全部',value:'全部'},
    	                    {text:'新授课',value:'新授课'},
    	                    {text:'复习课',value:'复习课'},
    	                    {text:'练习课',value:'练习课'},
    	                    {text:'实验课',value:'实验课'},
    	                    {text:'其他',value:'其他'}
    	                    ],
        modalCheckResult:{									//画面字段检查结果及msg信息
        	 errSeq: 0,
        	 msg: ''
        },
        emojiConst:{						// 图标显示用常量字段
    		emojiValence:["思考", "倾听", "活跃"],
//    		emojiInteractionLine:["波动率","波动偏差","波动均衡"],
	    	emojiSceneLine:["讨论","练习","听课"],
	    	emojiColor:["#0B62A4","#4DA74D","#CB4B4B"]
    	}
    },
	watch: {
		//监控学校id，根据该id，区分教育级别等显示方式
		"searchData.schoolId": function(){
			var that = this;
			var courseListTemp = new Array();
			if(!that.schoolList || that.schoolList.length == 0)
				return;
			
			that.schoolList.forEach(function(school,index){
				
				if(that.searchData.schoolId == 0){
					if(index == 1){
						courseListTemp = JSON.parse(school.subject);
					}else if(index > 1){
						courseListTemp = courseListTemp.concat(JSON.parse(school.subject));
					}
				}else{
					if(school.id == that.searchData.schoolId){
						//赋值年级列表
						that.gradeList = ['全部'].concat(that.CONST_GRADE_LIST.slice(0,school.schoolyears));
						//初始化年级
						that.searchData.gradeNo = that.gradeList[0];
						//赋值班级列表
						that.classList.splice(0,that.classList.length);//清空数组 
						that.classList.push('全部');
						for(var i=1; i <= school.classcount; i++){
							let classno = (school.classnamedrule == 0 ? i : String.fromCharCode(64 + parseInt(i))) + "班";
							that.classList.push(classno)
						}
						//初始化班级
						that.searchData.classNo = that.classList[0];
						
						that.courseList = JSON.parse(school.subject);
						
					}
				}
			});
			
			that.initViewData();
			
		}
	},
    mounted:function(){
    	common.checkUrl();
        console.log('------------------/Contrast');
        $('#context').css('min-height', $(window).height() - 150 + 'px');
        $('.video-box').height($(window).height() - 170 + 'px');
        $('.cac-chart').height($(window).height() - 140 + 'px');
        
//        if(memcacheInterval){
//    		clearInterval(memcacheInterval);
//    	}
        
        let that = this;
        that.DateInit();//初始化时间控件
      //指定时间控件默认时间
        that.searchData.begintime = 
    		DateUtil.editDateTime(0,common.getNowFormatDate(),2,3,"yyyy-MM-dd hh:mm:ss");
        that.searchData.endtime = common.getNowFormatDate();
    	
    	// 获取上课课程、所有的教师、学校的信息。获取后执行页面初始化赋值
		$.when(that.getSchoolInfoList(), that.getTeacherInfoList()).done(function() {
			that.initViewData();
		});
    },
    methods:{
    	// 获取教师信息
		getTeacherInfoList : function(type) {
			let that = this;
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.findTeacherAll).then(
						function(data) {
							if (data.meta.success && data.data
									&& data.data.length > 0) {
								that.teacherList = data.data;
								deferred.resolve();
							} else {
								if (data.meta.message)
									bootAlert.alertDanger(data.meta.message);
								deferred.reject();
							}
						}, function(response) {
							bootAlert.alertDanger("教师信息请求错误");
							deferred.reject();
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
		},
		// 获取学校信息
		getSchoolInfoList : function() {
			let that = this;
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.findSchoolAll).then(
						function(data) {
							if (data.meta.success && data.data
									&& data.data.length > 0) {
								data.data.forEach(function(val){
									that.schoolList.push(val);
								});
//								that.schoolList = data.data;
								deferred.resolve();
							} else {
								if (data.meta.message)
									bootAlert.alertDanger(data.meta.message);
								deferred.reject();
							}
						}, function(response) {
							bootAlert.alertDanger("学校信息请求错误");
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
          if(that.searchData.teacherId <= 0)
        	  return false;
    	  var params = {'id':that.searchData.teacherId};
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
    	// 初始化页面信息
		initViewData : function() {
			let that = this;
			// 初始化学校值
			if(that.searchData.schoolId == -1)
				that.searchData.schoolId = that.schoolList[0].id;
			// 初始化教师名
			if(that.searchData.teacherId == 0){
				that.searchData.teacherId = that.teacherList[0].id;
				that.searchData.teacherName = that.teacherList[0].teachername;
			}
			// 初始化学科
			if(that.courseList != null && that.courseList.length > 0){
				var isExit = false;
				for(var i = 0; i < that.courseList.length; i++){
					if(that.searchData.coursename == that.courseList[i].name){
						isExit = true;
						 break ;
					}
				}
				if(!isExit){
					that.searchData.coursename = that.courseList[0].name;
				}
			}
		},
        /**
         * 生成报表
         */
        createReportData:function(){
        	var that = this;
        	
        	if(!that.checkParams('学科',that.searchData.coursename, 300, 'vachar','notNull','coursename-class','error-coursename')){
            	return false;
            }
        	if(!that.checkParams('开始时间',that.searchData.begintime, 50, 'vachar','notNull','begintime-class','error-begintime')){
            	return false;
            }
        	if(!that.checkParams('结果时间',that.searchData.endtime, 50, 'vachar','notNull','endtime-class','error-endtime')){
            	return false;
            }
        	if(!that.checkParams('课程内容',that.searchData.coursecontents, 300, 'vachar','null','coursecontents-class','error-coursecontents')){
            	return false;
            }
        	$('#com-loading').modal('show');
            var params = [{
            	'schoolId':that.searchData.schoolId,
            	'teacherId': that.searchData.teacherId,
    			'coursename': that.searchData.coursename.trim(),
    			'gradeNo': that.searchData.gradeNo.trim(),
    			'classNo': that.searchData.classNo.trim(),
    			'classNature': that.searchData.classNature.trim(),
    			'coursecontents': that.searchData.coursecontents.trim(),
    			'begintime': that.searchData.begintime.trim(),
    			'endtime': that.searchData.endtime.trim()
			}];
            
            try {
            	requsestSer.post(camEmotion.getComparativeAnalysis, params).then(function (data) {
                	var msg = "";
                	var handleCulData;
                	$('#com-loading').modal('hide');
                	if (data.meta.success) {
                    	var culData = data.data;
                    	if(!culData || culData.length == 0){
                    		that.ColumnarFlag = true;
                    		bootAlert.alertWarning('没有满足条件的数据');
                    	}else{
                    		handleCulData = culData;
                    		
                    		var lineData = that.chgLineFormat(handleCulData);
                    		
                    		//活跃度对比
                    		that.LineChart3(lineData,'vs_valence_Bubble_ECharts','activity');
                    		//互动频率对比
//                    		that.LineChart3(lineData,'vs_interaction_Bubble_ECharts','interaction');
                    		//课堂听课率
                    		that.LineChart3(lineData,'vs_scene_Bubble_ECharts','sence');
                    		
                    	}
                    } else {
                    	 that.ColumnarFlag = true;
                    	 msg = data.meta.message;
                         if(!msg){
                         	msg = data.meta.retCode;
                         }
                         bootAlert.alertDanger(msg);
                    }
                },function (response) {
                   that.ColumnarFlag = true;
                   $('#com-loading').modal('hide');
               	   bootAlert.alertDanger("生成报表错误");
                });
            } catch (e) {
            	$('#com-loading').modal('hide');
            	bootAlert.alertDanger(e.message);
            }
    	},
    	/**
    	 * 格式化数据，折线图
    	 */
    	chgLineFormat:function(data){
    		var that = this;
    		if(!data){
    			return false;
    		}
    		var lineData = [];
    		
    		var curriculums = [];
    		var xLineData = [];
    		var yLineData = [];
    		
    		var curriculum = {};
    		
    		var data1 = [];
    		var data2 = [];
    		var data3 = [];
    		
    		var data4 = [];
    		var data5 = [];
    		var data6 = [];
    		
    		data.forEach(function(val,index){
    			//每堂课信息
    			curriculum = {};
    			curriculum.teacherName = val.teacherName;
    			curriculum.courseContents = val.courseContents;
    			curriculum.courseName = val.courseName;
    			curriculum.createTime = val.createTime;
    			curriculums.push(curriculum);
    			
    			xLineData[index] = index;
    			
    			//活跃度
    			val.valencePieList.forEach(function(value,i){
    				if(value.level == 0){
    					data1[index] = value.percent;
    				}else if(value.level == 1){
    					data2[index] = value.percent;
    				}else if(value.level == 2){
    					data3[index] = value.percent;
    				}
    			});
    			
    			//情景
    			val.faceCountPieList.forEach(function(value,i){
    				if(value.level == 0){
    					data4[index] = value.percent;
    				}else if(value.level == 1){
    					data5[index] = value.percent;
    				}else if(value.level == 2){
    					data6[index] = value.percent;
    				}
    			});
    			
    		});
    		
    		yLineData.push(data1);
    		yLineData.push(data2);
    		yLineData.push(data3);
    		
    		yLineData.push(data4);
    		yLineData.push(data5);
    		yLineData.push(data6);
    		
    		lineData.push(curriculums);
    		lineData.push(xLineData);
    		lineData.push(yLineData);
    		
    		return lineData;
    		
    	},
    	
    	/**
		 * 折线图3 (互动频度分析)
		 */
	    LineChart3:function(lineData, dom, Type){
	    	let that = this;
	    	var myChart = echarts.init(document.getElementById(dom));
	    	var legend = null;
	    	var yLable = null;
	    	var colorT;
	    	var selected = {};
	    	yLable = "百分比";
	    	colorT = that.emojiConst.emojiColor;
	    	var xLineData = [];
	    	lineData[0].forEach(function(val, index){
	    		xLineData[index] = val.createTime;
	    	});
	    	
	    	if(Type == 'activity'){
	    		legend = that.emojiConst.emojiValence;
	    		
	    		selected = {
	    				'思考': true,
	    				'倾听': false,  
	    				'活跃': false
	    				};
	    	}else if(Type == 'interaction'){
	    		legend = that.emojiConst.emojiInteractionLine;
	    		selected = {
	    				'波动率': true,  
	    				'波动偏差': false,  
	    				'波动均衡': false
	    				};
	    	}else if(Type == 'sence'){
	    		legend = that.emojiConst.emojiSceneLine;
	    		selected = {
	    				'听课': false,
	    				'练习': false,
	    				'讨论': true
	    				};
	    	}
	    	var Series = that.createSeries(lineData,legend,colorT,Type);
	    	var option = {
	    			title: {
	                    text: '时序图',
	                    left:'2%',
	                    top: '1.5%',
	                    textStyle:{
	                        // 文字颜色
	                        color:'#FFF',
	                        // 字体风格,'normal','italic','oblique'
	                        fontStyle:'normal',
	                        // 字体粗细 'normal','bold','bolder','lighter',100 | 200
							// | 300 | 400...
	                        fontWeight: 'bolder',
	                        // 字体系列,
	                        // fontFamily:'sans-serif',
	                        // 字体大小
	                　　　　 			fontSize:15
	                    }
	                },
	    		    tooltip : {
	    		        trigger: 'axis',
	    		        axisPointer: {
	    		            type: 'cross',
	    		            label: {
	    		                backgroundColor: '#6a7985'
	    		            }
	    		        }
	                ,formatter:function(data){
	                	var result = "";
	                	for (var int = 0; int < data.length; int++) {
	                		result +=  data[int].marker + "老师：" + lineData[0][data[int].dataIndex].teacherName + "</br>";
	                		
	                		result +=  data[int].marker + "课程内容：" + lineData[0][data[int].dataIndex].courseContents + "</br>";
	                		
	                		result +=  data[int].marker + "" + data[int].seriesName + "：" +
	 		        		data[int].value + "%</br>";
	                	}
	                	return result;
	    		        }
	    		    },
	    		    legend: {
	    		    	right: 100,
	                    top: that.shixutop,
	    		        data: legend,
	    		        textStyle: {
	                        color: '#000',
	                        fontSize: 14
	                    },
	                    selectedMode: 'single',  
	                    selected: selected
	    		    },
	    		    grid: {
		                left: '5%',
		            },
	    		    xAxis : [
	    		        {
	    		        	name: "时间(min)",
	    		            type : 'category',
	    		            boundaryGap : false,
	    	                interval:2,
	    	                splitNumber:15,
	    		            data : xLineData,
	    		            axisLabel: {
	    	                    formatter:function(value){
	    	                    	if(value.length > 11){
    			                		value = value.substring(0,12) + "...";
    			                	}
    			                	return value;
	    	                    }
	    	                }
	    		        }
	    		    ],
	    		    yAxis : [
	    		        {
	    		        	name: yLable,
	    		            type : 'value'
	    		        }
	    		    ],
	    		    dataZoom: [
	    		                 {
	    			                type: 'inside',
	    			                show: true,
	    			                xAxisIndex: [0],
	    			                start: 0,
	    			                end: 100
	    		                 }
	    		    ],
	    		    series : Series,
	    		    animation: false
	    		};
	    	
	    	myChart.clear();
	    	myChart.setOption(option);
	    	return myChart;
	    }
    	,
    	/**
    	 * 组装Series
    	 */
    	createSeries:function(data,legend,colorT,type){
    		var Series = [];
    		if(type == "activity"){
    			Series = [
    			 {
 		            name:legend[0],
 		            type:'line',
 		            itemStyle : {  
                         normal : {  
                             color:colorT[0],  
                             lineStyle:{  
                                 color:colorT[0]
                             }  
                         }  
                     }, 
 		            label: {
 		                normal: {
 		                    show: false,
 		                    position: 'top'
 		                }
 		            },
 		            data:data[2][0],
 		            color : colorT[0],
 		            markPoint: {
 		                data: [
 		                    {type: 'max', name: '最大值'},
 		                    {type: 'min', name: '最小值'}
 		                ]
 		            },
 		            markLine: {
 		                data: [
 		                    {type: 'average', name: '平均值'}
 		                ]
 		            }
 		         },
 		        {
  		            name:legend[1],
  		            type:'line',
  		            itemStyle : {  
                          normal : {  
                              color:colorT[1],  
                              lineStyle:{  
                                  color:colorT[1]
                              }  
                          }  
                      }, 
  		            label: {
  		                normal: {
  		                    show: false,
  		                    position: 'top'
  		                }
  		            },
  		            data:data[2][1],
  		            color : colorT[1],
  		            markPoint: {
  		                data: [
  		                    {type: 'max', name: '最大值'},
  		                    {type: 'min', name: '最小值'}
  		                ]
  		            },
  		            markLine: {
  		                data: [
  		                    {type: 'average', name: '平均值'}
  		                ]
  		            }
  		         },
  		       {
  		            name:legend[2],
  		            type:'line',
  		            itemStyle : {  
                          normal : {  
                              color:colorT[2],  
                              lineStyle:{  
                                  color:colorT[2]
                              }  
                          }  
                      }, 
  		            label: {
  		                normal: {
  		                    show: false,
  		                    position: 'top'
  		                }
  		            },
  		            data:data[2][2],
  		            color : colorT[2],
  		            markPoint: {
  		                data: [
  		                    {type: 'max', name: '最大值'},
  		                    {type: 'min', name: '最小值'}
  		                ]
  		            },
  		            markLine: {
  		                data: [
  		                    {type: 'average', name: '平均值'}
  		                ]
  		            }
  		         }
 		        ]
    		}else if(type == "interaction"){
    			Series = [
    		    			 {
    		 		            name:legend[0],
    		 		            type:'line',
    		 		            itemStyle : {  
    		                         normal : {  
    		                             color:colorT[0],  
    		                             lineStyle:{  
    		                                 color:colorT[0]
    		                             }  
    		                         }  
    		                     }, 
    		 		            label: {
    		 		                normal: {
    		 		                    show: false,
    		 		                    position: 'top'
    		 		                }
    		 		            },
    		 		            data:data[2][3],
    		 		            color : colorT[0],
    		 		            markPoint: {
    		 		                data: [
    		 		                    {type: 'max', name: '最大值'},
    		 		                    {type: 'min', name: '最小值'}
    		 		                ]
    		 		            },
    		 		            markLine: {
    		 		                data: [
    		 		                    {type: 'average', name: '平均值'}
    		 		                ]
    		 		            }
    		 		         },
    		 		        {
    		  		            name:legend[1],
    		  		            type:'line',
    		  		            itemStyle : {  
    		                          normal : {  
    		                              color:colorT[1],  
    		                              lineStyle:{  
    		                                  color:colorT[1]
    		                              }  
    		                          }  
    		                      }, 
    		  		            label: {
    		  		                normal: {
    		  		                    show: false,
    		  		                    position: 'top'
    		  		                }
    		  		            },
    		  		            data:data[2][4],
    		  		            color : colorT[1],
    		  		            markPoint: {
    		  		                data: [
    		  		                    {type: 'max', name: '最大值'},
    		  		                    {type: 'min', name: '最小值'}
    		  		                ]
    		  		            },
    		  		            markLine: {
    		  		                data: [
    		  		                    {type: 'average', name: '平均值'}
    		  		                ]
    		  		            }
    		  		         },
    		  		       {
    		  		            name:legend[2],
    		  		            type:'line',
    		  		            itemStyle : {  
    		                          normal : {  
    		                              color:colorT[2],  
    		                              lineStyle:{  
    		                                  color:colorT[2]
    		                              }  
    		                          }  
    		                      }, 
    		  		            label: {
    		  		                normal: {
    		  		                    show: false,
    		  		                    position: 'top'
    		  		                }
    		  		            },
    		  		            data:data[2][5],
    		  		            color : colorT[2],
    		  		            markPoint: {
    		  		                data: [
    		  		                    {type: 'max', name: '最大值'},
    		  		                    {type: 'min', name: '最小值'}
    		  		                ]
    		  		            },
    		  		            markLine: {
    		  		                data: [
    		  		                    {type: 'average', name: '平均值'}
    		  		                ]
    		  		            }
    		  		         }
    		 		        ]
    		    		}else if(type == "sence"){
    		    			Series = [
    		    		    			 {
    		    		 		            name:legend[0],
    		    		 		            type:'line',
    		    		 		            itemStyle : {  
    		    		                         normal : {  
    		    		                             color:colorT[0],  
    		    		                             lineStyle:{  
    		    		                                 color:colorT[0]
    		    		                             }  
    		    		                         }  
    		    		                     }, 
    		    		 		            label: {
    		    		 		                normal: {
    		    		 		                    show: false,
    		    		 		                    position: 'top'
    		    		 		                }
    		    		 		            },
    		    		 		            data:data[2][3],
    		    		 		            color : colorT[0],
    		    		 		            markPoint: {
    		    		 		                data: [
    		    		 		                    {type: 'max', name: '最大值'},
    		    		 		                    {type: 'min', name: '最小值'}
    		    		 		                ]
    		    		 		            },
    		    		 		            markLine: {
    		    		 		                data: [
    		    		 		                    {type: 'average', name: '平均值'}
    		    		 		                ]
    		    		 		            }
    		    		 		         },
    		    		 		        {
     		    		 		            name:legend[1],
     		    		 		            type:'line',
     		    		 		            itemStyle : {  
     		    		                         normal : {  
     		    		                             color:colorT[1],  
     		    		                             lineStyle:{  
     		    		                                 color:colorT[1]
     		    		                             }  
     		    		                         }  
     		    		                     }, 
     		    		 		            label: {
     		    		 		                normal: {
     		    		 		                    show: false,
     		    		 		                    position: 'top'
     		    		 		                }
     		    		 		            },
     		    		 		            data:data[2][4],
     		    		 		            color : colorT[1],
     		    		 		            markPoint: {
     		    		 		                data: [
     		    		 		                    {type: 'max', name: '最大值'},
     		    		 		                    {type: 'min', name: '最小值'}
     		    		 		                ]
     		    		 		            },
     		    		 		            markLine: {
     		    		 		                data: [
     		    		 		                    {type: 'average', name: '平均值'}
     		    		 		                ]
     		    		 		            }
     		    		 		         },
     		    		 		       {
      		    		 		            name:legend[2],
      		    		 		            type:'line',
      		    		 		            itemStyle : {  
      		    		                         normal : {  
      		    		                             color:colorT[2],  
      		    		                             lineStyle:{  
      		    		                                 color:colorT[2]
      		    		                             }  
      		    		                         }  
      		    		                     }, 
      		    		 		            label: {
      		    		 		                normal: {
      		    		 		                    show: false,
      		    		 		                    position: 'top'
      		    		 		                }
      		    		 		            },
      		    		 		            data:data[2][5],
      		    		 		            color : colorT[1],
      		    		 		            markPoint: {
      		    		 		                data: [
      		    		 		                    {type: 'max', name: '最大值'},
      		    		 		                    {type: 'min', name: '最小值'}
      		    		 		                ]
      		    		 		            },
      		    		 		            markLine: {
      		    		 		                data: [
      		    		 		                    {type: 'average', name: '平均值'}
      		    		 		                ]
      		    		 		            }
      		    		 		         }
    		    		 		        ]
    		    		    		}
    		return Series;
    	}
    	,
    	/**
         * 检验字段
         */
    	checkParams:function(paramname,param,length,type,checkStatus,dom,dom_err){
       	 var result;
       	 if(checkStatus == 'null'){
       		 if(param === undefined || param.replace(/(^\s*)|(\s*$)/g, "").length == 0){
       			 return true;
       		 }else{
       			 result = common.checkParams(paramname,param, length, type);
                    if(result != true){
                     bootAlert.alertDanger(result);
                        return false;
                    }else{
                   	 return true;
                    }
       		 }
       	 }else if(checkStatus == 'notNull'){
    		 result = common.checkParams(paramname,param, length, type);
             if(result != true){
            		bootAlert.alertDanger(result);
                 return false;
             }else{
            	 return true;
             }
       	 }
        },
        /**
         * 格式化字符
         */
        formatterValue:function(value){
        	var result = "";
        	if(value){
        		if(value.indexOf(".") != -1){
        			
        		}else{
        			for (var int = 0; int < (2 - value.length); int++) {
        				result += "0";
    				}
        			result = result + value
        		}
        	}
        },
    	//初始化时间控件
    	DateInit: function() {
    		var that = this;
            var start = {
                format: "YYYY-MM-DD hh:mm:ss",
                isinitVal: true,
                isTime: true,
                initAddVal:[0],
                choosefun: function(elem, datas) {
                    end.minDate = datas; //开始日选好后，重置结束日的最小日期
                    that.searchData.begintime = datas;
                },
                //清除日期后的回调, elem当前输入框ID, val当前选择的值
                clearfun:function(elem, val) {
                	that.searchData.begintime = "";
                	
                },      
                //点击确定后的回调, elem当前输入框ID, val当前选择的值
                okfun:function(elem, val) {
                	that.searchData.begintime = val;
                },
                //层弹出后的成功回调方法, elem当前输入框ID
                success:function(elem) {
                	
                }
            };
            var end = {
                format: "YYYY-MM-DD hh:mm:ss",
                isinitVal: true,
                isTime: true,
                initAddVal:[0],
                choosefun: function(elem, datas) {
                    start.maxDate = datas; //将结束日的初始值设定为开始日的最大日期
                    that.searchData.endtime = datas;
                },
                //清除日期后的回调, elem当前输入框ID, val当前选择的值
                clearfun:function(elem, val) {
                	that.searchData.endtime = "";
                },      
                //点击确定后的回调, elem当前输入框ID, val当前选择的值
                okfun:function(elem, val) {
                	that.searchData.endtime = val;
                },
                //层弹出后的成功回调方法, elem当前输入框ID
                success:function(elem) {
                	
                }
            };
            $.jeDate('#begintime', start);
            $.jeDate('#endtime', end);
        }
    },
		/**
		 * @页码点击事件
		 */
        btnClick: function(items) {
            if (items != this.cur) {
                this.cur = items
            }
            this.pageModel.currentPage = items;    
            sessionStorage.setItem('class_cur', this.cur); 
            this.GetList();
            
        },
        /**
		 * @上一页
		 */
        preClick: function() {
            this.cur--;
            this.pageModel.currentPage = this.cur;  
            sessionStorage.setItem('class_cur', this.cur);  
            this.GetList();
        },
        /**
		 * @下一页
		 */
        nexClick: function(items) {
            this.cur++;
            this.pageModel.currentPage = this.cur;    
            sessionStorage.setItem('class_cur', this.cur); 
            this.GetList();
        },
        /**
		 * 方法说明：获取老师列表
		 */
        GetList: function () {
        	var that = this;
            try {
            	requsestSer.post(camEmotion.getCurriculumList, that.pageModel).then(function (data) {
                    if (data.meta.success) {
                    	that.list = data.data.objList;
                    	that.all = data.data.totalPages;
                    	that.cur = data.data.currentPage;
                    } else {
 	            	   bootAlert.alertDanger(data.meta.message);
                    }
                },function (response) {
	            	   bootAlert.alertDanger("请求失败");
                });
            } catch (e) {
         	   bootAlert.alertDanger(e.message);
            }
        }
});