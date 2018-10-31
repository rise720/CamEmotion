/** 
 * 
 * @文件名称：zhangfei 
 * @类描述：js工具类 
 * @创建人：zhangfei 
 * @创建时间：2016年12月7日  
 */  
var DateUtil = {
    /**
     * @转换自定义日期格式
     * @param dateTime 传入时间的字符串
     * @param format	要转换的格式
     */
    DateTimeConvertToDateTime:function(dateTime,format){
    	var exp = new Date(dateTime);
     	return exp.format(format);
    },
    /** 
     * @描述：获取最近几天（开始时间和结束时间值,时间往前推算） 
     * @创建人：zhangfei 
     * @创建时间：2016年12月7日 
     */  
    getRecentDaysDateTime : function(day) {  
        var daymsTime = day * 24 * 60 * 60 * 1000;  
        var yesterDatsmsTime = this.getCurrentMsTime() - daymsTime;  
        var startTime = this.longMsTimeConvertToDateTime(yesterDatsmsTime);  
        var pastDate = this.formatterDate2(new Date(startTime));  
        var nowDate = this.formatterDate2(new Date());  
        var obj = {  
            startTime : pastDate,  
            endTime : nowDate  
        };  
        return obj;  
    },  
      
    /** 
     * @描述：获取今天（开始时间和结束时间值） 
     * @创建人：zhangfei 
     * @创建时间：2016年12月7日 
     */  
    getTodayDateTime : function() {  
        var daymsTime = 24 * 60 * 60 * 1000;  
        var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;  
        var currentTime = this.longMsTimeConvertToDateTime(this.getCurrentMsTime());  
        var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);  
        var nowDate = this.formatterDate2(new Date(currentTime));  
        var tomorrowDate = this.formatterDate2(new Date(termorrowTime));  
        var obj = {  
            startTime : nowDate,  
            endTime : tomorrowDate  
        };  
        return obj;  
    },  
      
    /** 
     * @描述：获取明天（开始时间和结束时间值） 
     * @创建人：zhangfei 
     * @创建时间：2016年12月7日 
     */  
    getTomorrowDateTime : function() {  
        var daymsTime = 24 * 60 * 60 * 1000;  
        var tomorrowDatsmsTime = this.getCurrentMsTime() + daymsTime;  
        var termorrowTime = this.longMsTimeConvertToDateTime(tomorrowDatsmsTime);  
        var theDayAfterTomorrowDatsmsTime = this.getCurrentMsTime()+ (2 * daymsTime);  
        var theDayAfterTomorrowTime = this.longMsTimeConvertToDateTime(theDayAfterTomorrowDatsmsTime);  
        var pastDate = this.formatterDate2(new Date(termorrowTime));  
        var nowDate = this.formatterDate2(new Date(theDayAfterTomorrowTime));  
        var obj = {  
            startTime : pastDate,  
            endTime : nowDate  
        };  
        return obj;  
    },
	/** 
	 * @描述：时间比较（结束时间大于开始时间） 
	 * @创建人：zhangfei 
	 * @创建时间：2016年12月7日 
	 */  
	compareDateTime : function(startTime, endTime) {
		/*var endTimeExp=endTime.replace(/-/g, "/")
		var startTimeExp=startTime.replace(/-/g, "/") 
		return ((new Date((endTimeExp.substr(0,10)+"T"+endTimeExp.substr(11,8)))) > (new Date((startTimeExp.substr(0,10)+"T"+startTimeExp.substr(11,8)))));*/
		return ((new Date(endTime.replace(/-/g, "/"))) > (new Date(startTime.replace(/-/g, "/"))));  
	},
	/**
     * 时间操作  两个时间字符串对比获得相差天数
     * startTime必须大于endTime
     * @param startTime 时间1字符串
     * @param endTime 时间2字符串
     * @returns  相差天数
     */
	compareDateDay: function (startTime, endTime) {
		var startTimeExp,endTimeExp;
		if(startTime.length>10){
	    	startTimeExp =new Date(startTime.substr(0,10)+"T"+startTime.substr(11,8));
	    	startTimeExp=new Date(startTimeExp.setHours(startTimeExp.getHours()-8));			
		}else{
			startTimeExp=new Date(startTime);
		}
		
		if(endTime.length>10){
			endTimeExp =new Date(endTime.substr(0,10)+"T"+endTime.substr(11,8));
	    	endTimeExp=new Date(endTimeExp.setHours(endTimeExp.getHours()-8));			
		}else{
			endTimeExp=new Date(endTime);
		}
        return parseInt((endTimeExp.getTime() - startTimeExp.getTime()) / (24 * 60 * 60 * 1000));
    },
    /**
     * @ 时间操作 加减天
     * @param type 时间的加减参数 1 加 0 减
     * @param dateTime 传入时间
     * @param dateType 操作类型 1年 2月 3 天 4 小时 5 分钟
     * @param amount 操作数
     * @param format 返回格式 yyyy-MM-dd HH:mm:ss
     * @returns 如果参数传入有误则返回原时间
     */
    editDateTime: function (type,dateTime,dateType,amount,format) {
        var exp ;
        if(dateTime.length>10){
            exp =new Date(dateTime.replace(/-/g, "/"));
        }else{
            exp =new Date(dateTime);
        }
        if(type == 1){
            switch (dateType) {
                case 1:
                    exp.setFullYear(exp.getFullYear() + amount);
                    break;
                case 2:
                    exp.setMonth(exp.getMonth() + amount);
                    break;
                case 3:
                    exp.setDate(exp.getDate() + amount);
                    break;
                case 4:
                    exp.setHours(exp.getHours() + amount);
                    break;
                default:
                    return dateTime;
            }
        }else if(type == 0){
            switch (dateType) {
                case 1:
                    exp.setFullYear(exp.getFullYear() - amount);
                    break;
                case 2:
                    exp.setMonth(exp.getMonth() - amount);
                    break;
                case 3:
                    exp.setDate(exp.getDate() - amount);
                    break;
                case 4:
                    exp.setHours(exp.getHours() - amount);
                    break;
                default:
                    return dateTime;
            }
        }

        return exp.Format(format);
    },
    /**
     * @描述 根据传入时间在基础上增加1个小时 如果非整点 在加一个小时
     * @param dateTime
     * @returns yyyy-MM-dd hh:00
     */
    editFormatDateTime: function (dateTime) {
        //var exp = new Date(dateTime);
    	var exp =new Date(dateTime.substr(0,10)+"T"+dateTime.substr(11,8));
        if(exp.getMinutes()>0){
           exp.setHours(exp.getHours()+2-8);
        }else{
        	exp.setHours(exp.getHours()+1-8);
        }
        return exp.format("yyyy-MM-dd hh:00");
    },
    
    /**
     * @ 根据日期计算年龄
     * @param dateofbirth
     *            生日
     * @returns {Number} 年龄
     */
    Ages: function (strBirthday) {
        var returnAge;
        var strBirthdayArr = strBirthday.split("-");
        var birthYear = Number(strBirthdayArr[0]);
        var birthMonth = Number(strBirthdayArr[1]);
        var birthDay = Number(strBirthdayArr[2]);

        var d = new Date();
        var nowYear = d.getFullYear();
        var nowMonth = d.getMonth() + 1;
        var nowDay = d.getDate();

        if (nowYear == birthYear) {
            returnAge = 0;//同年 则为0岁
        }
        else {
            var ageDiff = nowYear - birthYear; //年之差
            if (ageDiff > 0) {
                if (nowMonth == birthMonth) {
                    var dayDiff = nowDay - birthDay;//日之差
                    if (dayDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                }
                else {
                    var monthDiff = nowMonth - birthMonth;//月之差
                    if (monthDiff < 0) {
                        returnAge = ageDiff - 1;
                    } else {
                        returnAge = ageDiff;
                    }
                }
            } else {
                returnAge = -1;//返回-1 表示出生日期输入错误 晚于今天
            }
        }
        return returnAge;//返回周岁年龄
    },
};

Date.prototype.format = function(format) {
    var date = {
           "M+": this.getMonth() + 1,
           "d+": this.getDate(),
           "h+": this.getHours(),
           "m+": this.getMinutes(),
           "s+": this.getSeconds(),
           "q+": Math.floor((this.getMonth() + 3) / 3),
           "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
           format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
           if (new RegExp("(" + k + ")").test(format)) {
                  format = format.replace(RegExp.$1, RegExp.$1.length == 1
                         ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
           }
    }
    return format;
}
