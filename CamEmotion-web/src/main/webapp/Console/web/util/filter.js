Vue.filter('teacherSex', function (value) {
	var sexZh = '男';
    if (!value) return sexZh;
    if(value == 1)
    	sexZh = '女';
    return sexZh;
});
Vue.filter('education', function (value) {
	var educationZH = '高中以下';
    if (!value) return educationZH;
    switch (value) {
	case 1:
		educationZH = '高中以下';
		break;
	case 2:
		educationZH = '高中';
		break;
	case 3:
		educationZH = '中专';
		break;
	case 4:
		educationZH = '大专';
		break;
	case 5:
		educationZH = '本科';
		break;
	case 6:
		educationZH = '硕士';
		break;
	case 7:
		educationZH = '博士';
		break;
	default:
		break;
	}
    return educationZH;
});
Vue.filter('educationLevel', function (value) {
	var educationLevelZH = '小学';
    if (!value) return educationLevelZH;
    switch (value) {
	case 1:
		educationLevelZH = '小学';
		break;
	case 2:
		educationLevelZH = '初中';
		break;
	case 3:
		educationLevelZH = '高中';
		break;
	case 4:
		educationLevelZH = '中专';
		break;
	case 5:
		educationLevelZH = '大专';
		break;
	case 6:
		educationLevelZH = '本科';
		break;
	default:
		break;
	}
    return educationLevelZH;
});
Vue.filter('classNamedRule', function (value) {
	var classNamedRuleZH = '数字';
    if (!value) return educationLevelZH;
    switch (value) {
	case 1:
		classNamedRuleZH = '数字';
		break;
	case 2:
		classNamedRuleZH = '字母';
		break;
	default:
		break;
	}
    return classNamedRuleZH;
});

Vue.filter('schoolname', function (value) {
	if(!value){
		return "";
	}
	if(value.length > 20){
		value = value.substring(0, 20) + "...";
	}
    return value;
});

Vue.filter('headimgurl', function (value) {
	if(!value || value.length == 0){
		return "../../Console/images/headimg.png";
	}
    return value;
});

//时间格式化
Vue.filter('datetime', function (value) {
    if (!value) return '2018-01-01 00:00:00';
    return DateUtil.DateTimeConvertToDateTime(value, "yyyy-MM-dd hh:mm:ss");
});

//时间格式化
Vue.filter('datetime2', function (value) {
    if (!value) return '--:--';
    return DateUtil.DateTimeConvertToDateTime(value, "yyyy-MM-dd hh:mm:ss");
});

//时间格式化
Vue.filter('time', function (value) {
    if (!value) return '2018-01-01';
    return DateUtil.DateTimeConvertToDateTime(value, "yyyy-MM-dd");
});

/**
 * 根据日期计算年龄
 */
Vue.filter('getAges', function (value) {
    if (!value) return '0';
    return DateUtil.Ages(value)+'年';
});

Vue.filter('subject', function (value) {
	if (!value) return '';
    if(value.length>2){
        return value[0]+''+value[1]+'...';
    }
    return value;
});

/**
 * 多媒体类型转换器
 */
Vue.filter('fileType', function (value) {
	var educationZH = '图片';
    if (!value) return educationZH;
    switch (value) {
	case 1:
		educationZH = '音频';
		break;
	case 2:
		educationZH = '视频';
		break;
	default:
		break;
	}
    return educationZH;
});

/**
 * 获取路径最后地址或名称
 */
Vue.filter('filterAddr', function (value) {
	if (value == null || value.length <= 0) {
		return "";
	}
	var arr = value.split('\\');
	if (arr.length <= 1) {
		arr = value.split('/');
	}
	return arr[arr.length-1];
});

/**
 * @字符缩减
 */
Vue.filter('subStr', function (value) {
	if(value && value.length > 8){
		value = value.substring(0, 8) + '...';
	}
    return value;
});

Vue.filter('subStrFilter', function (value,length) {
	if(value && value.length > length){
		value = value.substring(0, length) + '...';
	}
    return value;
});
/**
 * 课程状态
 */
/**
 * 课程状态
 */
Vue.filter('classStatus', function (value) {
	 if (value == 1 || value == "1") return "演讲结束";
	 else if(value == 2 || value == "2")return "待演讲";
	 else if (value == 0 || value == "0")return "演讲中";
});

