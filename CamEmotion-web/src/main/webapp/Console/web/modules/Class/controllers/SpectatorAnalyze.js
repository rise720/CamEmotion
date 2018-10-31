/**
 * Created by houpp on 2018/2/5.
 */
new Vue({
    el:'#classvideoApp',
    data:{
        chart:true,
        myCharPic:'',
        myCharBubble:'',

        checkZIndex: 10,
        curriculumId:0,			// 传入参数课程id
        studentid:0,           //传入学生id
        studentImg:"../images/headimg.png",         //
        compareListTabActive: 0,	//优质课对比一览当前标签页的序号
        //video_path:"/EmotionWebResourceFiles/",
        studentsEmotionList:[],
        bubbleXMap:[],
        bubblePlayMap:[],
        videoData:[],
        videoData2:[],
        videoTblList:[],		// curriculum_video_tbl对应数据列表
        videoPlayList:[],		// videoTblList对应的视频对象
        videoTblList2:[],		// curriculum_video_tbl对应数据列表
        videoPlayList2:[],		// videoTblList对应的视频对象
        curriculumInfo:{},
        evaluationData:{},
        evaluationStatus:false,
        secondsPerPart:15,
        moodXMap:[],
        moodPlayMap:[],
        chartList:{
            moodLineList: "",
            valenceBubbleList: "",
            valencePieList: "",
            valencewaveRateList:"",
            valenceAverageList:"",
            valenceFluctuationDeviationList:"",
            valenceMeanAmplitudeList:"",
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
            neutralLineList: "",
            participationList: "",
            tActionRateList: "",
            emotionCHList: ""
        },
        chartData:{
            echarts1: "",
            echarts2: "",
            echarts2_pie1: "",
            echarts2_pie2: "",
            echarts2_pie3: "",
            echarts3: "",

            //echarts5: "",
            echarts6: "",
            echarts6_pie1: "",
            echarts6_pie2: "",
            echarts6_pie3: "",
            echarts7: "",
        },
        valence_vals:{
            waveRate:0,
            fluctuationDeviation:0,
            meanAmplitude:0,
            uniformFluctuation:0,
            averageVal:0,
            waveRateAv:0,
            fluctuationDeviationAv:0,
            uniformFluctuationAv:0,
            averageValAv:0,
            puzzled:0,
            calm:0,
            excited:0,
            listenClass:0,
            practice:0,
            discussion:0,
            emotionCH:0

        },
        valence_vals_status:{
            waveRate:0,
            fluctuationDeviation:0,
            meanAmplitude:0,
            uniformFluctuation:0,
            averageVal:0,
            puzzled:0,
            calm:0,
            excited:0,
            listenClass:0,
            practice:0,
            discussion:0,
            emotionCH:0
        },
        whole_evaluate_view:{
            activityRate:"",
            waveRate:"",
            fluctuationDeviation:"",
            uniformFluctuation:"",
            interactionFrequency:"",
            senceRatio:"",
            emotionCH:"",
            teachType:""
        },
        /**
         * ----------优质课----------------
         */
        bubbleXMap_high:[],
        chartList_high:{
            valenceBubbleList: "",
            valencePieList: "",
            valencewaveRateList:"",
            valenceAverageList:"",
            valenceFluctuationDeviationList:"",
            valenceMeanAmplitudeList:"",
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
            neutralLineList: "",
            emotionCHList: ""
        },
        valence_vals_high:{
            waveRate:0,
            fluctuationDeviation:0,
            meanAmplitude:0,
            uniformFluctuation:0,
            averageVal:0,
            puzzled:0,
            calm:0,
            excited:0,
            listenClass:0,
            practice:0,
            discussion:0,
            emotionCH:""
        },
        /**
         * ----------优质课----------------
         */
        emojiConst:{						// 图标显示用常量字段
            emojiValence:["思考", "倾听", "活跃"],
            emojiValenceLine:["情绪波动"],
            emojiSceneLine:["课堂情景"],
            emojiScenePie:["讨论","练习","听课"],
            emojiAttention:["分心", "一般", "专心"],
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
                valence:"#4DA74D", // 一般
                joy:"#0B62A4",		// 消极
                contempt:"#CB4B4B"	// 积极
            },
            basicChartColor:['#D48265','#61A0A8','#2F4554','#91C7AE']
        },
        pageModel: {
            currentPage:'1',
            pageRecorders:'6',
            rule:'DESC',
            expression:'id',
            context:''
        },
        index: -1,
        all: 1,       // 总页数
        cur: 1,
        comprehensiveReportList: [],
        totalPie:0,
        interval:null,
        shixutop:0
    },
    created: function(){
      
        let videoBoxWidth = $(window).height()-400;

        //页面显示内容区域高度
        let contentHeight = $(window).height()-140;

        let dataAnalyseHeight = contentHeight * 0.4 - 10;
        $(".contentBox").css("height", contentHeight);
        $(".videoBigBox").css("height", contentHeight * 0.6);
        $(".dataAnalyse").css("height", dataAnalyseHeight);

        let conterntWidth = $(".contentBox").width();

        //整体宽高
        $(".integralAnalysis").css("width",conterntWidth);
        $(".integralAnalysis").css("height",dataAnalyseHeight-10);

        // // 个体宽高
        // $(".ontoanalysisBox").css("width",conterntWidth);
        // $(".ontoanalysisBox").css("height",dataAnalyseHeight - 10);

        //整体echart图
        $("#valence_Pie_Echarts").css("height",dataAnalyseHeight - 47);
        $("#valence_Bubble_ECharts").css("height",dataAnalyseHeight - 2 );

       

    },
    watch:{
        chart:function(oldval,newval){
            console.log('old'+$('.maxbox').width());
            console.log('宽'+$('.maxbox').width()/2+'px');
            console.log('高'+$('.maxbox').width()/2*0.68+'px');
            // $('.maxvideo').width($('.maxbox').width()/2-15+'px').height($('.maxbox').width()/2*0.80+'px');
        }
    },
    mounted:function(){
        var that = this;
        window.onresize= function(){
           
           
            //页面显示内容区域高度
           
           var contentHeight = $(window).height()-140;
   
           let dataAnalyseHeight = contentHeight * 0.4 - 10;
           $(".contentBox").css("height", contentHeight);
           $(".videoBigBox").css("height", contentHeight * 0.6);
           $(".dataAnalyse").css("height", dataAnalyseHeight);
   
           let conterntWidth = $(".contentBox").width();
   
           //整体宽高
           $(".integralAnalysis").css("width",conterntWidth);
           console.log(dataAnalyseHeight)
           $(".integralAnalysis").css("height",dataAnalyseHeight);

           that.myCharPic.resize();  
           that.myCharBubble.resize();  

        }
      
       
        // window.addEventListener('resize',()=>{
             
    
          
        // })
        common.checkUrl();
        console.log('------------------/classvideoApp');
        var that = this;
        // $('#page-wrapper').css('min-height', $(window).height() - 100 + 'px');

        // $('.cac-chart').height($(window).height() - 140 + 'px');

        // 清理interval
        if (this.interval) {
            clearInterval(this.interval);
        }
        $('#com-loading').modal('show');
        var par = vipspa.parse();
        that.curriculumId = par.param.curriculumId;

        //获取当前课堂的基本信息 获取视频列表 获取图表列表
        $.when(that.findCurriculum(that.curriculumId),
            that.getVideoList(that.curriculumId),
            that.getChartList(that.curriculumId)
           )

            .done(function() {
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
                        },// 竖着的音量条
                        fullscreenToggle: true
                    }
                }, function () {
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
                        },// 竖着的音量条
                        fullscreenToggle: true
                    }
                }, function () {
                    // 捕获错误异常
                    this.on('error',function(rel){
                        console.log(rel.target.firstElementChild.error.code);// 错误id
                        var errCode = rel.target.firstElementChild.error.code
                        if(errCode >= 1 && errCode <= 5){
                            console.log(rel.target.innerHTML = that.videoErrorInfo());// 错误信息
                        }
                    });
                });
                myPlay.volume(0);
                that.videoPlayList2.push(myPlay);
            });
        }
    },
    filters:{
        // 格式化时间
        formatTime: function(value){
            return (value && value.length > 16 )? value.slice(0,16) : value;
        },

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
                        //氛围评价
                        //that.comprehensiveEvaluation();
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
        /**
         * 整体课堂评价
         */
        comprehensiveEvaluation:function(){
            let deferred = $.Deferred();
            var that = this;
            var params = {"id": parseInt(that.curriculumId), "coursename": that.curriculumInfo.coursename,
                "classNature": that.curriculumInfo.classNature};
            try {
                requsestSer.post(camEmotion.ComprehensiveEvaluation, params).then(function (data) {
                    if (data.meta.success) {
                        that.evaluationData = data.data;
                        // 处理整堂评价信息
                        that.handleEvaluate(data.data);

                        that.evaluationStatus = true;
                        deferred.resolve();
                    } else {
                        that.evaluationStatus = false;
                        $('#com-loading').modal('hide');
//                    	bootAlert.alertDanger(data.meta.message);
                        deferred.reject();
                    }
                },function (response) {
                    that.evaluationStatus = false;
                    $('#com-loading').modal('hide');
                    bootAlert.alertDanger("获取整体评价出错");
                    deferred.reject();
                });
            } catch (e) {
                that.evaluationStatus = false;
                $('#com-loading').modal('hide');
                bootAlert.alertDanger(e.message);
                deferred.reject();
            }
            return deferred.promise();

        },
        // 获取播放视频的数据及对象列表
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
            console.log(data)
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
         *
         * @param VideoPer
         * @constructor
         */
        getChartList: function (curriculumid) {
            let deferred = $.Deferred();
            let that = this;
            let params = {"curriculumid": curriculumid};	// 参数无用
            try{
                requsestSer.post(camEmotion.ReportDataUrl + "?curriculumid=" + curriculumid, params).then(function (data) {
                    if (data.meta.success) {
                        // 将数据赋值给报表data
                        // 积极度
                        that.chartList.valenceBubbleList = that.chgBubbleFormat(data.data.valenceBubbleList);
                        that.chartList.valencePieList = that.chgPieFormat(data.data.valencePieList, "valence");
                        that.chartList.valenceLineList = that.chgLineFormat(data.data.valenceBubbleList, "valence");

                     

                        // 人脸数
                        that.chartList.sourceBubbleList = that.chgLineFormat3(data.data.sourceBubbleList, "valence");
                        that.chartList.faceCountPieList = that.chgPieFormat(data.data.faceCountPieList, "scene");

                        that.chartList.emotionCHList = data.data.emotionCHList;

                        that.showChart(that.curriculumInfo);
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
        getChartList_higt: function () {
            let deferred = $.Deferred();
            let that = this;
            let params = {"curriculumid": curriculumid};	// 参数无用
            try{
                requsestSer.post(camEmotion.ReportDataUrl + "?curriculumid=" + curriculumid, params).then(function (data) {
                    if (data.meta.success) {
                        // 将数据赋值给报表data
                        // 积极度
                        that.chartList.valenceBubbleList = that.chgBubbleFormat(data.data.valenceBubbleList);
                        that.chartList.valencePieList = that.chgPieFormat(data.data.valencePieList, "valence");
                        that.chartList.valenceLineList = that.chgLineFormat(data.data.valenceBubbleList, "valence");


                        // 人脸数
                        that.chartList.sourceBubbleList = that.chgLineFormat3(data.data.sourceBubbleList, "valence");
                        that.chartList.faceCountPieList = that.chgPieFormat(data.data.faceCountPieList, "scene");

                        that.chartList.emotionCHList = data.data.emotionCHList;

                        that.showChart(that.curriculumInfo);
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
        showChart: function(infos){
            let that = this;
            // 活跃度气泡图
            that.chartData.echarts1 = that.BubbleChat(infos,that.chartList.valenceBubbleList, "valence_Bubble_ECharts",
                that.emojiConst.emojiValence, "valence",that.bubbleXMap,that.bubblePlayMap,0);
            // 活跃度饼图
            that.chartData.echarts2 = that.AnnularChart(that.chartList.valencePieList, "valence_Pie_Echarts", "valence");
           
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

  

        /***********************************************************************
         * ************** 将后台获取的数据转换成Echart所需的数据格式 ***************
         **********************************************************************/
        /**
         * 气泡图
         *
         * @param bubbleList
         * @returns {Array}
         */
        chgBubbleFormat: function (bubbleList) {
            var that = this;
            var data = new Array();
            for (var i in bubbleList) {
                // 动态新增数组到level值，保证每一个level必定有其对应的数组
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
         *
         * @param pieList
         * @param type
         * @returns {Array}
         */
        chgPieFormat: function (pieList, type) {
            let that = this;
            var data = new Array();
            pieList.forEach(function(val, index){
                // 动态新增数组到level值，保证每一个level必定有其对应的数组
                var pie = {};
                pie.value = val.number;
                pie.name = that.levelName(val.level, type);
                pie.percent = val.percent;
                pie.averagePercent = val.averagePercent;

                that.totalPie += pie.value;
                data.push(pie);
            });
            return data;
        },

        /**
         * 折线图
         *
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
                // 动态新增数组到level值，保证每一个level必定有其对应的数组
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
        // 折线图valance
        chgLineFormat2:function(bubbleList,type){
            var lineData = new Array();
            var xdata = new Array();
            var ydata = new Array();

            var k = 0;
            var i0 = 0;
            var i1 = 0;
            var i2 = 0;
            for(var i in bubbleList){
                // 1 中间档位 （平静）
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
        // 每分钟人脸数折线图
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
                    ydata[k] = bubbleList[i].faceCount;
                    k++;
                }
                // 分钟数
                that.bubbleXMap[bubbleList[i].minutes] = bubbleList[i].dateTime;
                // 视频播放位置
                that.bubblePlayMap[bubbleList[i].minutes] = bubbleList[i].playDuration;
            }

            lineData.push(xdata);
            lineData.push(ydata);
            return lineData;
        },
        // 每分钟人脸数折线图
        chgLineFormat3_high:function(bubbleList,type){
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
                // 分钟数
                that.bubbleXMap_high[bubbleList[i].minutes] = bubbleList[i].dateTime;
                // 视频播放位置
                // that.bubblePlayMap_high[bubbleList[i].minutes] = bubbleList[i].playDuration;
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
                    case 0:
                        return that.emojiConst.emojiAttention[0];
                    case 1:
                        return that.emojiConst.emojiAttention[1];
                    case 2:
                        return that.emojiConst.emojiAttention[2];
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
                    case 2:
                        return that.emojiConst.emojiScenePie[2];
                }
            }
            return "";
        },

        /***********************************************************************
         * *********************echarts 图表结构及定义****************************
         **********************************************************************/
        /**
         * 环形图
         *
         * @constructor
         */
        AnnularChart: function (data, dom, type) {
            let that = this;
            /*
             * 1.惊讶 #faa701 2.恐惧 #522623 3.轻蔑 #7e7e7e 4.喜悦 #ffe441 5.厌恶 #03351c
             * 6.悲伤 #5401a0 7.愤怒 #d32431
             */
            var legendData = null;

            var colorData = null;

            if (type == "valence") {
                legendData = [
                    {
                        name: that.emojiConst.emojiValence[0],
                        textStyle: {
                            fontSize: 13,
                            color: '#000'
                        },
                        icon: 'image://../images/VideoList/blue.png'
                    },
                    {
                        name: that.emojiConst.emojiValence[1],
                        textStyle: {
                            fontSize: 13,
                            color: '#000'
                        },
                        icon: 'image://../images/VideoList/green.png'
                    },
                    {
                        name: that.emojiConst.emojiValence[2],
                        textStyle: {
                            fontSize: 13,
                            color: '#000'
                        },
                        icon: 'image://../images/VideoList/orange.png'
                    }
                ];
                colorData = [that.emojiConst.emojiColor.joy, that.emojiConst.emojiColor.valence, that.emojiConst.emojiColor.contempt];
            
            }


            that.myCharPic = echarts.init(document.getElementById(dom));
            var option = {
                title: {
                    text: '',
                    left: '4%',
                    top: '1.5%',
                    textStyle: {
                        // 文字颜色
                        color: '#F8f8f8',
                        // 字体风格,'normal','italic','oblique'
                        fontStyle: 'normal',
                        // 字体粗细 'normal','bold','bolder','lighter',100 | 200 |
                        // 300 | 400...
                        fontWeight: 'normal',
                        // 字体系列
                        fontFamily: 'sans-serif',
                        // 字体大小
                        fontSize: 14
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {

                    top:'13%',
                    orient: 'vertical|horizontal',
                    x: 'left',
                   
                    data: legendData,
                    textStyle: {
                        color: '#000',
                        fontSize: 14
                    },

                    // formatter: "{b}({d}%)"
                    formatter: function (name) {
                        var legendText = name;
                        for (var i = 0; i < data.length; i++) {
                            if (data[i].name == name) {
                                legendText = name + "  " + data[i].percent + "%";
                                if(data[i].averagePercent != '' && data[i].averagePercent != null && that.evaluationStatus){
                                    legendText += "\n参考 "+ data[i].averagePercent +"%";
                                }

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
                       
                    }
                ],
                
                color: colorData,
                animation: true
            };
            this.myCharPic.clear();
            this.myCharPic.setOption(option);
          

            this.myCharPic.currentIndex = -1;


            return this.myCharPic;
        },

        /**
         * 气泡图
         *
         * @param Bubbledata
         *            emojiType 等级 statisticsType 类别
         * @constructor
         */
        BubbleChat: function (infos,Bubbledata, dom, emojiType, statisticsType, bubbleXMap,bubblePlayMap, classType) {

            let that = this;
            var starttime = new Date(infos.starttime);
            // app.title = '气泡图';
            that.myCharBubble = echarts.init(document.getElementById(dom));
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
                    text: '',
                    left: '4%',
                    top: '1.5%',
                    textStyle: {
                        // 文字颜色
                        color: '#fff',
                        // 字体风格,'normal','italic','oblique'
                        fontStyle:'normal',
                        // 字体粗细 'normal','bold','bolder','lighter',100 | 200 |
                        // 300 | 400...
                        fontWeight: 'normal',
                        // 字体系列
                        // fontFamily:'sans-serif',
                        // 字体大小
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
                            color: '#000'
                        },

                    },
                    axisTick: {
                        show: false
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
//	                    	if(lastTime.getMinutes() == nowTime.getMinutes())
//	                    		return "";
                            var getNowMinutes = parseInt((nowTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                            var getLastMinutes = parseInt((lastTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                            if(getNowMinutes - getLastMinutes == 0)
                                return "";
                            //根据计算总量，折算出X坐标点显示间隔
                            var intervalNum = 1 + Math.floor(bubbleXMap.length / 45);
                            if(getNowMinutes%intervalNum != 0)
                                return "";
                            return getNowMinutes;
                        }
                    }
                },
                yAxis: {
                    name: yLable,
                    splitLine: {
                        lineStyle: {
                            type: 'solid',

                        },
                        show:true
                    },
                    axisLine: {
                        lineStyle: {
                            color: '#000'
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
                ],
                series: [
                    {
                        name: emojiType[1],
                        data: data[1],
                        type: 'scatter',
                        symbolSize: function (data) {
                            return Math.pow(Number(data[3].replace("%", "")) / 7,1.5);
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
                            return Math.pow(Number(data[3].replace("%", "")) / 7,1.5);
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
                            return Math.pow(Number(data[3].replace("%", "")) / 7,1.5);
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
            that.myCharBubble.clear();
            that.myCharBubble.setOption(option);
           

            if(classType == 0){
                that.myCharBubble.on('click', function (params) {
                    that.playBack(bubblePlayMap[params.data[0]]);
                });
            }
            return that.myCharBubble;
        },
        /**
         * 折线图3 (互动频度分析)
         */
        LineChart3:function(infos,lineData, dom, Type, bubbleXMap,bubblePlayMap, classType){

            let that = this;
            var starttime = new Date(infos.starttime);
            var myChart = echarts.init(document.getElementById(dom));
            var legend = null;
            var yLable = null;
            var colorT;

            if(Type == 'scene'){
                legend = that.emojiConst.emojiSceneLine;
                yLable = that.emojiConst.emojiSceneLine;
                colorT = that.emojiConst.emojiColor.valence;
            }else if(Type == 'interaction'){
                legend = that.emojiConst.emojiValenceLine;
                yLable = "情绪系数";
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'joy'){
                legend = that.emojiConst.emojiJoyLine;
                yLable = that.emojiConst.emojiJoyLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'sadness'){
                legend = that.emojiConst.emojiSadnessLine;
                yLable = that.emojiConst.emojiSadnessLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'disgust'){
                legend = that.emojiConst.emojiDisgustLine;
                yLable = that.emojiConst.emojiDisgustLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'contempt'){
                legend = that.emojiConst.emojiContemptLine;
                yLable = that.emojiConst.emojiContemptLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'anger'){
                legend = that.emojiConst.emojiAngerLine;
                yLable = that.emojiConst.emojiAngerLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'surprise'){
                legend = that.emojiConst.emojiSurpriseLine;
                yLable = that.emojiConst.emojiSurpriseLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'fear'){
                legend = that.emojiConst.emojiFearLine;
                yLable = that.emojiConst.emojiFearLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'browFurrow'){
                legend = that.emojiConst. emojiBrowFurrowLine;
                yLable = that.emojiConst.emojiBrowFurrowLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'neutral'){
                legend = that.emojiConst.emojiNeutralLine;
                yLable = "所占百分比(%)";
                colorT = that.emojiConst.emojiColor.contempt;
            }
            var option = {
                title: {
                    text: '时序图',
                    left:'2%',
                    top: '1.5%',
                    textStyle:{
                        // 文字颜色
                        color:'#FFF',
                        // 字体风格,'normal','italic','oblique'
                        fontStyle:'normal',
                        // 字体粗细 'normal','bold','bolder','lighter',100 | 200
                        // | 300 | 400...
                        fontWeight: 'bolder',
                        // 字体系列,
                        // fontFamily:'sans-serif',
                        // 字体大小
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
                        var result = "&nbsp;&nbsp;&nbsp;"+bubbleXMap[data[0].name]+"</br>";
                        for (var int = 0; int < data.length; int++) {
                            if(Type == 'neutral'){
                                result +=  data[int].marker + "" + data[int].seriesName + "：" +
                                    data[int].value + "%</br>"
                            }else{
                                result +=  data[int].marker + "" + data[int].seriesName + "：" +
                                    data[int].value + "</br>"
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
                xAxis : [
                    {
                        name: "时间(min)",
                        type : 'category',
                        boundaryGap : false,
                        data : lineData[0],
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            interval:0,
                            splitNumber:15,
                            formatter:function(value){
                                var startTime = new Date(bubbleXMap[1]);
//	    	                    	if(value == "1")
//	    	                    		return value;
                                var nowTime = new Date(bubbleXMap[value]);
                                var lastTime = new Date(bubbleXMap[value - 1]);

                                var getNowMinutes = parseInt((nowTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                                var getLastMinutes = parseInt((lastTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                                if(getNowMinutes - getLastMinutes == 0)
                                    return "";
                                //根据计算总量，折算出X坐标点显示间隔
                                var intervalNum = 1 + Math.floor(bubbleXMap.length / 45);
                                if(getNowMinutes%intervalNum != 0)
                                    return "";
                                return getNowMinutes;
                            }
                        }
                    }
                ],
                yAxis : [
                    {
                        name: yLable,
                        type : 'value',
                        splitLine: {
                            show:true
                        },
                        max:30,
                        min:-30,
                        minInterval:10
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
                        symbolSize:3,
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
                        data:lineData[1],
                        color : colorT,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
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

        /**
         * 折线图4 课堂情景折线图
         */
        LineChart4:function(infos,lineData, dom, Type,bubbleXMap,bubblePlayMap, classType){

            let that =this;
            var starttime = new Date(infos.starttime);
            var myChart = echarts.init(document.getElementById(dom));
            var legend = null;
            var colorT;

            if(Type == 'scene'){
                legend = that.emojiConst.emojiSceneLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }else if(Type == 'interaction'){
                legend = that.emojiConst.emojiValenceLine;
                colorT = that.emojiConst.emojiColor.contempt;
            }
            var option = {
                title: {
                    text: '时序图',
                    left:'2%',
                    top: '1.5%',
                    textStyle:{
                        // 文字颜色
                        color:'#FFF',
                        // 字体风格,'normal','italic','oblique'
                        fontStyle:'normal',
                        // 字体粗细 'normal','bold','bolder','lighter',100 | 200
                        // | 300 | 400...
                        fontWeight : 'bolder',
                        // 字体系列
                        // fontFamily:'sans-serif',
                        // 字体大小
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
                        var result = "&nbsp;&nbsp;&nbsp;"+bubbleXMap[data[0].name]+"</br>";
                        for (var int = 0; int < data.length; int++) {
                            result +=  data[int].marker + "" + data[int].seriesName + "：";
                            switch(data[int].value){
                                case -1:
                                    result += that.emojiConst.emojiScenePie[0] + " </br>";
                                    break;
                                case 0:
                                    result += that.emojiConst.emojiScenePie[1] + " </br>";
                                    break;
                                case 1:
                                    result += that.emojiConst.emojiScenePie[2] + " </br>";
                                    break;
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
                xAxis : [
                    {
                        name: "时间(min)",
                        type : 'category',
                        boundaryGap : false,

                        splitNumber:15,
                        data : lineData[0],
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            interval:0,
                            formatter:function(value){
                                var startTime = new Date(bubbleXMap[1]);
//	    	                    	if(value == "1")
//	    	                    		return value;
                                var nowTime = new Date(bubbleXMap[value]);
                                var lastTime = new Date(bubbleXMap[value - 1]);
                                var getNowMinutes = parseInt((nowTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                                var getLastMinutes = parseInt((lastTime.getTime() - starttime.getTime())/(1000*60)) + 1;
                                if(getNowMinutes - getLastMinutes == 0)
                                    return "";
                                //根据计算总量，折算出X坐标点显示间隔
                                var intervalNum = 1 + Math.floor(bubbleXMap.length / 45);
                                if(getNowMinutes%intervalNum != 0)
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
                        splitLine: {
                            show:true
                        },
                        minInterval:1,
                        axisLabel: {
                            formatter:function(value){
                                switch(value){
                                    case 0:
                                        return that.emojiConst.emojiScenePie[1];
                                    case 1:
                                        return that.emojiConst.emojiScenePie[2];
                                    case -1:
                                        return that.emojiConst.emojiScenePie[0];
                                }
                            }
                        },
                        max: 1,
                        min: -1
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
                        symbolSize:3,
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
         * 方法说明：获取统计列表
         */
        GetList: function () {
            var that = this;
            var params = {'currentPage':this.pageModel.currentPage,
                'pageRecorders':this.pageModel.pageRecorders,
                'rule':this.pageModel.rule,
                'expression':this.pageModel.expression,
                'curriculumLevel':1,
                'coursename': that.curriculumInfo.coursename,
                'classNature': that.curriculumInfo.classNature};
            try {
                requsestSer.post(camEmotion.findComprehensiveReportList, params).then(function (data) {
                    if (data.meta.success) {
                        that.comprehensiveReportList = [];
                        data.data.objList.forEach(function(value){
                            if(value.id != that.curriculumId && value.classNature == that.curriculumInfo.classNature
                                && value.coursename == that.curriculumInfo.coursename){
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
        /**
         * 点击对比 显示优质课列表
         */
        contrastClass:function(){
            var that = this;
            that.GetList();
            $('#classList').modal('show');
        }
        ,
        /**
         * 点击对比 展示优质课与当前课堂对比
         */
        contrastChart:function(infos){
            var that = this;

            $('#classList').modal('hide');

            $('#contrastClass').modal('show');

            that.changeChartType(0);

            setTimeout(function(){
                that.getChartList_high(infos);
            },500);
        },
        contrastBack:function(){
            var that = this;
            $('#contrastClass').modal('hide');

            that.GetList();
            $('#classList').modal('show');

        },
        /**
         * 异步获取优质课报表统计数据
         *
         * @param VideoPer
         * @constructor
         */
        getChartList_high: function (infos) {
            let deferred = $.Deferred();
            let that = this;
            let params = {"curriculumid": infos.id};	// 参数无用
            try{
                requsestSer.post(camEmotion.ReportDataUrl + "?curriculumid=" + infos.id, params).then(function (data) {
                    if (data.meta.success) {
                        // 将数据赋值给报表data
                        // 积极度
                        that.chartList_high.valenceBubbleList = that.chgBubbleFormat(data.data.valenceBubbleList);
                        that.chartList_high.valencePieList = that.chgPieFormat(data.data.valencePieList, "valence");
                        that.chartList_high.valenceLineList = that.chgLineFormat(data.data.valenceBubbleList, "valence");


                        // 每分钟内valence平均值 折线图
                        that.chartList_high.valenceLineList2 = that.chgLineFormat2(data.data.valenceBubbleList, "valence");
                        that.chartList_high.valence1LineList = that.chgLineFormat2(data.data.valenceBubbleList, "valence1");
                        // 人脸数
                        that.chartList_high.sourceBubbleList = that.chgLineFormat3_high(data.data.sourceBubbleList, "valence");
                        that.chartList_high.faceCountPieList = that.chgPieFormat(data.data.faceCountPieList, "scene");
                        that.chartList_high.emotionCHList = data.data.emotionCHList;

                        that.showChart_high(infos);
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
         * 优质课报表显示
         */
        showChart_high:function(infos){
            var that = this;
            $("#valence_Bubble_ECharts_model").show();
            $("#interaction_Bubble_ECharts_model").show();
            $("#scene_Bubble_ECharts_model").show();

            $("#valence_Bubble_ECharts_model_0").show();
            $("#interaction_Bubble_ECharts_model_0").show();
            $("#scene_Bubble_ECharts_model_0").show();

            // 互动频率总计
            that.valence_vals_high = that.handleEmotionData3(that.chartList_high);
            that.BubbleChat(infos,that.chartList_high.valenceBubbleList, "valence_Bubble_ECharts_model_0", that.emojiConst.emojiValence, "valence",that.bubbleXMap_high,that.bubblePlayMap_high,1);
            that.LineChart3(infos,that.chartList_high.valenceLineList2, "interaction_Bubble_ECharts_model_0", "interaction",that.bubbleXMap_high,that.bubblePlayMap_high,1);
            that.LineChart4(infos,that.chartList_high.sourceBubbleList, "scene_Bubble_ECharts_model_0", "scene",that.bubbleXMap_high,that.bubblePlayMap_high,1);


            that.BubbleChat(that.curriculumInfo,that.chartList.valenceBubbleList, "valence_Bubble_ECharts_model", that.emojiConst.emojiValence, "valence",that.bubbleXMap,that.bubblePlayMap,1);
            that.LineChart3(that.curriculumInfo,that.chartList.valenceLineList2, "interaction_Bubble_ECharts_model", "interaction",that.bubbleXMap,that.bubblePlayMap,1);
            that.LineChart4(that.curriculumInfo,that.chartList.sourceBubbleList, "scene_Bubble_ECharts_model", "scene",that.bubbleXMap,that.bubblePlayMap,1);


            $("#valence_Bubble_ECharts_model").show();
            $("#interaction_Bubble_ECharts_model").hide();
            $("#scene_Bubble_ECharts_model").hide();

            $("#valence_Bubble_ECharts_model_0").show();
            $("#interaction_Bubble_ECharts_model_0").hide();
            $("#scene_Bubble_ECharts_model_0").hide();

            that.totall_qs();

        },
        // 切换报表
        changeChartType:function(type){
            this.compareListTabActive = type;
            switch (type) {
                case 0:
                    $("#valence_Bubble_ECharts_model").show();
                    $("#interaction_Bubble_ECharts_model").hide();
                    $("#scene_Bubble_ECharts_model").hide();

                    $("#valence_Bubble_ECharts_model_0").show();
                    $("#interaction_Bubble_ECharts_model_0").hide();
                    $("#scene_Bubble_ECharts_model_0").hide();
                    break;
                case 1:
                    $("#valence_Bubble_ECharts_model").hide();
                    $("#interaction_Bubble_ECharts_model").show();
                    $("#scene_Bubble_ECharts_model").hide();

                    $("#valence_Bubble_ECharts_model_0").hide();
                    $("#interaction_Bubble_ECharts_model_0").show();
                    $("#scene_Bubble_ECharts_model_0").hide();
                    break;
                case 2:
                    $("#valence_Bubble_ECharts_model").hide();
                    $("#interaction_Bubble_ECharts_model").hide();
                    $("#scene_Bubble_ECharts_model").show();

                    $("#valence_Bubble_ECharts_model_0").hide();
                    $("#interaction_Bubble_ECharts_model_0").hide();
                    $("#scene_Bubble_ECharts_model_0").show();
                    break;
                default:
                    break;
            }
        }
        ,
        totall_qs:function(){
            var that = this;

            // 困惑
            if(parseFloat(that.valence_vals.puzzled) > parseFloat(that.valence_vals_high.puzzled)){
                that.valence_vals_status.puzzled = 1;
            }
            if(parseFloat(that.valence_vals.puzzled) < parseFloat(that.valence_vals_high.puzzled)){
                that.valence_vals_status.puzzled = 4;
            }
            // 平静
            if(parseFloat(that.valence_vals.calm) > parseFloat(that.valence_vals_high.calm)){
                that.valence_vals_status.calm = 2;
            }
            if(parseFloat(that.valence_vals.calm) < parseFloat(that.valence_vals_high.calm)){
                that.valence_vals_status.calm = 3;
            }
            // 兴奋
            if(parseFloat(that.valence_vals.excited) > parseFloat(that.valence_vals_high.excited)){
                that.valence_vals_status.excited = 1;
            }
            if(parseFloat(that.valence_vals.excited) < parseFloat(that.valence_vals_high.excited)){
                that.valence_vals_status.excited = 4;
            }
            //讨论
            if(parseFloat(that.valence_vals.discussion) > parseFloat(that.valence_vals_high.discussion)){
                that.valence_vals_status.discussion = 1;
            }
            if(parseFloat(that.valence_vals.discussion) < parseFloat(that.valence_vals_high.discussion)){
                that.valence_vals_status.discussion = 4;
            }
            // 听课
            if(parseFloat(that.valence_vals.listenClass) > parseFloat(that.valence_vals_high.listenClass)){
                that.valence_vals_status.listenClass = 2;
            }
            if(parseFloat(that.valence_vals.listenClass) < parseFloat(that.valence_vals_high.listenClass)){
                that.valence_vals_status.listenClass = 3;
            }
            // 练习
            if(parseFloat(that.valence_vals.practice) > parseFloat(that.valence_vals_high.practice)){
                that.valence_vals_status.practice = 1;
            }
            if(parseFloat(that.valence_vals.practice) < parseFloat(that.valence_vals_high.practice)){
                that.valence_vals_status.practice = 4;
            }
            // 课堂参与度
            if(parseFloat(that.valence_vals.emotionCH) > parseFloat(that.valence_vals_high.emotionCH)){
                that.valence_vals_status.emotionCH = 1;
            }
            if(parseFloat(that.valence_vals.emotionCH) < parseFloat(that.valence_vals_high.emotionCH)){
                that.valence_vals_status.emotionCH = 4;
            }
        },
        handleEvaluate:function(evaluationData){
            var that = this;
            that.whole_evaluate_view.activityRate = that.wholEvaluateConvert("activityRate", evaluationData.activityLevel);
            that.whole_evaluate_view.waveRate = that.wholEvaluateConvert("waveRate", evaluationData.waveRateLevel);
            that.whole_evaluate_view.fluctuationDeviation = that.wholEvaluateConvert("fluctuationDeviation", evaluationData.fluctuationDeviationLevel);
            that.whole_evaluate_view.uniformFluctuation = that.wholEvaluateConvert("uniformFluctuation", evaluationData.uniformFluctuationLevel);
            that.whole_evaluate_view.interactionFrequency = that.wholEvaluateConvert("interactionFrequency", evaluationData.interactionFrequencyLevel);
            that.whole_evaluate_view.senceRatio = that.wholEvaluateConvert("senceRatio", evaluationData.senceRatioLevel);
            that.whole_evaluate_view.emotionCH = that.wholEvaluateConvert("emotionCH", evaluationData.emotionCHLevel);
            that.whole_evaluate_view.teachType = that.wholEvaluateConvert("teachType", evaluationData.teachTypeLevel);
        },
        /**
         * 转换整体课堂评价显示 type : 类型 levelVal：档次
         */
        wholEvaluateConvert:function(type, levelVal){
            var msg = "";
            switch (type) {
                case "activityRate":
                    switch(levelVal){
                        case 1:
                            msg = "沉闷";
                            break;
                        case 2:
                            msg = "较沉闷";
                            break;
                        case 3:
                            msg = "一般";
                            break;
                        case 4:
                            msg = "较活跃";
                            break;
                        case 5:
                            msg = "活跃";
                            break;
                    }
                    break;
                case "waveRate":
                    switch(levelVal){
                        case 1:
                            msg = "节奏频率平缓";
                            break;
                        case 2:
                            msg = "节奏频率较平缓";
                            break;
                        case 3:
                            msg = "节奏频率一般";
                            break;
                        case 4:
                            msg = "节奏频率较紧凑";
                            break;
                        case 5:
                            msg = "节奏频率紧凑";
                            break;
                    }
                    break;
                case "fluctuationDeviation":
                    switch(levelVal){
                        case 1:
                            msg = "节奏起伏小";
                            break;
                        case 2:
                            msg = "节奏起伏较小";
                            break;
                        case 3:
                            msg = "节奏起伏一般";
                            break;
                        case 4:
                            msg = "节奏起伏较大";
                            break;
                        case 5:
                            msg = "节奏起伏大";
                            break;
                    }
                    break;
                case "uniformFluctuation":
                    switch(levelVal){
                        case 1:
                            msg = "波动均衡失衡";
                            break;
                        case 2:
                            msg = "波动均衡较失衡";
                            break;
                        case 3:
                            msg = "波动均衡一般";
                            break;
                        case 4:
                            msg = "波动均衡较平衡";
                            break;
                        case 5:
                            msg = "波动均衡平衡";
                            break;
                    }
                    break;
                case "interactionFrequency":
                    switch(levelVal){
                        case 1:
                            msg = "弱";
                            break;
                        case 2:
                            msg = "较弱";
                            break;
                        case 3:
                            msg = "一般";
                            break;
                        case 4:
                            msg = "较好";
                            break;
                        case 5:
                            msg = "好";
                            break;
                    }
                    break;
                case "senceRatio":
                    switch(levelVal){
                        case 1:
                            msg = "不均衡";
                            break;
                        case 2:
                            msg = "较不均衡";
                            break;
                        case 3:
                            msg = "一般";
                            break;
                        case 4:
                            msg = "较均衡";
                            break;
                        case 5:
                            msg = "均衡";
                            break;
                    }
                    break;
                case "emotionCH":
                    switch(levelVal){
                        case 1:
                            msg = "较差";
                            break;
                        case 2:
                            msg = "一般";
                            break;
                        case 3:
                            msg = "较好";
                            break;
                        case 4:
                            msg = "良好";
                            break;
                        case 5:
                            msg = "高效";
                            break;
                    }
                    break;
                case "teachType":
                    switch(levelVal){
                        case 1:
                            msg = "练习型";
                            break;
                        case 2:
                            msg = "讲授型";
                            break;
                        case 3:
                            msg = "混合型";
                            break;
                        case 4:
                            msg = "对话型";
                            break;
                    }
                    break;
                default:
                    break;
            }
            return msg;
        }
        ,
        getPJJG:function(valueLevel, valueView,maxLevel){
            var color = "#DDDDDD";
            var colorDim = ["#0B62A4","#4F81BD","#4DA74D","#FF9900","#CB4B4B"];

            switch(maxLevel){
                case 4:
                    color = (valueLevel > 0 && valueLevel < colorDim.length) ? colorDim[valueLevel] : color;
                    break;
                default:
                    color = (valueLevel > 0 && valueLevel <= colorDim.length) ? colorDim[valueLevel-1] : color;
                    break;
            }
            return '<span style="color:'+ color +';font-weight: bold;" class="pjjg_size">'+ valueView + '</span>';

        }
    }// methods end
});