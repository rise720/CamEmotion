/**
 * Created by houpp on 2018/2/2.
 */
new Vue({
    el: '#userApp',
    data: {
        userList: [],
        userInfo: {},
        checkedNames: [],
        index: -1,
        userId: '',    // 查询条件（用户ID）
        all: 1,       // 总页数
        cur: 1,        // 当前页码
        pageModel: {
            currentPage: '1',
            pageRecorders: '6',
            useraccount: '',
        },
        error:{
     	   errorStatus:false,
           errorMsg:''
        }
    },
    mounted: function () {
    	common.checkUrl();
        initAlert();
        this.GetUserList();
    },

    /**
     * @分页事件
     */
    computed: {
        indexs: function () {
            var that = this;
            var left = 1;
            var right = that.all;
            var ar = [];
            if (that.all > 5) {
                if (that.cur > 3 && that.cur < that.all - 2) {
                    left = that.cur - 2;
                    right = that.cur + 2;
                } else {
                    if (that.cur <= 5) {
                        left = 1;
                        right = 5;
                    } else {
                        right = that.all;
                        left = that.all - 4;
                    }
                }
            }
            while (left <= right) {
                ar.push(left);
                left++;
            }
            return ar
        },
        showLast: function () {
            var that = this;
            if (that.cur == that.all)
                return false;
            return true;
        },
        showFirst: function () {
            var that = this;
            if (that.cur == 1)
                return false;
            return true;
        }
    },
    methods: {
        /**
         * @点击查询按钮
         */
        QueryList: function () {
            var that = this;
            that.pageModel.currentPage = 1;
            that.GetUserList();
        },
        /**
         * @页码点击事件
         */
        btnClick: function (items) {
            var that = this;
            if (items != that.cur)
                that.cur = items;
            that.pageModel.currentPage = items;
            that.GetUserList();
        },
        /**
         * @上一页
         */
        preClick: function () {
            var that = this;
            that.cur--;
            that.pageModel.currentPage = that.cur;
            that.GetUserList();
        },
        /**
         * @下一页
         */
        nexClick: function (items) {
            var that = this;
            that.cur++;
            that.pageModel.currentPage = that.cur;
            that.GetUserList();
        },

        /**
         * 方法说明：获取用户列表
         */
        GetUserList: function () {
            var that = this;
            var params = {'currentPage':that.pageModel.currentPage,
		      			  'pageRecorders':that.pageModel.pageRecorders,
		      			  'useraccount':that.pageModel.useraccount
		      			  };
            try {
            	requsestSer.post(camEmotion.Userlist, params).then(function (data) {
                    if (data.meta.success) {
                    	that.userList = data.data.objList;
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
         * 打开用户信息窗口
         */
        ShowUserInfo:function(infos){
        	var that = this;
        	that.error.errorStatus = false;
        	that.error.errorMsg = "";
        	that.userInfo = {};
        	
        	if (infos != null && infos.id > 0) {
//        		this.userInfo = this.DeepCopy(infos);
//                if (infos.cright != null) {
//                    that.checkedNames = infos.cright.split(',');
//                }
        		requsestSer.post(camEmotion.SelectUser, infos).then(function (data) {
        			 if (data.meta.success) {
        		
        				that.userInfo = data.data;
        				that.checkedNames = data.data.cright.split(',');
                     } else {
  	            	   bootAlert.alertDanger(data.meta.message);
                     }
                 },function (response) {
 	            	   bootAlert.alertDanger("请求失败");
                 });
			}else{
	            that.checkedNames = [];
			}
            $('#userModal').modal('show');
        },
        /**
         * 检测保存信息
         */
        CheckInfo:function(infos){
        	if (infos == null) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "请输入用户信息";
            	return false;
			}
			if (infos.useraccount == null || infos.useraccount.length <= 0 || infos.useraccount.length > 20) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "账号不能为空，且不能大于20个字符";
            	return false;
			}
        	if (infos.nickname == null || infos.nickname.length <= 0 || infos.nickname.length > 50) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "昵称不能为空，且不能大于50个字符";
            	return false;				
			}
        	if (infos.oldPassword == null || infos.oldPassword.length <= 0 || infos.oldPassword.length >10) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "新密码不能为空，且不能大于10个字符";
            	return false;					
			}
        	if (infos.newPassword == null || infos.newPassword.length <= 0 || infos.newPassword.length >10) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "确认密码不能为空，且不能大于10个字符";
            	return false;					
			}
        	if (infos.newPassword != infos.oldPassword) {
            	this.error.errorStatus = true;
            	this.error.errorMsg = "两次输入的密码不一致";
            	return false;	
			}
        	if (this.checkedNames == null || this.checkedNames.length <= 0) {
                this.error.errorStatus = true;
            	this.error.errorMsg = "权限不能为空";
            	return false;	
            }
        	return true;
        },
        /**
         * 保存数据
         */
        SaveUser:function(){
        	var that = this;
        	var result = that.CheckInfo(that.userInfo);
        	if (!result) {
				return;
			}
        	that.userInfo.cpassword = that.userInfo.oldPassword;
        	that.userInfo.cright = that.GetCright(that.checkedNames);
        	that.userInfo.createuser = sessionStorage.getItem('createUser');
        	that.userInfo.updateuser = sessionStorage.getItem('createUser');
        	var url = "";
        	if (that.userInfo.id != null && that.userInfo.id > 0) {
        		url = camEmotion.UpdateUser;
            	that.userInfo.updatedate = new Date();
			}else {
				url = camEmotion.InsertUser;
			}
        	
        	//字符串转数组并排序再转成字符串
        	var crights = that.userInfo.cright.split(",");
        	crights = crights.sort(that.NumAscSort);
        	that.userInfo.cright = crights.join(",");
        	
        	try {
	        	requsestSer.post(url, that.userInfo).then(function (data) {
		               if (data.meta.success) {
		            	   bootAlert.alertSuccess("保存成功");
		                   $('#userModal').modal('hide');
		            	   that.GetUserList();
		               } else {
		            	  that.error.errorStatus = true;
		            	  that.error.errorMsg = data.meta.message;
		               }
		           },function (response) {
		            	  that.error.errorStatus = true;
		            	  that.error.errorMsg = "请求失败";
		           });
	       } catch (e) {
		       	  that.error.errorStatus = true;
		      	  that.error.errorMsg = e.message;
	       }
        },
        /**
         * 方法说明：删除个人信息
         */
        DelectUser: function (infos) {
            var that = this;
            var curr_user = sessionStorage.getItem('createUser');
            if (curr_user == infos.useraccount) {
                bootAlert.alertWarning('不能删除当前登录用户');
                return false;
            }
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
	     	       	requsestSer.post(camEmotion.DeleteUser, params).then(function (data) {
	     	               if (data.meta.success) {
	     	            	   that.GetUserList();
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
         * 获取权限
         */
        GetCright:function(checkedNames){
           
    		var cright = "";
        	if (checkedNames != null && checkedNames.length > 0) {
				for (var i = 0; i < checkedNames.length; i++) {
					if (i == 0) {
						cright += checkedNames[i];
					}else {
						cright += "," + checkedNames[i];
					}
				}
			}
        	return cright;
        },
        /**
         * 深copy适用于复杂对象
         */
        DeepCopy:function(obj){
        	if(typeof obj != 'object'){
                return obj;
            }
            var newobj = {};
            for ( var attr in obj) {
                newobj[attr] = this.DeepCopy(obj[attr]);
            }
            return newobj;
        },
        /**
         * 浅copy适用于单个对象
         */
        Copy:function(obj){
            var newobj = {};
            for ( var attr in obj) {
                newobj[attr] = obj[attr];
            }
            return newobj;
        },
        NumAscSort:function (a,b){
         return parseInt(a) - parseInt(b);
        }
    }
});




