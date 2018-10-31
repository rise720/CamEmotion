/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
    el:'#AllSelectApp',
    data:{
    	index: -1,
        all: 1,       // 总页数
        cur: 1,        // 当前页码
        pageModel: {
            currentPage:'1',
            pageRecorders:'6',
            rule:'DESC',
			expression:'id',
            context:''
        },
        comprehensiveReportList: [],
        teacherInfo: {}
    },
    mounted:function(){
    	common.checkUrl();
    	initAlert();
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
    filters:{
    	//格式化时间
    	formatTime: function(value){ 
    		return (value && value.length > 16 )? value.slice(0,16) : value;
    	}
    },
    methods:{
    	/**
		 * @点击查询按钮
		 */
        QueryList: function() {
            this.pageModel.currentPage = 1;  
            this.GetList();

        },
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
          
            var params = {'currentPage':this.pageModel.currentPage,
            			  'pageRecorders':this.pageModel.pageRecorders,
            			  'rule':this.pageModel.rule,
            			  'expression':this.pageModel.expression,
            		      'context':this.pageModel.context};
            try {
            	requsestSer.post(camEmotion.findComprehensiveReportList, params).then(function (data) {
                  
                    if (data.meta.success) {
                    	that.comprehensiveReportList = data.data.objList;
                    	that.all = data.data.totalPages;
                    	that.cur = data.data.currentPage;
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
		 * 查询单个教师信息
		 */
        GetTeacherInfo:function(infos){
          var that = this;
    	  var params = {'id':infos.teacherid};
	      try {
	      	requsestSer.post(camEmotion.findTeacherInfo, params).then(function (data) {
	              if (data.meta.success) {
	            	  that.teacherInfo= {};
	            	  that.teacherInfo = data.data;
	                  $('#tearModal').modal('show');
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
		 * 排序
		 */
        clickTitle:function(key){
			var that = this;
			// 校验二次点击
			if(that.pageModel.expression == key){
				// 二次点击的正序倒叙
				if(that.pageModel.rule == 'ASC'){
					that.pageModel.rule = 'DESC';
				}else{
					that.pageModel.rule = 'ASC';
				}
			}else{
				that.pageModel.expression = key;
			}
			that.GetList();
		},
		/**
		 * 导出csv文件
		 */
		ExportCsv:function(){
      	    try {
			      	window.open(camEmotion.ExportCsv + "?rule="
					+ this.pageModel.rule + "&expression="
					+ this.pageModel.expression + "&context="
					+ this.pageModel.context);
    	      } catch (e) {
             	   bootAlert.alertDanger(e.message);
    	      }
		}
    }
});