
var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
var common = {

    /****************************基于bootstrap的弹出框********************************/
    /**
     * 基于bootstrap的弹出框
     * @param title 标题
     * @param text 文本
     */
    bootstrapAlert: function (title, text) {
        $("#cac_modal_title").text(title);
        $('#cac_modal_text').text(text);
        $('#myModal').modal('show');
    },

    /**
     * 获取url中参数值,会对uri编码的数据进行解码
     * @param name 参数名
     * @returns
     * houpp 2016/7/20
     */
    getQueryString : function(name) {
		// 构造一个含有目标参数的正则表达式对象
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return decodeURI(r[2]);
		return null;
	},
    /**
     * Ajax请求的共通方法
     * @param url 地址
     * @param data 参数集
     * @param callback 成功的回调函数
     * houpp 2016/7/20
     */
    loadAjax: function (url, data, callback, error) {
        $.ajax({
            type: 'post',
            url: url,
            dataType: 'json',
            data: data,
            timeout: 30000,
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: callback,
            error: error,
        });
    },

    /**
     * Ajax
     * @param ajaxurl 请求的地址
     * @param parameter 请求的参数
     * @returns {*}
     */
    cacAjax: function (type, ajaxurl, parameter) {
        var deferred = $.Deferred();
        $.ajax({
            type: type,
            url: ajaxurl,
            dataType: 'json',
            data: parameter,
            timeout: 180000,
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function (response) {
                deferred.resolve(response);
            },
            error: function (response) {
                deferred.reject(response);
            }
        });
        return deferred.promise();
    },

    /**
     * 点击跳链接
     * @param e 元素
     * @param url 路径
     * houpp 2016/7/20
     */
    clicklink: function (e, url) {
        $(e).click(function () {
            window.location.href = url;
        });
    },

    /**
     * 非微信浏览器禁用
     * houpp 2016/7/20
     */
    webchat: function () {
        var useragent = navigator.userAgent;
        if (useragent.match(/MicroMessenger/i) != 'MicroMessenger') {
            alert('已禁止本次访问：您必须使用微信内置浏览器访问本页面！');
            var opened = window.open('about:blank', '_self');
            opened.opener = null;
            opened.close();
        }
    },

    /**
     * 描述：响应式
     * houpp 2016/7/20
     */
    fontSize: function () {
        initpage();
        $(window).resize(function () {
            initpage();
        })

        function initpage() {
            var view_width = document.getElementsByTagName('html')[0].getBoundingClientRect().width;
            var _html = document.getElementsByTagName('html')[0];
            view_width > 640 ? _html.style.fontSize = 640 / 16 + 'px' : _html.style.fontSize = view_width / 16 + 'px';
        }
    },

    /**
     * 描述：千分位
     * @param {Object} s
     * @param {Object} n 保留几位小数 默认是2位
     */
    fmoney: function (num, n) {
        num = parseFloat(num).toFixed(1);
        return num && (num
                .toString().indexOf('.') != -1 ? num.toString().replace(/(\d)(?=(\d{3})+\.)/g, function ($0, $1) {
                return $1 + ",";
            }) : num.toString().replace(/(\d)(?=(\d{3}))/g, function ($0, $1) {
                return $1 + ",";
            }));
    },

    /**
     * 描述：显示弹出等待提示窗体(doing)    **** 调用时要基于 zepto.alert.js  css****
     * @param {Object} 提示消息
     */
    showMessageDialogWithoutTimeout: function (message) {
        return jQuery.dialog({
            content: message,
            title: ''
        });
    },

    /**
     * 描述：显示弹出警告提示窗体(doing)     **** 调用时要基于 zepto.alert.js  css****
     * @param {Object} 提示消息
     */
    showValidationErrorDialog: function (message) {
        return jQuery.dialog({
            content: message,
            title: 'alert',
            lock: false,
            time: 3000
        });
    },

    /**
     * 描述：显示弹出完成提示窗体(doing)     **** 调用时要基于 zepto.alert.js  css****
     * @param {Object} 提示消息
     */
    showValidationOKDialog: function (message) {
        return jQuery.dialog({
            content: message,
            title: 'ok',
            lock: false,
            time: 3000
        });
    },

    /**
     * 描述：根据身份证获取年月日
     * @param {Object} 身份号
     */
    getIdCardBirthday: function (idCard) {
        var len = idCard.length;
        if (len == '15') {
            var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/;
            var arr_data = idCard.match(re_fifteen);
            var year = arr_data[2];
            var month = arr_data[3];
            //		var day = parseInt(arr_data[4]);
            var day = arr_data[4];
            year = '19' + year;
            return {
                "year": year,
                "month": month,
                "day": day
            };
        }
        if (len == '18') {
            var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
            var arr_data = idCard.match(re_eighteen);
            var year = arr_data[2];
            var month = arr_data[3];
            var day = arr_data[4];
            return {
                "year": year,
                "month": month,
                "day": day
            };
        }
        return null;
    },

    /**
     * 描述：输入省份证获取性别
     * @param {Object} 身份证号
     * 1：男
     * 2：女
     */
    getIdSex: function (idCard) {
        if (idCard.length == 18) {
            sexno = idCard.substring(16, 17)
        } else if (idCard.length == 15) {
            sexno = idCard.substring(14, 15)
        } else {
            return "";
        }
        var tempid = sexno % 2;

        var sex = '1';
        if (tempid == 0) {
            sex = '2';
        } else {
            sex = '1';
        }
        return sex;
    },

    /**
     * @获取年龄
     * @param 日期字符串
     * @returns {boolean}
     */
    getAges: function (dateofbirth) {
        var r = dateofbirth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
        var d = new Date(r[1], r[3] - 1, r[4]);
        if (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
            && d.getDate() == r[4]) {
            var Y = new Date().getFullYear();
            return Y - r[1];
        }
    },

    /**
     * @校验手机
     * @param value
     * @returns {boolean}
     */
    isMobile: function (value) {
        var isMob = /^((\+?86)|(\(\+86\)))?(13[0123456789][0-9]{8}|15[0123456789][0-9]{8}|18[02356789][0-9]{8}|147[0-9]{8}|1349[0-9]{7})$/;
        if (isMob.test(value)) {
            return true;
        } else {
            return false;
        }
    },
    /**
     * @校验邮箱
     * @param value
     * @returns {boolean}
     */
    isEmail: function (value) {
        var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
        if (!pattern.test(value)) {
            return false;
        }
        return true;
    },

    /**
     * 数
     * @param text
     * @returns {string|void|XML}
     */
    getNum: function (text) {
        var value = text.replace(/[^0-9]/ig, '');
        return value;
    },

    /**
     * 生日
     * @param value 身份证号
     * @returns {string}
     */
    getBirthFromId: function (value) {
        var birth = value.substring(6, 10) + "-" + value.substring(10, 12) + "-" + value.substring(12, 14);
        return birth;
    },

    /**
     * 性别
     * @param value 身份证号
     * @returns {string}
     */
    getGenderFromId: function (value) {
        var gender = value.slice(14, 17) % 2 ? "1" : "2";
        return gender;
    },

    /**
     * 获取短信的动作
     * @param dom
     * @constructor
     */
    GetShortmessage: function (dom) {
        if (sessionStorage.phoneNumber == undefined) {
            common.showValidationErrorDialog("未获取您的手机号");
            return;
        }
        var s = 59;
        var zp = $(dom);
        zp.attr("disabled", true);
        SendMessagesFun.sendTextMessages();
        time = setInterval(function () {
            if (s == 0) {
                zp.text("获取验证码");
                zp.removeAttr("disabled");
                s = 59;
                clearInterval(time);
            } else {
                zp.html(s + "秒重新获取");
            }
            s--;
        }, 1000);
    },

    /**
     * 建立一個可存取到該file的url
     * @param file
     * @returns {*}
     */
    getObjectURL: function (file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    },

    /**
     * 改变字符串（6666 **** **** 6666）
     * @param string 支付串
     * @returns {string}
     */
    bankstring: function (string) {
        var text = string.substr(0, 4) + " **** **** " + string.substr(string.length - 4);
        return text;
    },

    /**
     * 改变字符串（131 ****  6666）
     * @param string 支付串
     * @returns {string}
     */
    telstrimg: function (string) {
        var text = string.substr(0, 3) + "****" + string.substr(string.length - 4);
        return text;
    },

    /**
     * 时间操作  两个时间字符串对比获得相差分钟
     * date1必须大于date2
     * @param date1 时间1字符串
     * @param date2 时间2字符串
     * @returns  相差分钟
     */
    ContrastMinute: function (date1, date2) {
        var exp1 = new Date(date1);
        var exp2 = new Date(date2);
        return parseInt((exp1.getTime() - exp2.getTime()) / (60 * 1000));
    },

    /**
     * 时间操作  两个时间字符串对比获得相差小时
     * date1必须大于date2
     * @param date1 时间1字符串
     * @param date2 时间2字符串
     * @returns  相差小时
     */
    ContrastHour: function (date1, date2) {
        var exp1 = new Date(date1);
        var exp2 = new Date(date2);
        return parseInt((exp1.getTime() - exp2.getTime()) / (60 * 60 * 1000));
    },

    /**
     * 时间操作  两个时间字符串对比获得相差天数
     * date1必须大于date2
     * @param date1 时间1字符串
     * @param date2 时间2字符串
     * @returns  相差小时
     */
    ContrastDay: function (date1, date2) {
        var exp1 = new Date(date1);
        var exp2 = new Date(date2);
        return parseInt((exp1.getTime() - exp2.getTime()) / (24 * 60 * 60 * 1000));
    },

    /**
     * 使用canvas对大图片进行压缩
     * @type 图片对象
     */
    compress: function (img) {
        //    用于压缩图片的canvas
        var canvas = document.createElement("canvas");
        var ctx = canvas.getContext('2d');
        //    瓦片canvas
        var tCanvas = document.createElement("canvas");
        var tctx = tCanvas.getContext("2d");

        var initSize = img.src.length;
        var width = img.width;
        var height = img.height;
        //如果图片大于四百万像素，计算压缩比并将大小压至400万以下
        var ratio;
        if ((ratio = width * height / 4000000) > 1) {
            ratio = Math.sqrt(ratio);
            width /= ratio;
            height /= ratio;
        } else {
            ratio = 1;
        }
        canvas.width = width;
        canvas.height = height;
        // 铺底色
        ctx.fillStyle = "#fff";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        //如果图片像素大于100万则使用瓦片绘制
        var count;
        if ((count = width * height / 1000000) > 1) {
            count = ~~(Math.sqrt(count) + 1); //计算要分成多少块瓦片
            //计算每块瓦片的宽和高
            var nw = ~~(width / count);
            var nh = ~~(height / count);
            tCanvas.width = nw;
            tCanvas.height = nh;
            for (var i = 0; i < count; i++) {
                for (var j = 0; j < count; j++) {
                    tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio, nh * ratio, 0, 0, nw, nh);
                    ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
                }
            }
        } else {
            ctx.drawImage(img, 0, 0, width, height);
        }
        //进行最小压缩
        var ndata = canvas.toDataURL('image/jpeg', 0.7);

        tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
        return ndata;
    },

    /**
     * 获取blob对象的兼容性写法
     * @param buffer
     * @param format
     * @returns {*}
     */
    getBlob: function (buffer, format) {
        try {
            return new Blob(buffer, {type: format});
        } catch (e) {
            var bb = new (window.BlobBuilder || window.WebKitBlobBuilder || window.MSBlobBuilder);
            buffer.forEach(function (buf) {
                bb.append(buf);
            });
            return bb.getBlob(format);
        }
    },

    //    图片上传，将base64的图片转成二进制对象，塞进formdata上传
    getImgObject: function (basestr, type) {
        var text = window.atob(basestr.split(",")[1]);
        var buffer = new Uint8Array(text.length);
        var pecent = 0, loop = null;
        for (var i = 0; i < text.length; i++) {
            buffer[i] = text.charCodeAt(i);
        }
        var blob = common.getBlob([buffer], type);
        return blob;
    },
    /**
     * @除法函数，用来得到精确的除法结果
     * 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
     * 调用：accDiv(arg1,arg2)
     * @param arg1
     * @param arg2
     * @returns {string} arg1除以arg2的精确结果
     */
    accDiv: function (arg1, arg2) {
        var t1 = 0, t2 = 0, r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {
        }
        ;
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {
        }
        ;
        with (Math) {
            r1 = Number(arg1.toString().replace(".", ""));
            r2 = Number(arg2.toString().replace(".", ""));
            return (r1 / r2) * pow(10, t2 - t1);
        }
    },

    /**
     * @乘法函数，用来得到精确的乘法结果
     * 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
     * 调用：accMul(arg1,arg2)
     * @param arg1
     * @param arg2
     * @returns {string} arg1乘以arg2的精确结果
     */
    accMul: function (arg1, arg2) {
        var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {
        }
        ;
        try {
            m += s2.split(".")[1].length
        } catch (e) {
        }
        ;
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    },

    /**
     * @加法函数，加法函数，用来得到精确的加法结果
     * 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
     * 调用：accAdd(arg1,arg2)
     * @param arg1
     * @param arg2
     * @returns {string} arg1加上arg2的精确结果
     */
    accAdd: function (arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    },

    /**
     * @减法函数，用来得到精确的减法结果
     * 说明：javascript的减法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的减法结果。
     * 调用：accSubtr(arg1,arg2)
     * @param arg1
     * @param arg2
     * @returns {string} arg1减去arg2的精确结果
     */
    accSubtr: function (arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        //动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    },
    /**
     * 随机数
     * n 位数
     */
     generateMixed:function(n) {
        var res = "";
        for(var i = 0; i < n ; i ++) {
            var id = Math.ceil(Math.random()*35);
            res += chars[id];
        }
        return res;
   },
   /**
    * 当前时间
    */
   getNowFormatDate:function() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds();
	    return currentdate;
	},
   fluctuationRatio:function(datas){
	   	//标准差
		var s = 0;
		//波动率
		var Q = 0;
		var n = datas.length - 1;
		var t = datas.length;
		
		var s1 = 0;
		var s2 = 0;
		
		for (var i=1; i < datas.length ; i++) {
//			if((datas[i] / datas[i-1]) > 0){
				s1 += Math.pow((Math.log(datas[i] / datas[i-1])), 2);
				s2 += Math.log(datas[i] / datas[i-1]);
//			}
			
		}
		s1 = s1/(n-1);
		s2 = Math.pow(s2, 2)/(n*(n-1));
		
		s = Math.sqrt(s1 - s2);
		
		Q = (s / Math.sqrt(t)) * 100;
		
		return Q.toFixed(2);
   },
   //排序
   sortNumber:function(a,b){
	   return a - b;
   },
   variance:function(datas){
	   
	   var sumData = 0;
		for (var i=0; i < datas.length ; i++) {
			sumData += datas[i];
		}
		var av = sumData / datas.length;
		
		var sum2 = 0;
		for (var i=0; i < datas.length ; i++) {
			sum2 += Math.pow(datas[i] - av, 2);
		}
		return (sum2 / datas.length).toFixed(2);
   }
   /**
    * 校验字段
    */
   ,checkParams:function(paramname,param,length,type){
	   if(param){
		   param = param.trim();
		   if(type == 'number'){
			   
		   }else if(type == 'vachar'){
			   if(param.length > 0){
				   if(param.length > length){
					   return paramname + "最大为" + length + "个字符";
				   }else{
					   return true;
				   }
			   }else{
				   return paramname + "不能为空";
			   }
		   }
	   }else{
		   return paramname + "不能为空";
	   }
   }
   ,
   getNowFormatDate:function() {
	    var date = new Date();
	    var seperator1 = "-";
	    var seperator2 = ":";
	    var month = date.getMonth() + 1;
	    var strDate = date.getDate();
	    if (month >= 1 && month <= 9) {
	        month = "0" + month;
	    }
	    if (strDate >= 0 && strDate <= 9) {
	        strDate = "0" + strDate;
	    }
	    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
	            + " " + date.getHours() + seperator2 + date.getMinutes()
	            + seperator2 + date.getSeconds();
	    return currentdate;
	},
	popoverDiy:function(dom,placement,contents){
		$('.' + dom).popover('destroy');
		$('.' + dom).popover({   
			trigger:'manual',
			container: 'body',
            placement : placement,  
            html: 'true',   
            content : '<div id="content_'+ dom +'">'+ contents + '</div>',  //这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；  
            animation: true  
        });
		
		$('#content_' + dom).html(contents);
		
		$('.' + dom).popover('show');
		
		
//		setTimeout(function () {
//			$('.' + dom).popover("hide")  
//        }, 4000); 
	},
	popoverDiyUser:function(dom,placement,contents){
		$('.' + dom).popover('destroy');
//		$('.' + dom).popover('hide');
		
		$('.' + dom).popover({  
			trigger:'manual',
			container: 'body',
            placement : placement,  
            html: 'true',   
            content :  '<div id="content_'+ dom +'">'+ contents + '</div>',  //这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；  
            animation: true  
        });
		
		$('#content_' + dom).html(contents);
		
		$('.' + dom).popover('show');
		
//		setTimeout(function () {
//			$('.' + dom).popover("hide")  
//        }, 4000); 
		
		
		
	},
	initPopover:function(dom,placement,contents){
		$('.' + dom).popover({  
			trigger:'manual',
			container: 'body',
            placement : placement,  
            html: 'true',   
            content :  '<div id="content_'+ dom +'">'+ contents + '</div>',  //这里可以直接写字符串，也可以 是一个函数，该函数返回一个字符串；  
            animation: true  
        });
		
		$('.' + dom).popover('show');
		$('.' + dom).popover('hide');
	},
	isNumber:function(value) {
	    var patrn = /^(-)?\d+(\.\d+)?$/;
	    if (patrn.exec(value) == null || value == "") {
	        return false
	    } else {
	        return true
	    }
	},
	
	/**
	 * 适合当前业务
	 * 
	 */
	checkUrl:function(){
		var hash = location.hash;
		var url;
		var usercright = [];
		if(hash.indexOf('?')>-1){
			url = hash.split('?')[0];
		}else{
			url = hash;
		}
		
		console.log(sessionStorage.getItem('cright'));
		
		switch (url) {
	        case '#/ClassTable':
	            common.checkUrlCright(1);
	            break;
	        // case '#/ClassClassify':
	        //     common.checkUrlCright(2);
	        //     break;
	        case '#/OpenClass':
	            common.checkUrlCright(3);
	            break;
	        // case '#/ContrastTransverse':
	        //     common.checkUrlCright(4);
            //     break;
            case '#/LiveVideo':
                common.checkUrlCright(4);
                break;

	        case '#/ContrastLongitudinal':
	            common.checkUrlCright(5);
	            break;
	        case '#/AllSelect':
	            common.checkUrlCright(6);
	            break;	            
	        case '#/Setup':
	            common.checkUrlCright(7);
	            break;
	        case '#/Teacherlist':
	            common.checkUrlCright(8);
	            break;
	        case '#/school':
	            common.checkUrlCright(9);
	            break;
	        case '#/Users':
	            common.checkUrlCright(10);
	            break;
	        
	    }
		// 当前页面的锚点
		console.log(location.hash);
	},
	
	/**
	 * 检查用户权限
	 */
	checkUrlCright:function(hashIndex){
		var flay = true;
		var cright = (sessionStorage.getItem('cright')).split(',');
		for(var i = 0 ; i < cright.length ; i ++){
			if(cright[i] == hashIndex){
				flay = false;
			}
		}
		if(flay){
			window.location.href = "error/error.html";
		}
	}
};

/**
 * 针对视频的公共
 * @type {{
 * getVideoFirstFrame: commonVideo.getVideoFirstFrame
 * }}
 */


/**
 * @对Date的扩展，将 Date 转化为指定格式的String
 * @月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * @年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * @例子：
 * @(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * @(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
 * @param fmt
 * @returns {*}
 * @constructor
 */
Date.prototype.Format = function (fmt) {
    //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

Array.max = function( array ){ 
	return Math.max.apply( Math, array );
	};
Array.min = function( array ){ 
	return Math.min.apply( Math, array );
	};
Array.prototype.contains = function ( needle ) {
	  for (i in this) {
	    if (this[i] == needle) return true;
	  }
	  return false;
	};
Array.prototype.unique = function() {
	var a = this.concat();
	for(var i=0; i < a.length; i++){
		for(var j=i+1; j < a.length; j++){
			if(a[i].name === a[j].name){
				a.splice(j, 1);
			}
		}
	}
	return a;
};
	
$(function () {
    //common.webchat();
	 common.fontSize();
});

//检验页面缓存里的数据是否超时（2分钟）
var setwebdate = {
    data: '2'//缓存时间
}

/*
 * loading加载显示
 */
//function showLoading(){
//    $("#ycf-alert").modal("show");
//}
///*
// * loading加载关闭
// */
//function hideLoading(){
//    $("#ycf-alert").modal("hide");
//}







