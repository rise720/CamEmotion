/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
    el:'#classvideoApp',
    data:{
        chart:true,
    	curriculumId:0,			//传入参数课程id
    	//video_path:"/opencv_video/",
    	bubbleXMap:[],
    	bubblePlayMap:[],
    	videoData:[],
    	videoData2:[],
    	videoTblList:[],		//curriculum_video_tbl对应数据列表
    	videoPlayList:[],		//videoTblList对应的视频对象
    	videoTblList2:[],		//curriculum_video_tbl对应数据列表
    	videoPlayList2:[],		//videoTblList对应的视频对象
    	curriculumInfo:{},		//课堂信息
    	studentInfo:{},			//学生信息
    	chartList:{
            valenceBubbleList: "",
            valencePieList: "",
			participationList:"",
            valenceAverageList:"",
			tActionRateList:"",
			emotionCHList:"",
            attentionBubbleList: "",
            attentionPieList: "",
            tiredLineList: "",
            tiredPieList: "",
            
            valenceLineList: "",
            valenceLineList2: "",
            faceCountList: "",
            sourceBubbleList: "",
            faceCountPieList: "",
            
            joyLineList: "",
            sadnessLineList: "",
            disgustLineList: "",
            contemptLineList: "",
            angerLineList: "",
            surpriseLineList: "",
            fearLineList: "",
            browFurrowLineList: "",
            neutralLineList: ""
    	},
    	/**
    	 * ----------优质课----------------
    	 */
    	emojiConst:{						//图标显示用常量字段
    		emojiValence:["思考", "倾听", "活跃"],
    		emojiValenceLine:["互动频度"],
	    	emojiSceneLine:["课堂情景"],
	    	emojiScenePie:["听课","练习"],
	    	emojiAttentionLine:["注意力"],
            emojiAttentionPie:["分散","集中"],
	    	emojiTired:["不疲劳","疲劳"],
	    	emojiJoyLine:["喜悦"],
	    	emojiSadnessLine:["悲伤"],
	    	emojiDisgustLine:["困惑"],
	    	emojiContemptLine:["蔑视"],
	    	emojiAngerLine:["愤怒"],
	    	emojiSurpriseLine:["惊讶"],
	    	emojiFearLine:["恐惧"],
	    	emojiBrowFurrowLine:["皱眉"],
	    	emojiNeutralLine:["无表情"],
	    	emojiColor:{
	    			valence:"#4DA74D", //一般
	    		    joy:"#0B62A4",		//消极
	    		    contempt:"#CB4B4B"	//积极
	    		},
	    	basicChartColor:['#D48265','#61A0A8','#2F4554','#91C7AE']
    	},
        comprehensiveReportList: [],
    	totalPie:0,
    	interval:null,
    	shixutop:0
    },
    watch:{
    	chart:function(oldval,newval){
    		console.log('old'+$('.maxbox').width());
    		console.log('宽'+$('.maxbox').width()/2+'px');
    		console.log('高'+$('.maxbox').width()/2*0.68+'px');
    		$('.maxvideo').width($('.maxbox').width()/2-15+'px').height($('.maxbox').width()/2*0.80+'px');
    	}
    },
    mounted:function(){
    	common.checkUrl();
        console.log('------------------/classvideoApp');
        var that = this;
        $('#page-wrapper').css('min-height', $(window).height() - 100 + 'px');
        $('.video-box').height($(window).height() - 170 + 'px');
        $('.cac-chart').height($(window).height() - 140 + 'px');
        
        //清理interval
        if (this.interval) {
            clearInterval(this.interval);
        }
        $('#com-loading').modal('show');
        var par = vipspa.parse();
        that.curriculumId = par.param.curriculumId;
        that.studentId = par.param.studentId;
        
      //获取当前课堂的基本信息  获取视频列表 获取图表列表
        $.when(that.findCurriculum(that.curriculumId), that.getVideoList(that.curriculumId),
        		that.getChartList(that.curriculumId,that.studentId)).done(function() {
        			$('#com-loading').modal('hide');
        			that.handleVideo(that.videoData);
        			that.handleVideo2(that.videoData2);
		});
       
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
    updated:function(){
    	let that = this;
		if(that.videoTblList && that.videoTblList.length > 0){
			that.videoTblList.forEach(function(video,index){
				var myPlay = videojs(video.id , {
					"controls": true,
					"autoplay": false,
					"preload": "auto",
					"loop": false,
					controlBar: {
					captionsButton: false,
					chaptersButton: false,
					playbackRateMenuButton: true,
					LiveDisplay: true,
					subtitlesButton: false,
					remainingTimeDisplay: true,
					
					progressControl: true,

					volumeMenuButton: {
					inline: false,
					vertical: true
					},//竖着的音量条
					fullscreenToggle: true
					}
				}, function () {
					// 捕获错误异常
//					this.on('error',function(rel){
//						bootAlert.alertDanger("视频播放错误");       
//					});
					
					// 捕获错误异常
                	this.on('error',function(rel){
                		console.log(rel.target.firstElementChild.error.code);// 错误id         
                		var errCode = rel.target.firstElementChild.error.code
                		if(errCode >= 1 && errCode <= 5){
                			console.log(rel.target.innerHTML = that.videoErrorInfo());// 错误信息
                		}  
                	});
				});
				if(index != 0){
					myPlay.volume(0);
				}
				that.videoPlayList.push(myPlay);
			});
		}
		
		if(that.videoTblList2 && that.videoTblList2.length > 0){
			that.videoTblList2.forEach(function(video,index){
				var myPlay = videojs(video.id , {
					"controls": true,
					"autoplay": false,
					"preload": "auto",
					"loop": false,
					controlBar: {
					captionsButton: false,
					chaptersButton: false,
					playbackRateMenuButton: true,
					LiveDisplay: true,
					subtitlesButton: false,
					remainingTimeDisplay: true,
					
					progressControl: true,

					volumeMenuButton: {
					inline: false,
					vertical: true
					},//竖着的音量条
					fullscreenToggle: true
					}
				}, function () {
//					// 捕获错误异常
//					this.on('error',function(rel){
//						bootAlert.alertDanger("视频播放错误");       
//					});
					
					// 捕获错误异常
                	this.on('error',function(rel){
                		console.log(rel.target.firstElementChild.error.code);// 错误id         
                		var errCode = rel.target.firstElementChild.error.code
                		if(errCode >= 1 && errCode <= 5){
                			console.log(rel.target.innerHTML = that.videoErrorInfo());// 错误信息
                		}  
                	});
				});
//				if(index != 0){
					myPlay.volume(0);
//				}
				that.videoPlayList2.push(myPlay);
			});
		}
    },
    filters:{
    	//格式化时间
    	formatTime: function(value){ 
    		return (value && value.length > 16 )? value.slice(0,16) : value;
    	}
    },
    methods:{
		start:function(){
			this.chart = !this.chart;
		},
    	/**
    	 * 获取当前课堂的基本信息
    	 *
    	 * @param durtion
    	 */
    	findCurriculum:function(id){
    		let deferred = $.Deferred();
    		var that = this;
    		var params = {'id':id};
    	    try {
    	    	requsestSer.post(camEmotion.findCurriculumTbl + "?id=" + id, params).then(function (data) {
    	            if (data.meta.success) {
    	            	that.curriculumInfo = data.data;
    	            	deferred.resolve();
    	            } else {
    	            	$('#com-loading').modal('hide');
    	                bootAlert.alertDanger(data.meta.message);
    	                deferred.reject();
    	            }
    	        }.bind(this)).catch(function (response) {
    	        	$('#com-loading').modal('hide');
    	            bootAlert.alertDanger('请求错误');
    	            deferred.reject();
    	        }.bind(this));
    	    } catch (e) {
    	    	$('#com-loading').modal('hide');
    	        alert(e.message);
    	        deferred.reject();
    	    }
    	    return deferred.promise();
    	},
    	//获取播放视频的数据及对象列表
    	getVideoList: function(curriculumid){
    		let deferred = $.Deferred();
    		var that = this;
            var params = {"curriculumid": curriculumid};
            try {
            	requsestSer.post(camEmotion.lessonVideo, params).then(function (data) {
                    if (data.meta.success) {
                    	data.data.forEach(function(val){
                    		that.videoData.push({'id':val.id,'videoimgurl':val.videoimgurl});
                    		that.videoData2.push({'id':val.id,'videoimgurl':val.videoimgurl});
                    		
                    	});
                    	deferred.resolve();
                    } else {
                    	$('#com-loading').modal('hide');
                    	bootAlert.alertDanger(data.meta.message);
                    	deferred.reject();
                    }
                },function (response) {
                	$('#com-loading').modal('hide');
               	   bootAlert.alertDanger("视频列表请求错误");
               	deferred.reject();
                });
            } catch (e) {
            	$('#com-loading').modal('hide');
          	   bootAlert.alertDanger(e.message);
          	 deferred.reject();
            }
            return deferred.promise();
    	},
    	/**
    	 * 组装视频video实例
    	 */
    	handleVideo:function(data){
    		var that = this;
    		var dataV = data;
    		dataV.forEach(function(video) {
        		video.id = "my-video-" + video.id + "-" + common.generateMixed(4);
        		//video.videoimgurl = that.video_path + video.videoimgurl;
			});
    		that.videoTblList = dataV;
    		
    	},
    	handleVideo2:function(data){
    		var that = this;
    		var dataV = data.concat();
    		dataV.forEach(function(video) {
        		video.id = "my-video-" + video.id + "-" + common.generateMixed(4);
        		//video.videoimgurl = that.video_path + video.videoimgurl;
			});
    		
    		that.videoTblList2 = dataV;
    	},
        /**
         * 异步获取报表统计数据
         * @param VideoPer
         * @constructor
         */
        getChartList: function (curriculumid,studentid) {
        	let deferred = $.Deferred();
        	let that = this;
        	let params = {"curriculumid": curriculumid,"studentid":studentid};	//参数无用
            try{
            	requsestSer.post(camEmotion.getStudentAnalysis + "?curriculumid=" + curriculumid + "&studentid=" + studentid, params).then(function (data) {
            		if (data.meta.success) {
                        //将数据赋值给报表data
						var data = data.data;
                        //积极度
                        that.chartList.valenceBubbleList = that.chgBubbleFormat(data.valenceBubbleList);
                        that.chartList.valencePieList = that.chgPieFormat(data.valencePieList, "valence");

                        //注意力
                        that.chartList.attentionBubbleList = that.chgLineFormat3(data.sourceBubbleList, "attention");
                        that.chartList.attentionPieList = that.chgPieFormat(data.attentionPieList, "attention");
                        
                        that.studentInfo = data.studentInfo;
                        
                        that.showChart();
                        deferred.resolve();
            		}else{
            			$('#com-loading').modal('hide');
            			bootAlert.alertDanger(data.meta.message);
            			deferred.reject();
            		}
            	},function (response) {
            		$('#com-loading').modal('hide');
            		bootAlert.alertDanger("图表列表请求错误");
            		deferred.reject();
            	}); 	
            }catch (e){
            	$('#com-loading').modal('hide');
            	bootAlert.alertDanger(e.message);
            	deferred.reject();
            }
            $('#com-loading').modal('hide');
            return deferred.promise();
        },
        
        /**
         * 显示图表
         */
        showChart: function(){
        	let that = this;
        	//活跃度气泡图
        	that.BubbleChat(that.chartList.valenceBubbleList, "valence_Bubble_ECharts", that.emojiConst.emojiValence, "valence",that.bubbleXMap,that.bubblePlayMap,0);
            //活跃度饼图
        	that.AnnularChart(that.chartList.valencePieList, "valence_Pie_Echarts", "valence");
            //注意力折线图
        	that.LineChart4(that.chartList.attentionBubbleList, "attention_line_ECharts", "attention",that.bubbleXMap,that.bubblePlayMap,0);
            //注意力饼图
            that.AnnularChart(that.chartList.attentionPieList, "attention_Pie_Echarts", "attention");
        },
        
        /**
         * 指定回放位置
         *
         * @param durtion
         */
        playBack: function (durtion) {
        	let that = this;
        	
        	that.videoPlayList.forEach(function(play){
        		play.currentTime(durtion);
        		play.play();
        	});
//        	
        	that.videoPlayList2.forEach(function(play){
        		play.currentTime(durtion);
        		play.play();
        	});
        },

        /* *******************************************************************
  	    ***************  将后台获取的数据转换成Echart所需的数据格式    ***************
  	    ******************************************************************* */  
        /**
         * 气泡图
         * @param bubbleList
         * @returns {Array}
         */
        chgBubbleFormat: function (bubbleList) {
        	var that = this;
            var data = new Array();
            for (var i in bubbleList) {
                //动态新增数组到level值，保证每一个level必定有其对应的数组
                for (var j = (data.length); j <= bubbleList[i].level; j++) {
                    data.push(new Array());
                }
                if(data[bubbleList[i].level]){
                	data[bubbleList[i].level].push(new Array(
                            bubbleList[i].minutes, bubbleList[i].value,
                            bubbleList[i].number, bubbleList[i].percent,
                            bubbleList[i].level, bubbleList[i].name)
                        );
                }
            }
            return data;
        },

        /**
         * 饼图
         * @param pieList
         * @param type
         * @returns {Array}
         */
        chgPieFormat: function (pieList, type) {
        	let that = this;
            var data = new Array();          
            pieList.forEach(function(val, index){
            	//动态新增数组到level值，保证每一个level必定有其对应的数组
                var pie = {};
                pie.value = val.number;
                pie.name = that.levelName(val.level, type);
                pie.percent = val.percent;
                that.totalPie += pie.value;
                data.push(pie);
            });
            return data;
        },

        /**
         * 折线图
         * @param bubbleList
         * @param type
         * @returns {Array}
         */
        chgLineFormat: function (bubbleList, type) {
            var lineData = new Array();
            var xdata = new Array();
            var ydata = new Array();

            var k = 0;
            var i0 = 0;
            var i1 = 0;
            var i2 = 0;
            for (var i in bubbleList) {
                //动态新增数组到level值，保证每一个level必定有其对应的数组
                for (var j = (ydata.length); j <= bubbleList[i].level; j++) {
                    ydata.push(new Array());
                }
                if (bubbleList[i].level == 0) {
                    xdata[k] = bubbleList[i].minutes;
                    k++;
                }
                if (type == "tired") {
                    switch (bubbleList[i].level) {
                        case 0:
                            ydata[bubbleList[i].level][i0] = bubbleList[i].percent.replace('%', '');
                            i0++;
                            continue;
                        case 1:
                            ydata[bubbleList[i].level][i1] = bubbleList[i].percent.replace('%', '');
                            i1++;
                            continue;
                    }
                } else if (type == "valence") {
                    switch (bubbleList[i].level) {
                        case 0:
                            ydata[bubbleList[i].level][i0] = bubbleList[i].percent.replace('%', '');
                            i0++;
                            continue;
                        case 1:
                            ydata[bubbleList[i].level][i1] = bubbleList[i].percent.replace('%', '');
                            i1++;
                            continue;
                        case 2:
                            ydata[bubbleList[i].level][i2] = bubbleList[i].percent.replace('%', '');
                            i2++;
                            continue;
                    }
                }
            }
            lineData.push(xdata);
            lineData.push(ydata);
            return lineData;
        },
      //折线图valance
        chgLineFormat2:function(bubbleList,type){
        	var lineData = new Array();
        	var xdata = new Array();
        	var ydata = new Array();
        	
        	var k = 0;
        	var i0 = 0;
        	var i1 = 0;
        	var i2 = 0;
        	for(var i in bubbleList){
        		//1 中间档位 （平静）
        		if(bubbleList[i].level == 1){
        			xdata[k] = bubbleList[i].minutes;
        			if(type == "tired"){
            			
            		}else if(type == "valence"){
            			ydata[k] = bubbleList[i].averageValueAll;
            		}else if(type == "neutral"){
            			ydata[k] = bubbleList[i].percent.replace("%", "");
            		}else if(type == "valence1"){
            			ydata[k] = bubbleList[i].value.toFixed(2);
            		}
        			k++;
        		}
        	}
        	
        	lineData.push(xdata);
        	lineData.push(ydata);
        	return lineData;
        },
      //每分钟人脸数折线图
        chgLineFormat3:function(bubbleList,type){
        	var that = this;
        	var lineData = new Array();
        	var xdata = new Array();
        	var ydata = new Array();
        	
        	var k = 0;
        	var i0 = 0;
        	var i1 = 0;
        	var i2 = 0;
        	for(var i in bubbleList){
        		if(bubbleList[i].level == 0){
        			xdata[k] = bubbleList[i].minutes;
        			if(type == 'attention'){
        				ydata[k] = bubbleList[i].attentionLevel;
        			}else{
        				ydata[k] = bubbleList[i].faceCount;
        			}
        			
        			k++;
        		}
        		//分钟数
        		that.bubbleXMap[bubbleList[i].minutes] = bubbleList[i].dateTime;
                //视频播放位置
                that.bubblePlayMap[bubbleList[i].minutes] = bubbleList[i].playDuration;
        	}
        	
        	lineData.push(xdata);
        	lineData.push(ydata);
        	return lineData;
        },
        /**
         * 注意力
         * @param bubbleList
         * @param type
         * @returns {Array}
         */
        chgLineFormat:function(bubbleList,type){
            var that = this;
            var lineData = new Array();
            var xdata = new Array();
            var ydata = new Array();

            var k = 0;
            var i0 = 0;
            var i1 = 0;
            var i2 = 0;

            for(var i in bubbleList){
                if(bubbleList[i].level == 0){
                    xdata[k] = bubbleList[i].minutes;
                    ydata[k] = bubbleList[i].faceCount;
                    k++;
                }
                //分钟数
                that.bubbleXMap[bubbleList[i].minutes] = bubbleList[i].dateTime;
                //视频播放位置
                that.bubblePlayMap[bubbleList[i].minutes] = bubbleList[i].playDuration;
            }

            lineData.push(xdata);
            lineData.push(ydata);
            return lineData;
        },
        /**
         *
         * @param level
         * @param type
         * @returns {*}
         */
        levelName: function (level, type) {
        	let that = this;
            if (type == "valence") {
                switch (level) {
                    case 0:
                        return that.emojiConst.emojiValence[0];
                    case 1:
                        return that.emojiConst.emojiValence[1];
                    case 2:
                        return that.emojiConst.emojiValence[2];
                }
            } else if (type == "attention") {
                switch (level) {
                    case 10:
                        return that.emojiConst.emojiAttentionPie[0];
                    case 11:
                        return that.emojiConst.emojiAttentionPie[1];
                }
            } else if (type == "tired") {
                switch (level) {
                    case 0:
                        return that.emojiConst.emojiTired[0];
                    case 1:
                        return that.emojiConst.emojiTired[1];
                }
            }else if(type == "scene"){
            	switch (level) {
                case 0:
                    return that.emojiConst.emojiScenePie[0];
                case 1:
                    return that.emojiConst.emojiScenePie[1];
            }
            }
            return "未知";
        },
    	
	    /* *******************************************************************
 	    **********************echarts 图表结构及定义****************************
 	    ******************************************************************* */  
	    /**
	     * 环形图
	     * @constructor
	     */
	    AnnularChart: function (data, dom, type) {
	    	let that = this;
	        /*
	         1.惊讶           #faa701
	         2.恐惧           #522623
	         3.轻蔑           #7e7e7e
	         4.喜悦           #ffe441
	         5.厌恶           #03351c
	         6.悲伤           #5401a0
	         7.愤怒           #d32431
	         */
	        var legendData = null;
	        //清理interval
//	        if (that.interval) {
//	            clearInterval(that.interval);
//	        }
	        
	        if(data == null || data.length == 0){
	        	$('#' + dom).html('<div style="padding-top: 2.2rem;">没有识别到相应的情绪数据</div>');
	        	return false;
	        }
	        
	        var colorData = null;
	
	        if (type == "valence") {
	            legendData = [
	                {
	                    name: that.emojiConst.emojiValence[0],
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/blue.png'
	                },
	                {
	                    name: that.emojiConst.emojiValence[1],
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/green.png'
	                },
	                {
	                    name: that.emojiConst.emojiValence[2],
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/orange.png'
	                }
	            ];
	            colorData = [that.emojiConst.emojiColor.joy, that.emojiConst.emojiColor.valence,that.emojiConst.emojiColor.contempt];
	        } else if (type == "attention") {
	            legendData = [
	                {
	                    name: that.emojiConst.emojiAttentionPie[0],
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/blue.png'
	                },
	                {
	                    name: that.emojiConst.emojiAttentionPie[1],
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/orange.png'
	                },

	            ];
	            colorData = [that.emojiConst.emojiColor.contempt,that.emojiConst.emojiColor.joy];
	        } else if (type == "tired") {
	            legendData = [
	                {
	                    name: '疲劳',
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/orange.png'
	                },
	                {
	                    name: '不疲劳',
	                    textStyle: {
	                        fontSize: 13,
	                        color: '#333333'
	                    },
	                    icon: 'image://../images/VideoList/green.png'
	                }
	            ];
	            colorData = [that.emojiConst.emojiColor.valence, that.emojiConst.emojiColor.contempt];
	        }else if (type == "scene") {
	            legendData = [
	                          {
	                              name: that.emojiConst.emojiScenePie[0],
	                              textStyle: {
	                                  fontSize: 13,
	                                  color: '#333333'
	                              },
	                              icon: 'image://../images/VideoList/orange.png'
	                          },
	                          {
	                              name: that.emojiConst.emojiScenePie[1],
	                              textStyle: {
	                                  fontSize: 13,
	                                  color: '#333333'
	                              },
	                              icon: 'image://../images/VideoList/green.png'
	                          }
	                      ];
	                      colorData = [that.emojiConst.emojiColor.contempt,that.emojiConst.emojiColor.valence];
	                  }

	        var myChart = echarts.init(document.getElementById(dom));
	        var option = {
	            title: {
	                text: '比例图',
	                left: '4%',
	                top: '1.5%',
	                textStyle: {
	                    //文字颜色
	                    color: '#FFF',
	                    //字体风格,'normal','italic','oblique'
	                    fontStyle: 'normal',
	                    //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                    fontWeight: 'normal',
	                    //字体系列
	                    fontFamily: 'sans-serif',
	                    //字体大小
	                    fontSize: 14
	                }
	            },
	            tooltip: {
	                trigger: 'item',
	                formatter: "{a} <br/>{b}: {c} ({d}%)"
	            },
	            legend: {
//	                bottom: that.shixutop,
//	                right: 'center',
//	                itemGap: 10,
	                top:'13%',
	                orient: 'vertical|horizontal',
	                x: 'left',
	                
	                data: legendData,
	//        	        formatter: "{b}({d}%)"
	                formatter: function (name) {
	                    var legendText = name;
	                    for (var i = 0; i < data.length; i++) {
	                        if (data[i].name == name) {
	                            legendText = name + " " + data[i].percent;
	                        }
	                    }
	                    return legendText;
	                }
	            },
	            series: [
	                {
	                    name: '识别分析结果',
	                    type: 'pie',
	                    center: ['65%', '50%'],
	                    radius: ['55%', '65%'],
	                    avoidLabelOverlap: false,
	                    label: {
	                        normal: {
	                            show: false,
	                            position: 'center',
	//        	                    formatter:function(data){
	//        	                        return data.name + "\r\n" + (data.value*100/totalPie).toFixed(2) + "%";
	//        	                    }
	                        },
	                        emphasis: {
	                            show: true,
	                            textStyle: {
	                                fontSize: '28',
	                                fontWeight: 'bold',
	                            }
	                        }
	                    },
	                    labelLine: {
	                        normal: {
	                            show: true
	                        }
	                    },
	                    data: data,
	                    itemStyle: {
	                        normal: {
	                            label: {
	                                show: true,
	                                formatter: '{d}%'
	                            },
	                            labelLine: {show: true}
	                        }
	                    }
	//        	            formatter:function(name){
	//            	        	var legendText = name;
	//            	            for (var i = 0; i < data.length; i++) {
	//            	            	if(data[i].name == name){
	//            	            		legendText = name + "\r\n " + (data[i].value*100/totalPie).toFixed(2) + "%";
	//            	            	}
	//    						}
	//            	            return legendText;
	//            	        },
	                }
	            ],
	            //对应的7中表情 color
	            color: colorData,
	            animation: false
	        };
	        myChart.clear();
	        myChart.setOption(option);
	
	        myChart.currentIndex = -1;
	
//	        that.interval = setInterval(function () {
//	            var dataLen = option.series[0].data.length;
//	            // 取消之前高亮的图形
//	            myChart.dispatchAction({
//	                type: 'downplay',
//	                seriesIndex: 0,
//	                dataIndex: myChart.currentIndex
//	            });
//	            myChart.currentIndex = (myChart.currentIndex + 1) % dataLen;
//	            // 高亮当前图形
//	            myChart.dispatchAction({
//	                type: 'highlight',
//	                seriesIndex: 0,
//	                dataIndex: myChart.currentIndex
//	            });
//	            // 显示 tooltip
//	//            myChart.dispatchAction({
//	//                type: 'showTip',
//	//                seriesIndex: 0,
//	//                dataIndex: myChart.currentIndex
//	//            });
//	        }, 1000);
	
	//        myChart2.on('click', function (params) {
	//            console.info(params);
	//        });
	        
	        return myChart;
	    },
	
	    /**
	     * 气泡图
	     * @param Bubbledata
	     * emojiType    等级
	     * statisticsType    类别
	     * @constructor
	     */
	    BubbleChat: function (Bubbledata, dom, emojiType, statisticsType, bubbleXMap,bubblePlayMap, classType) {
	    	let that = this;
	    	var starttime = new Date(that.curriculumInfo.starttime);
	        var myChart = echarts.init(document.getElementById(dom));
	
	        var data = Bubbledata;
	        var yLable;
	        var BubbleLegendData = null;
	        if (statisticsType == "valence") {
	            BubbleLegendData = that.emojiConst.emojiValence;
	            yLable = "情绪系数";
	        } else if (statisticsType == "attention") {
	            BubbleLegendData = that.emojiConst.emojiAttention;
	            yLable = "情绪系数";
	        }
	
	        var option = {
	            tooltip: {
	                trigger: "item",
	                formatter: function (params) {
	                    return '<div>' + emojiType[params.value[4]] + '</div>' +
	                        '<div>' + params.value[3] + '</div>' +
	                        '<div>' + that.bubbleXMap[params.value[0]] + '</div>';
	                }
	            },
	            title: {
	                text: '时序图',
	                left: '4%',
	                top: '1.5%',
	                textStyle: {
	                    //文字颜色
	                    color: '#fff',
	                    //字体风格,'normal','italic','oblique'
	                    fontStyle:'normal',
	                    //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                    fontWeight: 'normal',
	                    //字体系列
	//                    fontFamily:'sans-serif',
	                    //字体大小
	                    fontSize: 15
	                }
	            },
	            grid: {
	                left: '5%',
	            },
	            backgroundColor: 'transparent',
	            legend: {
	                right: 100,
	                top: 2,
	                data: BubbleLegendData,
	                textStyle: {
	                    color: '#000',
	                    fontSize: 14
	                }
	            },
	            xAxis: {
	                name: "时间(min)",
	                splitLine: {
	                    show: false
	                },
	                axisLine: {
	                    lineStyle: {
	                        color: '#333333'
	                    },
	                    
	                },
                    boundaryGap : false,
                    interval:1,
                    splitNumber:15,
                    min:0,
                    scale:true,
	                axisLabel: {
	                    formatter:function(value){
                            if(value == 0)
                            	return "";
                            value = parseInt(value);
                            if(!bubbleXMap[value])
                                return "";
                            var nowTime = new Date(bubbleXMap[value]);
                            var lastTime = new Date(bubbleXMap[value - 1]);
//                            if(lastTime.getMinutes() == nowTime.getMinutes())
//                                return "";
                            var getNowMinutes = parseInt((nowTime.getTime() - starttime.getTime())/(1000*60)) + 1;
	                    	var getLastMinutes = parseInt((lastTime.getTime() - starttime.getTime())/(1000*60)) + 1;
	                    	if(getNowMinutes - getLastMinutes == 0)
	                    		return "";
	                    	if(getNowMinutes%2 == 0)
	                    		return "";
	                    	return getNowMinutes;
	                    }
	                }
	            },
	            yAxis: {
	                name: yLable,
	                splitLine: {
	                    lineStyle: {
	                        type: 'solid'
	                    }
	                },
	                axisLine: {
	                    lineStyle: {
	                        color: '#333333'
	                    }
	                },
	                scale: true,
                    max: 100,
                    min: -100
	            },
	            dataZoom: [
	                {
	                    type: 'inside',
	                    show: true,
	                    xAxisIndex: [0],
	                    start: 0,
	                    end: 100
	                }
	                ,
	//                 {
	// 	                type: 'inside',
	// 	                show: true,
	// 	                yAxisIndex: [0],
	// 	                start: 1,
	// 	                end: 100
	//                  }
	            ],
	            series: [
                     {
 	                    name: emojiType[1],
 	                    data: data[1],
 	                    type: 'scatter',
 	                    symbolSize: function (data) {
 	                        return Math.sqrt(Number(data[3].replace("%", ""))) * 4;
 	                    },
 	                    itemStyle: {
 	                        normal: {
 	                            shadowBlur: 10,
 	                            shadowColor: 'rgba(25, 100, 150, 0.5)',
 	                            shadowOffsetY: 5,
 	                            color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
 	                                offset: 0,
 	                                color: that.emojiConst.emojiColor.valence
 	                            }, {
 	                                offset: 1,
 	                                color: that.emojiConst.emojiColor.valence
 	                            }])
 	                        }
 	                    }
 	                },
	                {
	
	                    name: emojiType[0],
	                    data: data[0],
	                    type: 'scatter',
	                    symbolSize: function (data) {
	                        return Math.sqrt(Number(data[3].replace("%", ""))) * 4;
	                    },
	                    itemStyle: {
	                        normal: {
	                            shadowBlur: 10,
	                            shadowColor: 'rgba(120, 36, 50, 0.5)',
	                            shadowOffsetY: 5,
	                            color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
	                                offset: 0,
	                                color: that.emojiConst.emojiColor.joy
	                            }, {
	                                offset: 1,
	                                color: that.emojiConst.emojiColor.joy
	                            }])
	                        }
	                    }
	                },
	                {
	                    name: emojiType[2],
	                    data: data[2],
	                    type: 'scatter',
	                    symbolSize: function (data) {
	                        return Math.sqrt(Number(data[3].replace("%", ""))) * 4;
	                    },
	                    itemStyle: {
	                        normal: {
	                            shadowBlur: 10,
	                            shadowColor: 'rgba(120, 36, 50, 0.5)',
	                            shadowOffsetY: 5,
	                            color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [{
	                                offset: 0,
	                                color: that.emojiConst.emojiColor.contempt
	                            }, {
	                                offset: 1,
	                                color: that.emojiConst.emojiColor.contempt
	                            }])
	                        }
	                    }
	                }
	            ]
	        };
	        myChart.clear();
	        myChart.setOption(option);
	       
	        if(classType == 0){
	        	 myChart.on('click', function (params) {
	 	        	that.playBack(bubblePlayMap[params.data[0]]);
	 	        });
	        }
	        return myChart;
	    },
	    /**
	     * 折线图4 注意力折线图
	     */
	    LineChart4:function(lineData, dom, Type,bubbleXMap,bubblePlayMap, classType){
	    	let that =this;
	    	var starttime = new Date(that.curriculumInfo.starttime);
	    	var myChart = echarts.init(document.getElementById(dom));
	    	var legend = null;
	    	var colorT;
	    	
	    	if(Type == 'scene'){
	    		legend = that.emojiConst.emojiSceneLine;
	    		colorT = that.emojiConst.emojiColor.contempt;
	    	}else if(Type == 'interaction'){
	    		legend = that.emojiConst.emojiAttentionLine;
	    		colorT = that.emojiConst.emojiColor.contempt;
	    	}else if(Type == 'attention'){
                legend = that.emojiConst.emojiAttentionLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }
	    	var option = {
	    			title: {
	                    text: '时序图',
	                    left:'2%',
	                    top: '1.5%',
	                    textStyle:{
	                        //文字颜色
	                        color:'#FFF',
	                        //字体风格,'normal','italic','oblique'
	                        fontStyle:'normal',
	                        //字体粗细 'normal','bold','bolder','lighter',100 | 200 | 300 | 400...
	                        fontWeight : 'bolder',
	                        //字体系列
	//                        fontFamily:'sans-serif',
	                        //字体大小
	                　　　　 		fontSize:15
	                    }
	                },
	    		    tooltip : {
	    		        trigger: 'axis',
	    		        axisPointer: {
	    		            type: 'cross',
	    		            label: {
	    		                backgroundColor: '#6a7985'
	    		            }
	    		        }
	                ,formatter:function(data){
	//    		        	console.log("------data:" + data);
	    		        	 var result = "&nbsp;&nbsp;&nbsp;"+bubbleXMap[data[0].name]+"</br>";
	     		        	for (var int = 0; int < data.length; int++) {
	     		        		result +=  data[int].marker + "" + data[int].seriesName + "：";
	     		        		if(data[int].value == 0){
	     		        			result += that.emojiConst.emojiAttentionPie[0] + " </br>"
	     		        		}else{
	     		        			result += that.emojiConst.emojiAttentionPie[1] + " </br>"
	     		        		}
	    					}
	     		        	return result;
	    		        }
	    		    },
	    		    legend: {
	    		    	right: 100,
	                    top: that.shixutop,
	    		        data: legend,
	    		        textStyle: {
	                        color: '#000',
	                        fontSize: 14
	                    }
	    		    },
	    		    
	    		    grid: {
		                left: '5%',
		            },
	    		    
//	    		    grid: {
//	                    left: '0%',
//	                    right: '10%',
//	                    top: '24%',
//	                    bottom: '5%',
//	                    containLabel: true
//	                },
	    		    xAxis : [
	    		        {
	    		        	name: "时间(min)",
	    		            type : 'category',
	    		            boundaryGap : false,
                            interval:1,
                            splitNumber:15,
                            min:0,
	    		            data : lineData[0],
	    		            axisLabel: {
                                interval:0,
	    	                    formatter:function(value){
                                    var startTime = new Date(bubbleXMap[1]);
//                                    if(value == "0")
//                                        return value;
                                    var nowTime = new Date(bubbleXMap[value]);
                                    var lastTime = new Date(bubbleXMap[value - 1]);
//                                    if(lastTime.getMinutes() == nowTime.getMinutes())
//                                        return "";
                                    var getNowMinutes = parseInt((nowTime.getTime() - starttime.getTime())/(1000*60)) + 1;
        	                    	var getLastMinutes = parseInt((lastTime.getTime() - starttime.getTime())/(1000*60)) + 1;
        	                    	if(getNowMinutes - getLastMinutes == 0)
        	                    		return "";
        	                    	if(getNowMinutes%2 == 0)
        	                    		return "";
        	                    	return getNowMinutes;
	    	                    }
	    	                }
	    		        }
	    		    ],
	    		    yAxis : [
	    		        {
	    		        	name: legend,
	    		            type : 'value',
	//    		            minInterval : 1,
	    		            axisLabel: {
	    	                    formatter:function(value){
	    	                    	if(value == 0){
	         		        			return that.emojiConst.emojiAttentionPie[0] ;
	         		        		}else if(value == 1){
                                        return that.emojiConst.emojiAttentionPie[1] ;
                                    }else if(value == -1){
                                        return that.emojiConst.emojiAttentionPie[2] ;
                                    }
	    	                    }
	    	                }
	    		        }
	    		    ],
	    		    dataZoom: [
	    		                 {
	    			                type: 'inside',
	    			                show: true,
	    			                xAxisIndex: [0],
	    			                start: 0,
	    			                end: 100
	    		                 }
	    		    ],
	    		    series : [
	    		        {
	    		            name:legend,
	    		            type:'line',
                            symbol:'Circle',
	    		            itemStyle : {  
	                            normal : {  
	                                color:colorT,  
	                                lineStyle:{  
	                                    color:colorT
	                                }  
	                            }  
	                        }, 
	    		            label: {
	    		                normal: {
	    		                    show: false,
	    		                    position: 'top'
	    		                }
	    		            },
	//    		            areaStyle: {normal: {
	//    		            	color:emojiColor.contempt
	//    		            }},
	    		            data:lineData[1],
	    		            color : colorT
	    		        }
	    		    ],
	    		    animation: false
	    		};
	    	
	    	myChart.clear();
	    	myChart.setOption(option);
	    	
	    	if(classType == 0){
	    		myChart.on('click', function (params) {
		    		that.playBack(bubblePlayMap[parseInt(params.name)]);
		    	});
	        }
	    	
	    	return myChart;
	    },
	    videoErrorInfo:function(){
	    	return '<div class="gavinPlabox"><div id="gavinPlay"></div></div>';
	    },
		/**
		 * 方法说明：获取统计列表
		 */
        GetList: function () {   
        	var that = this;
            var params = {'currentPage':this.pageModel.currentPage,
            			  'pageRecorders':this.pageModel.pageRecorders,
            			  'rule':this.pageModel.rule,
            			  'expression':this.pageModel.expression,
            			  'curriculumLevel':1};
            try {
            	requsestSer.post(camEmotion.findComprehensiveReportList, params).then(function (data) {
                    if (data.meta.success) {
                    	that.comprehensiveReportList = [];
                    	data.data.objList.forEach(function(value){
                    		if(value.id != that.curriculumId){
                    			that.comprehensiveReportList.push(value);
                    		}
                    	});
                    	
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
    }//methods end
});