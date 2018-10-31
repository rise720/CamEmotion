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
        	coursename:'语文',
        	gradeNo:'全部',
        	classNo:'全部',
        	classNature:'全部',
        	curriculumType:'授课',
        	coursecontents:'',
        	begintime:'',
        	endtime:'',
    	},
    	teacherInfo: {},
    	teacherList : [],	// 所有教师列表
		schoolList : [],	// 所有学校列表
    	gradeList:[],		// 所有年级列表
		classList:[],		// 所有班级列表
		CONST_EDUCATION_NAME_LIST:["小学", "初中", "高中", "中专", "大专", "本科", "硕士", "博士"],
		CONST_GRADE_LIST:["一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "七年级", "八年级", "九年级", "十年级"],
    	curr_content:true,
    	promptText:'',
    	staticTypeList:[
    	                   {text:'课堂综合对比分析',value:'0'},
    	                   {text:'课堂明细对比分析',value:'1'},
    	                   {text:'教师对比分析',value:'2'}
    	                   ],
       courseList:[],
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
				
				console.log(school.id);
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
			
			if(that.searchData.schoolId == 0){
				that.gradeList = ['全部'];
				that.classList = ['全部'];
				
				that.searchData.gradeNo = that.gradeList[0];
				that.searchData.classNo = that.classList[0];
				
				that.courseList = courseListTemp.unique();
				
			}
			
		},
		/**
		 * 监听分类
		 */
		"searchData.staticType":function(){
			var that = this;
    		if(that.searchData.staticType == '0'){//
    			that.curr_content = true;
    			that.searchData.coursecontents = '';
    			
    			that.teacherList.forEach(function(val){
    				if(val.id == 0){
    					that.teacherList.splice(0, 1);
    				}
    				that.searchData.teacherId = that.teacherList[0].id;
    			});
    			
    		}else if(that.searchData.staticType == '1'){
    			that.teacherList.forEach(function(val){
    				if(val.id == 0){
    					that.teacherList.splice(0, 1);
    				}
    				that.searchData.teacherId = that.teacherList[0].id;
    			});
    			
    			that.curr_content = false;
    		}else if(that.searchData.staticType == '2'){
    			that.teacherList.forEach(function(val,index){
    				if(index == 0 && val.id != 0){
    					that.teacherList.unshift({'id':0,'teachername':'全部'});
    				}
    				that.searchData.teacherId = that.teacherList[0].id;
    			});
    			that.curr_content = false;
    		}
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
    		DateUtil.editDateTime(0,common.getNowFormatDate(),3,30,"yyyy-MM-dd hh:mm:ss");
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
								data.data.forEach(function(val){
									that.teacherList.push(val);
								});
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
								that.schoolList.push({'id':0,'schoolname':'全部'});
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
		},
        /**
         * 生成报表
         */
        createReportData:function(){
        	var that = this;
        	var coursecontents_null = 'null';
        	if(that.searchData.staticType === '0' || that.searchData.staticType === '1'){
        		if(that.teacherId <= 0){
                	return false;
                }
        	}
        	if(that.searchData.staticType === '1'){
        		coursecontents_null = 'notNull';
        	}
        	
        	
        	if(!that.checkParams('课堂分类',that.searchData.coursename, 300, 'vachar','notNull','coursename-class','error-coursename')){
            	return false;
            }
        	if(!that.checkParams('开始时间',that.searchData.begintime, 50, 'vachar','notNull','begintime-class','error-begintime')){
            	return false;
            }
        	if(!that.checkParams('结果时间',that.searchData.endtime, 50, 'vachar','notNull','endtime-class','error-endtime')){
            	return false;
            }
        	if(!that.checkParams('课程内容',that.searchData.coursecontents, 300, 'vachar',coursecontents_null,'coursecontents-class','error-coursecontents')){
            	return false;
            }
        	$('#com-loading').modal('show');
            var params = {
            			  'id':'',
            			  'curriculumid':'',
            			  'staticType': that.searchData.staticType.trim(),
	            		  'teacherId': that.searchData.teacherId,
	            		  'teacherName': that.searchData.teacherName.trim(),
	            		  'schoolId': that.searchData.schoolId,
	            		  'schoolName': that.searchData.teacherName.trim(),
	            		  'coursename': that.searchData.coursename.trim(),
	            		  'gradeNo': that.searchData.gradeNo.trim(),
	            		  'classNo': that.searchData.classNo.trim(),
	            		  'classNature': that.searchData.classNature.trim(),
	            		  'coursecontents': that.searchData.coursecontents.trim(),
	            		  'begintime': that.searchData.begintime.trim(),
	            		  'endtime': that.searchData.endtime.trim()
            			};
            
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
                    		if(culData.length > that.ChartCount){
                    			handleCulData = culData.slice(0,that.ChartCount);
                    			bootAlert.alertDanger('默认查询' + that.ChartCount + '条数据，当前已查询出' + culData.length + '条数据，请缩小查询范围');
                    		}else{
                    			handleCulData = culData;
                    		}
                    		that.ColumnarFlag = false;
                    		that.pileColumnarChart(
                    				that.chgColumnarFormat(handleCulData,that.searchData.staticType,
                    						that.searchData.coursecontents),'Columnar_ECharts',null);
                    		
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
    	 * 格式化数据，柱状图
    	 */
    	chgColumnarFormat:function(data,type, courseContent){
    		if(!data){
    			return false;
    		}
    		ColumnarData = new Array();
    		var xdata = new Array();
    		var ydata = new Array();
    		var zdata = new Array();
    		
            var ydata1 = new Array();
            var ydata2 = new Array();
            var ydata3 = new Array();
            
            var ydata4 = new Array();
            var ydata5 = new Array();
    		data.forEach(function(val,index){
    			if(!val.valencePieList || !val.faceCountPieList || val.valencePieList.length == 0 || val.faceCountPieList.length == 0){
    				return false;
    			}
    			if(type === '0'){
    				xdata[index] = val.courseContents;
    			}else if(type === '1'){
    				xdata[index] = val.createTime + "\n" + val.gradeNo + "(" + val.classNo + ")";
    			}else if(type === '2'){
    				xdata[index] = val.teacherName;
    			}else{
    				return null;
    			}
    			
    			zdata[index] = val.averageValueAll;
    			
    			ydata1[index] = val.valencePieList[0].percent.replace("%", "");
    			ydata2[index] = val.valencePieList[1].percent.replace("%", "");
    			ydata3[index] = val.valencePieList[2].percent.replace("%", "");
    			
    			ydata4[index] = val.faceCountPieList[0].percent.replace("%", "");
    			ydata5[index] = val.faceCountPieList[1].percent.replace("%", "");
    			
    		});
    		ydata.push(ydata1);
    		ydata.push(ydata2);
    		ydata.push(ydata3);
    		ydata.push(ydata4);
    		ydata.push(ydata5);
    		
    		ColumnarData.push(xdata);
    		ColumnarData.push(ydata);
    		ColumnarData.push(zdata);
    		
    		return ColumnarData;
    	}
    	,
    	/**
    	 * 堆叠柱状图
    	 */
    	pileColumnarChart:function(columnarData, dom, Type){
    		let that = this;
    		if(columnarData == null){
    			return false;
    		}
    		$('#'+dom).width($('#box').width());
    		var myChart = echarts.init(document.getElementById(dom));
    		
    		var dataZoom_end=0;
    		var counts = columnarData[0].length;
    		if(counts <= that.ChartCountView){
    			dataZoom_end =100;
    		}else{
    			var c = counts / that.ChartCountView;
    			dataZoom_end = that.ChartCount / c;
    		}
    		option = {
    			    tooltip : {
    			        trigger: 'axis',
    			        axisPointer: {
    			            type: 'cross',
    			            crossStyle: {
    			                color: '#999'
    			            }
    			        },
    			        formatter:function(data){
//        		        	console.log("------data:" + data);
        		        	 var result = "";
         		        	for (var int = 0; int < data.length; int++) {
     		        			result +=  "<div style='text-align:left;'>" + data[int].marker + "" + data[int].seriesName + "：" +
         		        		data[int].value + "</div>"
        						}
         		        	return result;
        		        }
    			    },
    			    legend: {
    			        data:['思考','活跃','听课']
    			    },
    			    grid: {
    			        left: '3%',
    			        right: '4%',
    			        bottom: '3%',
    			        containLabel: true
    			    },
    			    xAxis : [
    			        {
    			            type : 'category',
    			            data : columnarData[0],
    			            axisLabel: {
    			            	interval:0,  
//    			            	rotate:20,
    			                formatter:function(value){
    			                	if(that.ChartCount > 10 && value.length > 5){
    			                		value = value.substring(0,5) + "...";
    			                	}
    			                	return value;
    			                }
    			            },
    			        }
    			    ],
    			    yAxis : [
    			        {
    			            type : 'value',
    			            name: '占比（%）',
    			            axisLabel: {
    			                formatter: '{value} %'
    			            }
    			        }
    			    ],
    			    dataZoom: [//给x轴设置滚动条  
    			               {  
    			            	   start:0,//默认为0  
    			                   end: dataZoom_end,//默认为100  
    			                   type: 'slider',  
    			                   show: true,  
//    			                   xAxisIndex: [0],  
    			                   handleSize: 0,//滑动条的 左右2个滑动条的大小  
    			                   height: 8,//组件高度  
    			                   left: 85, //左边的距离  
    			                   right: 60,//右边的距离  
    			                   bottom: 0,//右边的距离  
    			                   handleColor: '#ddd',//h滑动图标的颜色  
    			                   handleStyle: {
    			                       borderColor: "#cacaca",  
    			                       borderWidth: "1",  
    			                       shadowBlur: 2,  
    			                       background: "#ddd",  
    			                       shadowColor: "#ddd",  
    			                   },  
    			                   fillerColor: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{  
    			                       //给颜色设置渐变色 前面4个参数，给第一个设置1，第四个设置0 ，就是水平渐变  
    			                       //给第一个设置0，第四个设置1，就是垂直渐变  
    			                       offset: 0,  
    			                       color: '#1eb5e5'  
    			                   }, {  
    			                       offset: 1,  
    			                       color: '#5ccbb1'  
    			                   }]),  
    			                   backgroundColor: '#ddd',//两边未选中的滑动条区域的颜色  
    			                   showDataShadow: false,//是否显示数据阴影 默认auto  
    			                   showDetail: false,//即拖拽时候是否显示详细数值信息 默认true  
    			                   filterMode: 'filter',  
    			               },  
    			               //下面这个属性是里面拖到  
    			               {  
    			                   type: 'inside',  
    			                   show: true,  
//    			                   xAxisIndex: [0],  
    			                   start: 0,//默认为1  
    			                   end: dataZoom_end,//默认为100  
    			               },  
    			           ],
    			    series : [
    			        {
    			            name:'思考',
    			            type:'bar',
    			            stack: '活跃度',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'inside'
    			                }
    			            },
    			            data:columnarData[1][0]
    			        },
    			        {
    			            name:'活跃',
    			            type:'bar',
    			            stack: '活跃度',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'inside'
    			                }
    			            },
    			            data:columnarData[1][2]
    			        },
    			        {
    			            name:'听课',
    			            type:'bar',
    			            label: {
    			                normal: {
    			                    show: true,
    			                    position: 'inside'
    			                }
    			            },
    			            data:columnarData[1][3]
    			        }
    			    ]
    			};
    		
    		myChart.clear();
        	myChart.setOption(option);
        	return myChart;
    	},
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
    }
});