var vm = new Vue({
    el: '#classcommentApp',
    data: {
    	PublishFlag: true, // true：显示为文本 false：上传文件,
        progress: 0,
        progressStyle: {},
        fileName: null,
        
        index: -1,
        all: 1,       // 总页数
        cur: 1,        // 当前页码
        pageModel: {
            currentPage:'1',
            pageRecorders:'6',
            rule:'DESC',
			expression:'id',
			curriculumId:''
        },
        list: [],
        info:{},
        curriculumId : 0
    },
    mounted: function () {
    	common.checkUrl();
    	initAlert();
        $('#context').css('min-height', $(window).height() - 150 + 'px');
        $('.video-box').height($(window).height() - 170 + 'px');
        $('.cac-chart').height($(window).height() - 140 + 'px');

        var par = vipspa.parse();
        this.curriculumId = par.param.curriculumId;
        this.pageModel.curriculumId = this.curriculumId;
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
    methods: {
        /**
		 * @页码点击事件
		 */
        btnClick: function(items) {
            if (items != this.cur) {
                this.cur = items
            }
            this.pageModel.currentPage = items;              
            this.GetList();
            
        },
        /**
		 * @上一页
		 */
        preClick: function() {
            this.cur--;
            this.pageModel.currentPage = this.cur;              
            this.GetList();
        },
        /**
		 * @下一页
		 */
        nexClick: function(items) {
            this.cur++;
            this.pageModel.currentPage = this.cur;              
            this.GetList();
        },
        /**
		 * 方法说明：获取老师列表
		 */
        GetList: function () {
        	var that = this;
            var params = {'currentPage':that.pageModel.currentPage,
            			  'pageRecorders':that.pageModel.pageRecorders,
            			  'rule':that.pageModel.rule,
            			  'expression':that.pageModel.expression,
            			  'curriculumid':that.pageModel.curriculumId
            			  };
            try {
            	requsestSer.post(camEmotion.CurriculumEvaluateGetList, params).then(function (data) {
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
        DeleteId:function(infos){
        	var that = this;
        	Modal.alert({
                msg: '内容',
                title: '标题',
                btnok: '确定',
                btncl: '取消'
            });
            Modal.confirm({msg: "是否删除？"}) .on(function (e) { 
	        	if (e) {
	               var params = {'id':infos.id};
	     	       try {
	     	       	requsestSer.post(camEmotion.CurriculumEvaluateDeleteId, params).then(function (data) {
	     	               if (data.meta.success) {
	     	            	   that.GetList();
	     	            	   bootAlert.alertSuccess("删除成功");
	     	               } else {
	     	            	   bootAlert.alertDanger(data.meta.message);
	     	               }
	     	           }).catch(function (response) {
	                 	   bootAlert.alertDanger("请求失败");
	     	           });
	     	       } catch (e) {
	     	    	  bootAlert.alertDanger(e.message);
	     	       }
	            }
	        });
        },
        
        /**
		 * 方法说明：上传文件
		 * 
		 * @param event
		 * @param type
		 *            业务上传类型（教师头像，评论）
		 */
        upload: function (event, type) {
            var that = this;
            that.fileName = event.target.files[0].name;
            var formData = new FormData();
            formData.append('files', event.target.files[0]);
            formData.append('uploadtype', "3");
            try {
                fileRequsestSer.uploadPhoto(camEmotion.upLoad, formData, function (e) {
                    that.PublishFlag = false;
                    var percent = Math.floor(e.loaded / e.total * 100);
                    that.progress = percent;
                    that.progressStyle = {width: percent + '%'};
                }, function (data) {
                     if (data.meta.success) {
                    	 bootAlert.alertSuccess("上传文件成功");
                    	 that.info.filetype = data.data.fileType;
                    	 that.info.fileaddr = data.data.webUrl;
  	                 } else {
  	                     bootAlert.alertDanger(data.meta.message);
  	                     that.uploadCancel();
  	                 }
                     $('#file').val('');
                },function(){
                	$('#file').val('');
                });
            } catch (e) {
            	$('#file').val('');
            }
        },

        /**
		 * 方法说明：删除文件
		 */
        uploadCancel: function () {
            var that = this;
            that.PublishFlag = true;
            that.progress = 0;
            that.progressStyle = {width: 0 + '%'};
        },
        /**
		 * 新增评论
		 */
        InsertEvaluate:function(){
  	       try {
  	           var that = this;
  	    	   if (that.info == null) {
  	    		   bootAlert.alertDanger("参数有误");
  	    		   return;
  	    	   }
  	    	   if (that.curriculumId == null || that.curriculumId <= 0) {
  	    		   bootAlert.alertDanger("课程ID为空");
	    		   return;
  	    	   }
  	    	   if (that.info.name == null || that.info.name.length <= 0 || that.info.name.length > 20) {
  	    		   bootAlert.alertDanger("评论人姓名必须填写，且不能大于20个字符");
  	    		   return;
  	    	   }
  	    	   if (that.info.work == null || that.info.work.length <= 0 || that.info.work.length > 60) {
  	    		   bootAlert.alertDanger("评论人职务必须填写，且不能大于60个字符");
  	    		   return;
  	    	   }
  	    	   if (that.info.evaluatecontent == null || that.info.evaluatecontent.length <= 0 || that.info.evaluatecontent.length > 520) {
             	   bootAlert.alertDanger("评论信息必须填写，且不能大于520个字符");
  	    		   return;
  	    	   }
  	    	   if (that.info.fileaddr != null) {
  				  var infoLength = that.info.fileaddr.length;
  				  if (infoLength > 520) {
  	  	    		  bootAlert.alertDanger("文件地址过长");
  				      return;
  				  }
  				  if (infoLength > 0) {
  					if (that.info.filetype < 0) {
  	  	    		  	bootAlert.alertDanger("文件类型有误");
						return;
					}
				  }
	  		   }
  	    	   that.info.evaluatorinfo = "（"+that.info.work+"）" + that.info.name;
  	    	   that.info.curriculumid = that.curriculumId;
  	       	requsestSer.post(camEmotion.CurriculumEvaluateSave, that.info).then(function (data) {
  	               if (data.meta.success) {
	            	   bootAlert.alertSuccess("添加成功");
	            	   that.GetList();
	            	   that.info = {};
	                   that.uploadCancel();
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
    }
});