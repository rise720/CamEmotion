/**
 * Created by houpp on 2018/2/7.
 */
var teacherVue = new Vue({
    el:'#TeacherlistApp',
    data:{
    	 teacherList: [],
    	 teacherInfo: {
         	id:'',
         	headimgurl:'',
         	teachername:'',
         	teachersex:'0',
         	education:'1',
         	birthdate:'',
         	startworkdate:''
         },
    	 index: -1,
         all: 1,       // 总页数
         cur: 1,       // 当前页码
         totalRows: 1, // 总条数
         fromView: 1,  //分页显示从多少条到多少条
         toView: 6,
         pageModel: {
             currentPage:'1',
             pageRecorders:'6',
             teachername:''
         },
         btnflag:'',
         educations:[
                    {text:'高中以下',value:'1'},
                    {text:'高中',value:'2'},
                    {text:'中专',value:'3'},
                    {text:'大专',value:'4'},
                    {text:'本科',value:'5'},
                    {text:'硕士',value:'6'},
                    {text:'博士',value:'7'}
                    ],
         error:{
        	 errorStatus:false,
             errorMsg:''
         }
    },
    mounted:function(){
    	common.checkUrl();
    	initAlert();
//    	bootAlert.alertDanger('我错了');
        console.log('------------------/TeacherlistApp');
        this.QueryList();
        //指定时间控件默认时间
      	this.teacherInfo.birthdate = 
      		DateUtil.editDateTime(0,common.getNowFormatDate(),3,30,"yyyy-MM-dd");
      	this.teacherInfo.startworkdate = new Date().Format("yyyy-MM-dd");
      	//初始化时间控件
      	this.DateInit();
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
		 * @点击查询按钮
		 */
        QueryList: function() {
        	var that = this;
        	initAlert();
        	that.pageModel.currentPage = 1;  
        	that.getTeacherList();
        },
        /**
		 * @页码点击事件
		 */
        btnClick: function(items) {
        	var that = this;
        	initAlert();
            if (items != that.cur) {
            	that.cur = items
            }
            that.pageModel.currentPage = items; 
            //刷新页码
            that.refreshPages();
            that.getTeacherList();
            
        },
        /**
		 * @上一页
		 */
        preClick: function() {
        	var that = this;
        	initAlert();
        	that.cur--;
        	that.pageModel.currentPage = that.cur; 
          //刷新页码
        	that.refreshPages();
        	that.getTeacherList();
        },
        /**
		 * @下一页
		 */
        nexClick: function(items) {
        	var that = this;
        	initAlert();
        	that.cur++;
        	that.pageModel.currentPage = that.cur;  
          //刷新页码
        	that.refreshPages();
        	that.getTeacherList();
        },
        /**
         * 计算页码
         */
        refreshPages:function(){
        	var that = this;
        	that.fromView = (that.cur - 1) * that.pageModel.pageRecorders + 1;
        	that.toView = that.cur * that.pageModel.pageRecorders;
            if(that.toView > that.totalRows){
            	that.toView = that.totalRows;
            }
        },
        /**
		 * 方法说明：获取老师列表
		 */
        getTeacherList: function () {
        	var that = this;
            var params = {'currentPage':that.pageModel.currentPage,
            			  'pageRecorders':that.pageModel.pageRecorders,
            		      'teachername':that.pageModel.teachername};
            try {
            	requsestSer.post(camEmotion.findTeacherBypagination, params).then(function (data) {
                    if (data.meta.success) {
                    	that.teacherList = data.data.objList;
                    	that.all = data.data.totalPages;
                    	that.cur = data.data.currentPage;
                    	that.totalRows = data.data.totalRows;
                        
                        if(that.pageModel.currentPage > that.totalRows){
                        	that.fromView = that.cur;
                        	that.toView = that.totalRows;
                        }
                    } else {
                        bootAlert.alertDanger(that.fromatterError(data.meta.retCode));
                    }
                }, function(response) {
                	bootAlert.alertDanger('获取老师数据错误');
					deferred.reject();
				});
            } catch (e) {
            	bootAlert.alertDanger(e.message);
            }
        },
        /**
		 * 方法说明：打开信息页(修改)
		 * 
		 * @param obj
		 */
        openUpdateTeacherInfo: function (obj) {
        	var that = this;
        	initAlert();
        	that.btnflag='update';
        	that.teacherInfo.id = obj.id;
        	that.teacherInfo.headimgurl = obj.headimgurl;
        	that.teacherInfo.teachername = obj.teachername;
        	that.teacherInfo.teachersex = obj.teachersex;
        	that.teacherInfo.education = obj.education;
        	that.teacherInfo.birthdate = obj.birthdate;
        	that.teacherInfo.startworkdate = obj.startworkdate;
        	
        	that.error.errorStatus= false;
        },
        
        /**
		 * 方法说明：打开信息页(添加)
		 * 
		 * @param obj
		 */
        openInsertUserInfo: function () {
        	var that = this;
        	initAlert();
        	that.btnflag='insert';
        	that.teacherInfo.id = '';
        	that.teacherInfo.teachername = "";
        	that.teacherInfo.teachersex = "0";
        	that.teacherInfo.education = "1";
        	that.teacherInfo.headimgurl = "";
        	that.teacherInfo.birthdate = DateUtil.editDateTime(0,common.getNowFormatDate(),3,30,"yyyy-MM-dd");;
        	that.teacherInfo.startworkdate = new Date().Format("yyyy-MM-dd");
        	
        	that.error.errorStatus= false;
        },
        /**
		 * 方法说明：修改老师信息
		 */
        updateTeacherInfo: function (userInfo) {
        	var that = this;
        	if(that.teacherInfo.teachername === undefined 
        		   || that.teacherInfo.teachername.replace(/(^\s*)|(\s*$)/g, "").length == 0){
        		that.error.errorStatus = true;
        		that.error.errorMsg = "姓名不能为空";
                return false;
        	}
        	
        	if(that.teacherInfo.teachername.length > 50){
        		that.error.errorStatus = true;
        		that.error.errorMsg = "姓名长度不能超过50";
                return false;
        	}else{
        		that.error.errorStatus = false;
        	}
        	        	
        	if(that.teacherInfo.birthdate ===undefined 
        			|| that.teacherInfo.birthdate.replace(/(^\s*)|(\s*$)/g, "").length == 0){
        		that.error.errorStatus = true;
        		that.error.errorMsg = "出生日期不能为空";
                return false;
        	}
        	if(that.teacherInfo.startworkdate ===undefined 
         			|| that.teacherInfo.startworkdate.replace(/(^\s*)|(\s*$)/g, "").length == 0){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能为空";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.birthdate).getTime() >= new Date().getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "出生日期不能大于当前时间";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.startworkdate).getTime() >= new Date().getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能大于当前时间";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.startworkdate).getTime() <= new Date(that.teacherInfo.birthdate).getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能小于出生日期";
                 return false;
         	}
        	that.error.errorStatus = false;
        	var params = that.teacherInfo;
            try {
            	requsestSer.post(camEmotion.modifyTeacherInfo, params).then(function (data) {
                	if(data.meta.success){
                		//关闭窗口
                		that.error.errorStatus = false;
                		$("#tearModal").modal('hide');
                		that.getTeacherList();
                		bootAlert.alertSuccess('修改成功');
                	}else{
                		that.error.errorStatus = true;
                		that.error.errorMsg = that.fromatterError(data.meta.retCode);
                	}
                }, function(response) {
                	that.error.errorStatus = true;
                	that.error.errorMsg = '修改老师信息错误';
					deferred.reject();
				});
            } catch (e) {
            	that.error.errorStatus = true;
            	that.error.errorMsg = e.message;
            }
        },

        /**
		 * 方法说明：添加老师信息
		 */
        InsertTeacherInfo: function (e) {
        	var that = this;
        	if(that.teacherInfo.teachername === undefined 
         		   || that.teacherInfo.teachername.replace(/(^\s*)|(\s*$)/g, "").length == 0){
        		that.error.errorStatus = true;
        		that.error.errorMsg = "姓名不能为空";
                 return false;
         	}
         	
         	if(that.teacherInfo.teachername.length > 50){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "姓名长度不能超过50";
                 return false;
         	}else{
         		that.error.errorStatus = false;
         	}
         	        	
         	if(that.teacherInfo.birthdate ===undefined 
         			|| that.teacherInfo.birthdate.replace(/(^\s*)|(\s*$)/g, "").length == 0){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "出生日期不能为空";
                 return false;
         	}
         	if(that.teacherInfo.startworkdate ===undefined 
         			|| that.teacherInfo.startworkdate.replace(/(^\s*)|(\s*$)/g, "").length == 0){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能为空";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.birthdate).getTime() >= new Date().getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "出生日期不能大于当前时间";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.startworkdate).getTime() >= new Date().getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能大于当前时间";
                 return false;
         	}
         	
         	if(new Date(that.teacherInfo.startworkdate).getTime() <= new Date(that.teacherInfo.birthdate).getTime()){
         		that.error.errorStatus = true;
         		that.error.errorMsg = "参加工作日期不能大于出生日期";
                 return false;
         	}
         	
         	that.error.errorStatus = false;
         	var params = that.teacherInfo;
            try {
            	requsestSer.post(camEmotion.saveTeacherInfo, params).then(function (data) {
            		if(data.meta.success){
                		//关闭窗口
            			that.error.errorStatus = false;
                		$("#tearModal").modal('hide');
                		that.getTeacherList();
                		bootAlert.alertSuccess('添加成功');
                	}else{
                		that.error.errorStatus = true;
                		that.error.errorMsg = that.fromatterError(data.meta.retCode);
                	}
                }, function(response) {
                	that.error.errorStatus = true;
                	that.error.errorMsg = '添加老师信息错误';
					deferred.reject();
				});
            } catch (e) {
            	that.error.errorStatus = true;
            	that.error.errorMsg = e.message;
            }
        },
                

        /**
		 * 方法说明：删除个人信息
		 */
        delectTeacher: function (obj) {   
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
                    	var params = {"id":obj.id};
                        try {
                        	requsestSer.post(camEmotion.deleteTeacherInfo, params).then(function (data) {
                        		if(data.meta.success){
                        			that.getTeacherList();
                        			bootAlert.alertSuccess('删除成功');
                            	}else{
                            		bootAlert.alertDanger(that.fromatterError(data.meta.retCode));
                            	}
                            }, function(response) {
                            	bootAlert.alertDanger('删除老师信息错误');
            					deferred.reject();
            				});
                        } catch (e) {
                            bootAlert.alertDanger(e.message);
                        }
                    }
                });
        },
        /**
         * 方法说明：上传文件
         * @param event
         * @param type 业务上传类型（教师头像，评论）
         */
        upload: function (event, type) {
        	let that = this;
        	let supportedTypes = ['image/jpg', 'image/jpeg', 'image/png'];
        	let file = event.target.files[0];

        	let typeFlag = false;
        	for(let n in supportedTypes){
        		if(supportedTypes[n] == file.type){
        			typeFlag = true;
        			break;
        		}
        	}
        	if(!typeFlag){
        		that.error.errorStatus = true;
        		that.error.errorMsg = "请上传正确的图片格式文件[" + supportedTypes.join(' - ').replace(/image\//g,'') + "]";
        		return;
        	} 
            console.log();
            var formData = new FormData();
            formData.append('files',file);
            formData.append('uploadtype',type);
            try {
            	fileRequsestSer.post(camEmotion.upLoad,formData).then(function(data){
            		 if (data.meta.success) {
            			 that.teacherInfo.headimgurl = data.data.webUrl;
            			 that.error.errorStatus = false;
                     } else {
                    	 that.error.errorStatus = true;
                    	 that.error.errorMsg = data.meta.message;
                     }
            		 $(".headimg").val('');
                },function(data){
                	$(".headimg").val('');
                });
            }catch (e){
            	$(".headimg").val('');
            	that.error.errorStatus = true;
            	that.error.errorMsg = "上传图片失败，请稍后重试！";
            }
        },
        /**
    	 * @初始化时间控件
    	 */
        DateInit: function() {
        	var that = this;
            var startworkdate = {
                format: "YYYY-MM-DD",
                isinitVal: true,
                isTime: true,
                initAddVal:[0],
                choosefun: function(elem, datas) {
                	that.teacherInfo.startworkdate = datas;
                },
                //清除日期后的回调, elem当前输入框ID, val当前选择的值
                clearfun:function(elem, val) {
                	that.teacherInfo.startworkdate = "";
                },      
                //点击确定后的回调, elem当前输入框ID, val当前选择的值
                okfun:function(elem, val) {
                	that.teacherInfo.startworkdate = val;
                },
                //层弹出后的成功回调方法, elem当前输入框ID
                success:function(elem) {
                	
                }
            };
            var birthdate = {
                format: "YYYY-MM-DD",
                isinitVal: true,
                isTime: true,
                initAddVal:[0],
                choosefun: function(elem, datas) {
                	that.teacherInfo.birthdate = datas;
                },
                //清除日期后的回调, elem当前输入框ID, val当前选择的值
                clearfun:function(elem, val) {
                	that.teacherInfo.birthdate = "";
                },      
                //点击确定后的回调, elem当前输入框ID, val当前选择的值
                okfun:function(elem, val) {
                	that.teacherInfo.birthdate = val;
                },
                //层弹出后的成功回调方法, elem当前输入框ID
                success:function(elem) {
                	
                }
            };
            $.jeDate('#startworkdate', startworkdate);
            $.jeDate('#birthdate', birthdate);
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

