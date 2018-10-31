var vm = new Vue({
    el: '#setupApp',
    data: {
        list: [],
        info:{
        	fillpolysize:1,
        	identificationFlag:'0'
        },
        fillpolyForm: {'x1':'','y1':'','x2':'','y2':'','x3':'','y3':'','x4':'','y4':''},// 添加坐标的表格
        fillpolyList: [],// 坐标的列表
        curriculumId : '0',
        drawdisplayList:[
    	              {text: '不实时播放视频', value: '0'},
    	              {text: '实时播放录制视频', value: '1'},
    	              {text: '实时播放分析视频', value: '2'},
    				  {text: '全部播放', value: '3'}
    	              ],
        error:{
     	   errorStatus:false,
           errorMsg:'',
           setupalert:-1,	//	提示的样式控制(-1 错误，0正常 1 警告)
        },
        
        fromflag:true, 	// 提交的按键样式 控制
    },
    mounted: function () {
    	common.checkUrl();
    	initAlert();
        this.GetSettingList();
    },
//    watch:{
//    	'info.fillpolysize':{
//    		handler:function(oldval,newval){
//        		var that = this;
//        		that.fillpolyList = [];
//        		var arr  = {'x1':'','y1':'','x2':'','y2':'','x3':'','y3':'','x4':'','y4':''};
//        		for(var i = 0 ; i< that.info.fillpolysize; i++){
//        			that.fillpolyList.push(arr);
//        		}
//        	},// 深度观察
//            deep:true
//    	}
//    },
    methods: {
    	/**
    	 * 添加坐标
    	 */
    	insertFillpoly:function(){
    		var fillpolyFlay = false;
    		var that = this;
    		if(that.fillpolyForm.x1==null||that.fillpolyForm.x1==''){
    			fillpolyFlay = true;
    		} else if(that.fillpolyForm.y1==null||that.fillpolyForm.y1==''){
    			fillpolyFlay = true;
    		} else if(that.fillpolyForm.x2==null||that.fillpolyForm.x2==''){
    			fillpolyFlay = true;
			} else if(that.fillpolyForm.y2==null||that.fillpolyForm.y2==''){
				fillpolyFlay = true;
			} else if(that.fillpolyForm.x3==null||that.fillpolyForm.x3==''){
				fillpolyFlay = true;
			} else if(that.fillpolyForm.y3==null||that.fillpolyForm.y3==''){
				fillpolyFlay = true;
			} else if(that.fillpolyForm.x4==null||that.fillpolyForm.x4==''){
				fillpolyFlay = true;
			} else if(that.fillpolyForm.y4==null||that.fillpolyForm.y4==''){
				fillpolyFlay = true;
			}
    		
    		if(fillpolyFlay){
    			this.error.errorStatus = true;
            	this.error.errorMsg = "遮罩层坐标填写不完整！";
            	this.error.setupalert = -1;
            	return false;
    		}
    		
    		var arr = [that.fillpolyForm.x1,that.fillpolyForm.y1,
    		           that.fillpolyForm.x2,that.fillpolyForm.y2,
    		           that.fillpolyForm.x3,that.fillpolyForm.y3,
    		           that.fillpolyForm.x4,that.fillpolyForm.y4];
    		// 从头插入
    		that.fillpolyList.unshift(arr);
    		that.fillpolyForm = {'x1':'','y1':'','x2':'','y2':'','x3':'','y3':'','x4':'','y4':''};
    	},
    	
    	/**
    	 * 删除
    	 */
    	deleteFillpoly:function(index){    
    		var that = this;
			that.fillpolyList.splice(index,1);   
    	},
    	
        /**
		 * 方法说明：获取配置列表
		 */
        GetSettingList: function () {
        	var that = this;
            try {
            	requsestSer.post(camEmotion.getSettingInfo).then(function (data) {
                    if (data.meta.success) {
                    	that.list = data.data;
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
         * 打开配置信息画面
         */
        ShowInfo:function(infos){
        	var that = this;
        	that.fillpolyList = [];
        	that.fillpolyForm = {'x1':'','y1':'','x2':'','y2':'','x3':'','y3':'','x4':'','y4':''};
        	this.error.errorStatus = false;
        	this.error.errorMsg = "";
        	this.info = {
        		fillpolysize:'0',
        		identificationFlag:'0'
        	};
        	if (infos != null && infos.hostno > 0) {
        		this.info = this.Copy(infos);
        		if (this.info.fillpolysize > 0) {
		    		// 遮罩层
		    		var str = this.info.fillpoly.replace(/\|/g,',');
		    		var arr = [];
		    		if(str.indexOf('#')>-1){
		    			arr = str.split('#');
		    			arr.forEach(function(val){
		    				that.fillpolyList.push(val.split(','));
		    			});
		    		}else {
		    			arr = str.split(',');
	    				that.fillpolyList.push(arr);
					}    
				} 
			}else {
				this.info.drawdisplay = '1';
				this.info.fillpolysize = 1;
				this.info.maxnumfaces = '10';
				this.info.hostno = '0';
				this.info.identificationFlag = '0';
			}
            $('#setupModal').modal('show');
        },
        /**
         * 保存一条数据
         */
        SaveInfo:function(){
	        var that = this;
	        that.info.fillpolysize = that.fillpolyList.length;
	        that.info.fillpoly = "";
	        for (var i = 0; i < that.fillpolyList.length; i++) {
	        	if (i != 0) {
	        		that.info.fillpoly += "#";
				}
	        	var fillpolyArr = that.fillpolyList[i];
	        	for (var j = 0; j < fillpolyArr.length; j++) {
					var fillpoly = fillpolyArr[j];
					if (j != 0 && (j % 2) != 0) {
						that.info.fillpoly += ",";
					} else if (j != 0 && (j % 2) == 0) {
						that.info.fillpoly += "|";
					}
					that.info.fillpoly += fillpoly;
				}
			}					
	        var result = this.CheckSaveInfo(that.info);
        	if (!result) {
				return;
			}
   	       try {
   	       	requsestSer.post(camEmotion.saveSettingInfo, that.info).then(function (data) {
   	               if (data.meta.success) {
   	            	   if (parseInt(that.info.hostno) > 0) {
   	 	            	   bootAlert.alertSuccess("修改成功");
   	            	   }else{
   	            		   bootAlert.alertSuccess("添加成功");
   	            	   }
 	                   $('#setupModal').modal('hide');
 	            	   that.GetSettingList();
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
         * 测试连接
         */
        TestConnect:function(){
	        var that = this;	        
	        that.fromflag = false;	        
        	var result = this.CheckSaveInfo(that.info);
        	if (!result) {
        		that.fromflag = true;
				return;
			}
        	that.error.errorStatus = true;
	        that.error.errorMsg = "正在测试连接...";
       	  	that.error.setupalert = 1;
   	       try {
   	       	requsestSer.posts(camEmotion.testConnect, that.info, 100000).then(function (data) {
   	               if (data.meta.success) {
 	            	  that.error.errorStatus = true;
 	            	  that.error.errorMsg = "连接正常";
 	            	  that.error.setupalert = 0;
 	               } else {
 	            	  that.error.errorStatus = true;
 	            	  that.error.errorMsg = data.meta.message;
 	            	  that.error.setupalert = -1;
 	               }
   	               that.fromflag = true;
   	           },function (response) {
 	            	  that.error.errorStatus = true;
 	            	  that.error.errorMsg = "请求失败";
 	            	  that.error.setupalert = -1;
 	            	  that.fromflag = true;
   	           });
   	       } catch (e) {
          	  that.error.errorStatus = true;
         	  that.error.errorMsg = e.message;
         	  that.error.setupalert = -1;
         	  that.fromflag = true;
   	       }
        },
        /**
         * 检查保存参数
         */
        CheckSaveInfo:function(infos){
        	if (infos == null) {
        		this.error.errorStatus = true;
                this.error.setupalert = -1;
                this.error.errorMsg = "请输入配置项";
                return false;
			}
        	if (infos.hostname == null || infos.hostname.length <= 0 || infos.hostname.length > 200) {
        		this.error.errorStatus = true;
                this.error.setupalert = -1;
                this.error.errorMsg = "主机名称有误，不能为空且不能大于200字符";
                return false;
			}
        	if (infos.hostip == null || infos.hostip.length <= 0 || infos.hostip.length > 15) {
        		this.error.errorStatus = true;
                this.error.setupalert = -1;
                this.error.errorMsg = "主机IP有误，不能为空且不能大于15字符";
                return false;
			}
        	if (!this.checkIp(infos.hostip)) {
        		this.error.errorStatus = true;
                this.error.setupalert = -1;
                this.error.errorMsg = "主机IP格式不正确";
                return false;
			}
        	if (infos.cameraip == null || infos.cameraip.length <= 0 || infos.cameraip.length > 15) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "摄像机IP有误，不能为空且不能大于15字符";
                return false;
			}
        	if (!this.checkIp(infos.cameraip)) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "摄像机IP格式不正确";
                return false;
			}
        	if (infos.cameraip == infos.hostip) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "主机IP不能与摄像机IP一致";
                return false;
			}
        	if (infos.camaddrm == null || infos.camaddrm.length <= 0 || infos.camaddrm.length > 200) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "主码流地址有误，不能为空且不能大于200字符";
                return false;
			}
        	if (infos.camaddra == null || infos.camaddra.length <= 0 || infos.camaddra.length > 200) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "辅码流地址有误，不能为空且不能大于200字符";
                return false;
			}
//        	if (infos.shareaddr == null || infos.shareaddr.length <= 0 || infos.shareaddr.length > 500) {
//        		this.error.errorStatus = true;
//        		this.error.setupalert = -1;
//                this.error.errorMsg = "图片保存地址有误，不能为空且不能大于500字符";
//                return false;
//			}
        	if (infos.fillpolysize != null && infos.fillpolysize.length > 0 ) {
        		if (!this.checkNumber(infos.fillpolysize)) {
        			this.error.errorStatus = true;
        			this.error.setupalert = -1;
                    this.error.errorMsg = "遮罩个数只能输入数字";
                    return false;				
				}
        		if (parseInt(infos.fillpolysize) > 0) {
        			if (infos.fillpoly == null || infos.fillpoly.length <= 0 || infos.fillpoly.length > 500) {
                		this.error.errorStatus = true;
                		this.error.setupalert = -1;
                        this.error.errorMsg = "遮罩规则有误，不能为空且不能大于500字符";
                        return false;					
    				}
				}
			}
        	if (infos.maxnumfaces == null || infos.maxnumfaces.length <= 0) {
        		this.error.errorStatus = true;
        		this.error.setupalert = -1;
                this.error.errorMsg = "面部识别个数不能为空";
                return false;
			}
        	if (!this.checkNumber(infos.maxnumfaces)) {
    			this.error.errorStatus = true;
    			this.error.setupalert = -1;
                this.error.errorMsg = "面部识别个数只能输入数字";
                return false;				
			}
        	if (!this.checkHostCam(infos)) {
				return false;
			}
        	this.error.errorStatus = false;
        	this.error.setupalert = -1;
            this.error.errorMsg = "";
            return true;
        },
        /**
         * 删除一条记录
         */
        DeleteSetting:function(infos){
        	var that = this;
            Modal.alert({
                msg: '内容',
                title: '标题',
                btnok: '确定',
                btncl: '取消'
            });
            Modal.confirm({msg: "是否删除？"}) .on(function (e) { 
	        	if (e) {
		           var params = {'hostno':infos.hostno};
	     	       try {
	     	       	requsestSer.post(camEmotion.deleteSettingInfo, params).then(function (data) {
	     	               if (data.meta.success) {
	     	            	   that.GetSettingList();
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
    	/*
    	 * 检查字段是否符合ip地址的规范
    	 */
    	checkIp: function(val){
    		var pattern1 = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
    		var reg = new RegExp(pattern1);
    		return reg.test(val);
    	},
    	/*
    	 * 检查字段是否都是数字
    	 */
    	checkNumber: function(val){
    		var reg = new RegExp("^[0-9]*$");
    		return reg.test(val);
    	},
    	/**
    	 * 检测主机和摄像机是否重复添加
    	 */
    	checkHostCam:function(infos){
    		var list = this.list;
    		for (var i in list) {
    			if (infos.hostname == list[i].hostname && infos.hostno != list[i].hostno) {
        			this.error.errorStatus = true;
        			this.error.setupalert = -1;
                    this.error.errorMsg = "主机名称：" + list[i].hostname + " 已经存在";
					return false;
				}
//    			if (infos.hostip == list[i].hostip && infos.hostno != list[i].hostno) {
//        			this.error.errorStatus = true;
//        			this.error.setupalert = -1;
//                    this.error.errorMsg = "主机：" + list[i].hostip + " 已经存在";
//					return false;
//				}
    			if (infos.cameraip == list[i].cameraip  && infos.hostno != list[i].hostno) {
        			this.error.errorStatus = true;
        			this.error.setupalert = -1;
                    this.error.errorMsg = "摄像机：" + list[i].cameraip + " 已经存在";
					return false;
				}
    			if (infos.camaddrm == list[i].camaddrm  && infos.hostno != list[i].hostno) {
        			this.error.errorStatus = true;
        			this.error.setupalert = -1;
                    this.error.errorMsg = "主码流已经存在";
					return false;
				}
    			if (infos.camaddra == list[i].camaddra  && infos.hostno != list[i].hostno) {
        			this.error.errorStatus = true;
        			this.error.setupalert = -1;
                    this.error.errorMsg = "辅码流已经存在";
					return false;
				}
			}
    		return true;
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
        }
    }
});