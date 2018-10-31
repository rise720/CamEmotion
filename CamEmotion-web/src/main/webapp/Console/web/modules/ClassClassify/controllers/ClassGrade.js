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
        teacherInfo: {}
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
        delectCurriculum:function(id){
        	initAlert();
        	var that = this;
        	Modal.alert({
                msg: '内容',
                title: '标题',
                btnok: '确定',
                btncl: '取消'
            });
            Modal.confirm({msg: "是否删除？"})
                .on(function (e) {
                    if (e) {
                    	$('#com-loading').modal('show');
                    	var params = {"id":id};
                        try {
                        	requsestSer.post(camEmotion.deleteCurriculumTbl, params).then(function (data) {
                        		if(data.meta.success){
                        			that.GetList();
                        			bootAlert.alertSuccess('删除成功');
                        			$('#com-loading').modal('hide');
                            	}else{
                            		$('#com-loading').modal('hide');
                            		bootAlert.alertDanger(that.fromatterError(data.meta.retCode));
                            	}
                            }, function(response) {
                            	$('#com-loading').modal('hide');
                            	bootAlert.alertDanger('删除课堂信息错误');
            					deferred.reject();
            				});
                        } catch (e) {
                        	$('#com-loading').modal('hide');
                            bootAlert.alertDanger(e.message);
                        }
                    }
                });
        },
        
        setCurriculumLevel:function(id,curriculumLevel){
        	var that = this;
        	if(curriculumLevel == 0){
        		curriculumLevel = 1;
        	}else{
        		curriculumLevel = 0;
        	}
        	$('#com-loading').modal('show');
        	var params = {'id':id, 'curriculumLevel':curriculumLevel};
        	try {
            	requsestSer.post(camEmotion.setCurriculumLevel, params).then(function (data) {
                	if(data.meta.success){
                		that.GetList();
            			bootAlert.alertSuccess('设置成功');
            			$('#com-loading').modal('hide');
                	}else{
                		bootAlert.alertDanger(that.fromatterError(data.meta.retCode));
                		$('#com-loading').modal('hide');
                	}
                }, function(response) {
                	bootAlert.alertDanger("设置失败");
                	$('#com-loading').modal('hide');
					deferred.reject();
				});
            } catch (e) {
            	bootAlert.alertDanger(e.message);
            	$('#com-loading').modal('hide');
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
			default:
				break;
			}
        	return msg;
        }
    }
});