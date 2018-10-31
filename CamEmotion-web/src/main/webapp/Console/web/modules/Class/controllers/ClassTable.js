/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
    el:'#classtableApp',
    data:{
    	index: -1,
        all: 1,       // 总页数
        cur: 1,        // 当前页码
        pageModel: {
            currentPage:'1',
            pageRecorders:'6',
            rule:'DESC',
			expression:'id',
			searchcontent:'',
			id:''
        },
        list: [],
        teacherInfo: {},
       
    },
    mounted:function(){
    	common.checkUrl();
    	initAlert();
    	this.cur = sessionStorage.getItem('class_cur');
    	if(!this.cur || this.cur == 0){
    		this.cur = 1;
    	}
    	this.pageModel.currentPage = this.cur;
    	this.GetList();
    },
    /**
	 * @分页事件
	 */
    computed: {
        indexs: function() {
            var left = 1
            var right = this.all
            var ar = []
            if (this.all > 5) {
                if (this.cur > 3 && this.cur < this.all - 2) {
                    left = this.cur - 2;
                    right = this.cur + 2;
                } else {
                    if (this.cur <= 5) {
                        left = 1;
                        right = 5;
                    } else {
                        right = this.all ;
                        left = this.all - 4;
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++;
            }
            return ar
        },
        showLast: function() {
            if (this.cur == this.all) {
                return false
            }
            return true
        },
        showFirst: function() {
            if (this.cur == 1) {
                return false
            }
            return true
        }

    },
    methods:{
        
        /**
         * 
         * @发送录制请求 开始演讲
         */
        sendStart(el,person){
            let that = this;
            console.log(el);
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.CreateCurriculumData, person).then(
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
				            	
                                    bootAlert.alertSuccess("开始录制成功");
							
				            		deferred.resolve();
                                    that.GetList();
				            	}	
				            }
							// $('#com-loading').modal('hide');	//关闭loading模态框
						}, function(response) {
							bootAlert.alertDanger("开始录制请求错误");
							deferred.reject();
							$('#com-loading').modal('hide');	//关闭loading模态框
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
				// $('#com-loading').modal('hide');	//关闭loading模态框
			}
			
			return deferred.promise();


        },
        /**
         * 
         * @发送结束录制请求 演讲结束
         */
        sendEnd(person){
            
            let that = this;
            
			let deferred = $.Deferred();
			try {
				requsestSer.post(camEmotion.CloseCurriculumData, person).then(
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
									that.GetList();
				            	    deferred.resolve();
				            }
					
						}, function(response) {
							bootAlert.alertDanger("结束录制请求错误");
							deferred.reject();
					
						});
			} catch (e) {
				bootAlert.alertDanger(e.message);
				deferred.reject();
			}
			return deferred.promise();
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
        },
        /**
		 * 查询单个教师信息
		 */
        GetTeacherInfo:function(person){
          var that = this;
    	  var params = {'id':person.teacherid};
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
         * @点击查询按钮
         */
        QueryList: function () {
        	this.pageModel.currentPage = 1;
        	sessionStorage.setItem('class_cur', 1); 
        	this.GetList();
        },
        /**
         * 跳转学生页面事件
         */
		JumpStudentPage : function(person) {
			if (person == null) {
	         	bootAlert.alertDanger("参数有误");
	         	return;
			}
			if (person.classstatus == 0) {
	         	bootAlert.alertDanger("本节课正在上课中，下课后方可观看本节课学生分析");
				bootAlert.alertDanger("本节报告正在录制中，录制结束后方可观看本节报告数据分析");
	         	return;
			}
			location.hash = '#/ClassStudentsList?curriculumId=' + person.id;
		}
    }
});