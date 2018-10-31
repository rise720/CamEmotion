/**
 * Created by houpp on 2018/2/24.
 */

new Vue({
    el : '#loginApp',
    data : {
        error:'',
        userName: '',
        passWord: '',
        pwd:'',
    },
    mounted: function () {
        var mySwiper = new Swiper ('#face-popup', {
            onInit: function(swiper){ //Swiper2.x的初始化是onFirstInit
                swiperAnimateCache(swiper); //隐藏动画元素
                swiperAnimate(swiper); //初始化完成开始动画
            },
            onSlideChangeEnd: function(swiper){
                swiperAnimate(swiper); //每个slide切换结束时也运行当前slide动画
            },
            onSetTransition: function(swiper) {
                swiper.disableTouchControl();
            }
        });
    },
    watch: {

    },
    methods: {
        /**
         * 方法说明:获取用户登录信息
         */
        getUserLoginInfo: function () {
            var that = this;
            console.log(that.userName + '' + that.passWord);
            if (that.userName == null || that.userName == ''){
            	that.error = "请输入账号";
                return false;
            }            	
            if (that.passWord == null || that.passWord == ''){
            	that.error = "请输入密码";
                return false;
            }            	
            try {
            	requsestSer.post('../'+camEmotion.getUserInfo, {'useraccount':that.userName,'cpassword':that.passWord}).then(function (data) {
                    if (data.meta.success){
                        sessionStorage.setItem('createUser',that.userName);
                        that.pwd = that.passWord;
                        sessionStorage.setItem('pwd',that.pwd);
                        sessionStorage.setItem('loginDate',new Date());                        

                        if(data.data.cright.length == 0 || data.data.cright == 0){
                            that.error = "您没有权限请联系管理员";
                            return false;
                        }
                        sessionStorage.setItem('cright',data.data.cright);
                        
                        var url='';
                        switch (parseInt(data.data.cright[0])) {
	                        case 1:
	                        	url = '#/ClassTable';
	                            break;
	                        case 2:
	                        	url = '#/OpenClass';
	                            break;
	                        case 3:
	                        	url = '#/Contrast';
	                            break;
	                        case 4:
	                        	url = '#/AllSelect';
	                            break;
	                        case 5:
	                        	url = '#/Setup';
	                            break;
	                        case 6:
	                        	url = '#/Teacherlist';
	                            break;
	                        case 7:
	                        	url = '#/school';
	                            break;
	                        case 8:
	                        	url = '#/Users';
	                            break;
                        }
                        window.location.href='../index.html' + url;
                    }
                    else{
                        that.error = "账号密码错误";
                    }
                }, function(response) {
                	that.error = "参数错误";
				});
            } catch (e) {
                alert(e.message);
            }
        },
    }
})