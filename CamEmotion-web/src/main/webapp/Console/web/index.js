/**
 * Created by houpp on 2018/2/5.
 */
var indexVue = null;
indexVue = new Vue({
    el: '#wrapper',
    data: {
        mouseenter: 0, // 鼠标上移FLAY
        activeline: 1, // ACTIVE
        line: [],
        userInfo:{}
    },
    mounted: function () {
        var that = this;
        
        //$('#com-loading').modal('hide');
        //console.log(that.onMouseenter());
        //console.log(that.getlocation(location.hash));

        //console.log(indexVue);
        //console.log(that);
        $('.sidebar').height($(window).height()+'px');
        
        that.ajax();
        that.getUserLoginInfo();
        that.getlocation(window.location.href);
        
        //$('#com-loading').modal('hide');
    },
    methods: {
        onMouseenter: function () {
            return 11;
        },

        /**
         * 方法说明：页面刷新时获取 锚点名称
         * @param hash
         */
        getlocation: function (hash) {
            var that = this;
            var url = (function(hash){
                var str1 = hash.split('#/');
                if(str1[1]){
                    if (str1[1].indexOf('?') > -1) {
                        var str2 = str1[1].split('?');
                        return str2[0];
                    } else {
                        return str1[1];
                    }
                }
            })(hash);
            //console.log(url);
            switch (url) {
                case 'ClassTable':
                    that.activeline = 1;
                    break;
        
                case 'OpenClass':
                    that.activeline = 2;
                    break;
               
                case 'AllSelect':
                    that.activeline = 3;
                    break;
                case 'Setup':
                    that.activeline = 4;
                    break;
                case 'Teacherlist':
                    that.activeline = 5;
                    break;
                case 'school':
                    that.activeline = 6;
                    break;
                case 'Users':
                    that.activeline = 7;
                    break;
                 case 'LiveVideo':
                    that.activeline = 8;
                    break;    
                case  'rankings':
                    that.activeline = 9   
            }
        },
        
        closeAlert:function(){
        	initAlert();
        },

        ajax:function(){
            requsestSer.post('',{"data":11}).then(function(){

            },function(){

            });
        },
        /**
         * 方法说明：清除登录信息 
         */
        clearUserLoginInfo:function(){
        	sessionStorage.removeItem('createUser');
        	sessionStorage.removeItem('pwd');
        	sessionStorage.removeItem('loginDate');
        	sessionStorage.removeItem('cright');
        },
        /**
		 * 方法说明:获取登录用户信息
		 */
        getUserLoginInfo: function () {
        	var that = this;
        	var username = sessionStorage.getItem('createUser');  
            var passwd = sessionStorage.getItem('pwd');                          
            var loginDate = sessionStorage.getItem('loginDate');
        	
            if (username == null || username == ''){
            	window.location.href='login/login.html';
                return false;
            }            	
            if (passwd == null || passwd == ''){
            	window.location.href='login/login.html';
                return false;
            }  
            
            var params = {'useraccount':username, 'cpassword':passwd};
            try {
            	requsestSer.post(camEmotion.getUserInfo, params).then(function (data) {
                    if (data.meta.success){
                        if(data.data.cright.length == 0 || data.data.cright == 0){
                        	bootAlert.alertDanger('您没有权限请联系管理员');
                        	//跳转到登录页面
                        	window.location.href='login/login.html';
                        	return false;
                        }
                        that.userInfo = data.data;
                
                        that.handleMenu(data.data.cright);
                    }
                    else{
                    	bootAlert.alertDanger('获取当前用户错误');
                    }
                }, function(response) {
                	bootAlert.alertDanger('获取当前用户错误');
					deferred.reject();
				});
            } catch (e) {
                alert(e.message);
            }
        },
        handleMenu:function(cright){
        	var that = this;
            var menus = [];
        
        	 if(cright.length == 1){
        		 menus[0] = cright;
             }else{
                 menus = cright.split(',');
             }
            
        	 menus.forEach(function(val){
               
        		 switch (parseInt(val)) {
                     
				case 1:
					that.line.push({
		                url: '#/ClassTable',
		                active: 1,
		                name: '报告管理',
		                icon: '<i class="iconfont iconfont-lg icon-chazhaobiaodanliebiao nar-icon-white"></i>'
		            });
					break;
				case 2:
					that.line.push({
		                url: '#/OpenClass',
		                active: 2,
		                name: '报告采集',
		                icon: '<i class="iconfont iconfont-lg icon-caijiqi nar-icon-white"></i>'
		            });
                    break;
              
                
				case 3:
					that.line.push({
		                url: '#/AllSelect',
		                active: 3,
		                name: '综合查询',
		                icon: '<i class="iconfont iconfont-lg icon-zonghechaxun nar-icon-white"></i>'
		            });
					break;
				case 4:
					that.line.push({
		                url: '#/Setup',
		                active: 4,
		                name: '设备管理',
		                icon: '<i class="iconfont iconfont-lg icon-shezhi nar-icon-white"></i>'
		            });
                    break;
                case 5:
                that.line.push({
                    
                    url: '#/Teacherlist',
                    active: 5,
                    name: '演讲人管理',
                    icon: '<i class="iconfont iconfont-lg icon-shezhicanyujiaoshi nar-icon-white"></i>'
                });
                break;

				case 6:
					that.line.push({
		                url: '#/school',
		                active: 6,
		                name: '报告厅管理',
		                icon: '<i class="iconfont iconfont-lg icon-xuexiaogaikuang nar-icon-white"></i>'
		            });
					break;
				case 7:
					that.line.push({
		                url: '#/Users',
		                active: 7,
		                name: '用户管理',
		                icon: '<i class="iconfont iconfont-lg icon-yonghu-tianchong nar-icon-white"></i>'
		            });
                    break;
                case 8:
                    that.line.push({
                       url: '#/LiveVideo',
                       active: 8,
                       name: '会场直播',
                       icon: '<i class="iconfont iconfont-lg icon-shipinzhibo nar-icon-white"></i>'
                });
                break;  
                case 9:
                    that.line.push({
                        url: '#/rankings',
                        active: 9,
                        name: '微表情排行榜',
                        icon: '<i class="iconfont iconfont-lg icon-huangguan4 nar-icon-white"></i>'
                });
                break;        
               
              

				default:
					break;
				}
             });
             
            
        }
    }
});
