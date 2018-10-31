/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
    el:'#classStudentsList',
    data:{
    	curriculumId:0,			//传入参数课程id
    	imagePath: "",			//图片路径
    	curriculumInfo:[],
    	studentsEmotionList:[],
    	orderBy:{
    		parm1:'studentInfo',
    		parm2:'',
    		asc: true
    	},
    },
    watch:{
    	curriculumId:function(){
    		var that = this;
    		//that.imagePath = "/opencv_video/FaceImage/" + that.curriculumId + "/";
    	}
    },
    mounted:function(){
    	common.checkUrl();
        console.log('------------------/classvideoApp');
        var that = this;
        $('#page-wrapper').css('min-height', $(window).height() - 100 + 'px');
        $('.video-box').height($(window).height() - 170 + 'px');
        $('.cac-chart').height($(window).height() - 140 + 'px');
        
        $('#com-loading').modal('show');
        var par = vipspa.parse();
        that.curriculumId = par.param.curriculumId;
        
        $.when(that.findCurriculum(that.curriculumId),that.getStudentsList(that.curriculumId))
        		.done(function(){
        			$('#com-loading').modal('hide');
        		});
        
        //$.when(that.findCurriculum(that.curriculumId))
		//.done(function(){
		//	$('#com-loading').modal('hide');
		//});
    },

    computed: {
    	
    	//学生列表排序
    	newStudentsSort: function(value){
    		var that = this;
    		return that.studentsEmotionList.sort(that.sortBy(that.orderBy.parm1,that.orderBy.parm2,that.orderBy.asc));
    	}
    },
    filters:{
    	//格式化时间
    	formatTime: function(value){ 
    		return (value && value.length > 16 )? value.slice(0,16) : value;
    	},
    	
    },
    methods:{
    	/**
		 * 获取当前课堂的基本信息
		 * 
		 * @param durtion
		 */
    	findCurriculum:function(id){
    		let deferred = $.Deferred();
    		var that = this;
    		var params = {'id':id};
    	    try {
    	    	requsestSer.post(camEmotion.findCurriculumTbl + "?id=" + id, params).then(function (data) {
    	            if (data.meta.success) {
    	            	that.curriculumInfo = data.data;
    	            	deferred.resolve();
    	            } else {
    	            	$('#com-loading').modal('hide');
    	                bootAlert.alertDanger(data.meta.message);
    	                deferred.reject();
    	            }
    	        }.bind(this)).catch(function (response) {
    	        	$('#com-loading').modal('hide');
    	            bootAlert.alertDanger('请求错误');
    	            deferred.reject();
    	        }.bind(this));
    	    } catch (e) {
    	    	$('#com-loading').modal('hide');
    	        alert(e.message);
    	        deferred.reject();
    	    }
    	    return deferred.promise();
    	},
    	
    	/**
    	 * 获取当前课堂的学生列表
    	 *
    	 * @param durtion
    	 */
        getStudentsList:function(id){
    		let deferred = $.Deferred();
    		var that = this;
    		var params = {'curriculumid':id};
    	    try {
    	    	requsestSer.post(camEmotion.getStudentsEmotionList + "?curriculumid=" + id, params).then(function (data) {
    	            if (data.meta.success) {
    	            	that.studentsEmotionList = data.data;
    	            	that.addMergeAttribute();
    	            	deferred.resolve();
    	            } else {
    	            	$('#com-loading').modal('hide');
    	                bootAlert.alertDanger(data.meta.message);
    	                deferred.reject();
    	            }
    	        }.bind(this)).catch(function (response) {
    	        	$('#com-loading').modal('hide');
    	            bootAlert.alertDanger('请求错误');
    	            deferred.reject();
    	        }.bind(this));
    	    } catch (e) {
    	    	$('#com-loading').modal('hide');
    	        alert(e.message);
    	        deferred.reject();
    	    }
    	    return deferred.promise();
    	},  	
    	/**
    	 * 删除学生功能
    	 */
    	deleteStudent:function(curriculumId, studentId){
        	initAlert();
        	var that = this;
            Modal.confirm(
            		{
            			title:"<font size='3'>删除警告</font>",
            			msg: "<h5>是否删除学生 （ ID: "+ studentId + " ）的课堂信息？</h5>",
                        btnok: '删除',
                        btncl: '取消'
                     })
                .on(function (e) {
                    if (e) {
                    	var params = {"curriculumid":curriculumId, "studentid":studentId};
                    	$('#com-loading').modal('show');
                        try {
                        	requsestSer.post(camEmotion.deleteStudentEmotion, params).then(function (data) {
                        		$('#com-loading').modal('hide');
                        		if(data.meta.success){
                        			that.getStudentsList(that.curriculumId);
                        			bootAlert.alertSuccess('删除成功');
                            	}else{
                            		bootAlert.alertDanger(that.fromatterError(data.meta.retCode).format("删除"));
                            	}
                            }, function(response) {
                            	$('#com-loading').modal('hide');
                            	bootAlert.alertDanger('删除学生信息错误');
            				});
                        } catch (e) {
                        	$('#com-loading').modal('hide');
                            bootAlert.alertDanger(e.message);
                        }
                    }
                });
        },
        
        /**
         * 合并学生
         */
        mergeStudent: function(){
        	var that = this;
        	var mergeNumber = 0;
        	for(var student of that.studentsEmotionList){
        		if(student.merge == true){
        			mergeNumber++;
        			if(mergeNumber >= 2){
        				$('#mergeModal').modal('show');
        				return;
        			}
        		}
        	}
            Modal.alert(
            		{
            			title:"<font size='3'>合并失败提醒</font>",
            			msg: "<h5>请至少选择2位学生进行合并</h5>",
            			btnok: "确定"
            			})
        	return;
        },
        
        /**
         * 确认合并学生
         */
        mergeStudentConfirm: function(){
        	var that = this;
        	var params = [];
        	for(var student of that.studentsEmotionList){
        		if(student.merge == true){
        			var studentParams = {
        					"curriculumid": student.studentInfo.curriculumid, 
        					"studentid": student.studentInfo.studentId
        					};
        			params.push(studentParams);
        		}
        	}
        	$('#mergeModal').modal('hide');
        	$('#com-loading').modal('show');
            try {
            	requsestSer.post(camEmotion.mergeStudentsEmotion, params).then(function (data) {
            		$('#com-loading').modal('hide');
            		if(data.meta.success){
            			that.getStudentsList(that.curriculumId);
            			bootAlert.alertSuccess('合并成功');
                	}else{
                		bootAlert.alertDanger(that.fromatterError(data.meta.retCode).format("合并"));
                	}
                }, function(response) {
                	$('#com-loading').modal('hide');
                	bootAlert.alertDanger('合并学生信息错误');
				});
            } catch (e) {
            	$('#com-loading').modal('hide');
                bootAlert.alertDanger(e.message);
            }       	
        },
        
        /**
         * 对studentsEmotionList增加合并属性，默认为不合并
         */
        addMergeAttribute: function(){
        	var that = this;
        	for(var student of that.studentsEmotionList){
        		Vue.set(student, 'merge', false);
        	}
        },
        
        /**
         * 排序
         * parm1: 排序类型： valencePieList 活跃度， attentionPieList 注意力 studentInfo 学生 
         * parm2: 排序字段： 对于活跃度而言0~2 对应困惑 平静 兴奋， 对于注意力而言 0~1 对应 分散 集中  对学生
         * rev:  排序方式: 1 正序 2倒序
         */
        sortBy: function(parm1, parm2, rev){
            //第二个参数没有传递 默认升序排列
            if(rev ==  undefined){
                rev = 1;
            }else{
                rev = (rev) ? 1 : -1;
            }
            return function(a,b){
            	var valueA = 0;
            	var valueB = 0;
            	
            	if(parm1 == 'valencePieList' || parm1 == 'attentionPieList'){
            		for(var emotion of a[parm1]){
                    	if(emotion.level == parm2){
                    		valueA = parseFloat(emotion.percent);
                    		break;
                    	}
                    }
                    for(var emotion of b[parm1]){
                    	if(emotion.level == parm2){
                    		valueB = parseFloat(emotion.percent);
                    		break;
                    	}
                    }
            	}else if(parm1 == 'studentInfo'){
            		valueA = parseFloat(a[parm1].studentId);
            		valueB = parseFloat(b[parm1].studentId);
            	}
                
                if(parseInt(valueA) < parseInt(valueB)){
                    return rev * -1;
                }
                if(valueA > valueB){
                    return rev * 1;
                }
                return 0;
            }
        },
        
        //点击表格标题后，触发排序
        clickTitle: function(parm1,parm2){
        	var that = this;
        	if(that.orderBy.parm1 == parm1 && that.orderBy.parm2 == parm2){
        		that.orderBy.asc = !that.orderBy.asc;
        	} else{
        		that.orderBy.parm1 = parm1;
        		that.orderBy.parm2 = parm2;
        		that.orderBy.asc = true;
        	}	
        },
        /**
         * 获取错误信息
         */
        fromatterError:function(key){
        	var msg = "未知错误";
        	switch (key) {
			case '10001':
				msg = "系统异常，请稍候重试";
				break;
			case '70002':
				msg = "该数据已经不存在";
				break;
			case '-1':
				msg = "上课过程中不能{0}学生信息";
				break;
			default:
				break;
			}
        	return msg;
        }
        
    }//methods end
});