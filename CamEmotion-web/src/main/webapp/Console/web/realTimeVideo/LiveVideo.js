new Vue({
    el : '#LiveVideoApp',
    data:{
        //画布描框相关参数
        canvas:null,		//画布对象
        cxt: null,			//上下文
        videoFrame: null,	//视频框
        cameraViewJ:[],
        statusInterval: null, //定时器
        cameraShow:0,
        domCss:{
            canvasHeight:""
        },
        
		myCharts:[],
		
		todayDate:'',
		todayTime:'',
        ws:null,
        cameraip:'',
        
		faceImgs:[],  
		faceVals:[],
      
        totalTime:30,
        cameraList:[],
        // 三张人脸情绪信息对象
        faceInfo:[],
        getSetOption1: {//折线图
            grid: {
                top: '5pt',
                left: 0,
                width: '95%',
                height: '95%',
                fontSize:9,
                containLabel: true
            },
            color: [
                '#0ff',    //亮蓝色
            ],

           
            xAxis: {
                type: 'value',
				position:'bottom',
                splitLine: {
                    show: false
                },
                axisLine:{
                	lineStyle:{
                		color:'#eee',
                		width:1,
                	}
                }

            },
            yAxis: {
                type: 'value',
				
                boundaryGap: [0, '100%'],
                splitLine: {
                    show: false
                },
                axisLine:{
                	lineStyle:{
                		color:'#eee',
                		width:1,
                	},
                },
                max: 100,
                min: -100,
				interval:50
            },
            series: [
                {
                    name: 'Valence',
                    type: 'line',
                    showSymbol: false,
                    hoverAnimation: false,
                    animation: false,
                    data: []
                }
            ]
        },

        playerOptions: {
//        poster: "/static/img/no_data.png", // 播放器默认图片
            notSupportedMessage: '此视频暂无法播放，请稍后再试', //允许覆盖Video.js无法播放媒体源时显示的默认信息。
            autoplay: true, // 自动播放
            muted: false,    // 默认情况下将会消除任何音频。
            loop: false,  // 导致视频一结束就重新开始。
            controls: false, // 是否显示控制栏
//          preload:'auto',  // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
            language: 'zh-CN',
//          aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
            fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
            sourceOrder: true, //
            techOrder: ['flash'
//          , 'html5'
            ], // 兼容顺序
            sources: [{ // 流配置，数组形式，会根据兼容顺序自动切换
                type: 'rtmp/mp4',
//                src: 'rtmp://live.hkstv.hk.lxdns.com/live/hks'
                src: 'rtmp://${window.location.hostname}:1935${camEmotion.rtmpAddress}'
//                src: 'rtmp://172.28.175.164/live/test2'
            }

            ],
            
        },
    },
   
    created() {
        let panelBody = $(window).height();
        //this.domCss.canvasHeight= $(".faceInfo .liclass").height() - 40 + "px"
        // this.domCss.canvasHeight = (panelBody ) * 0.333 -40  + "px";
		this.domCss.canvasHeight = $(".grid-container .item").height()  + "px";
    },
    computed: {
    },
    mounted: function() {
        let that=this;
        that.initFaceInfo(6,that.faceInfo);
		setInterval(function(){
            that.getTodayTime();
        },1000)
		
        let timeId;
        that.cameraList = camEmotion.cameraJson;
        clearInterval(timeId);
        
        this.getVideo();
        this.setInitEcharts(6);
		if(that.cameraList.length > 1)
			that.setStatusInterval();
        
        that.cameraip = that.cameraList[0].cameraip;
        this.initCanvas();
        
        timeId= setInterval(function(){
        	for (var int = 0; int < that.cameraViewJ.length; int++) {
				//that.cameraViewJ       		
        		that.CanvasClear(that.cameraViewJ[int]);
			}
//            that.CanvasClear();
        },1000);  
		
		
		
		
		/**
		*echats 自适应
		*/
         window.addEventListener("resize",function(){
            that.myCharts.forEach(function(e){
                e.resize()
            })
        })		
		
        setTimeout(function(){
			//that.swithCamera();
			 that.CanvasClear();
               that.initData(6);
		},2000);
		
    },
    components: {
    },
    methods : {
        /**
         * 
         * @param {数组长度} number 
         * @param {直播页面需要显示捕获人脸的个数} array 
         */
        initFaceInfo: function(number,array){
            if(array.length > 0) array = [];
            for(var i =0; i<number; i++){
                array.push(
                    {
                        id: -1,
                        startTime: '',
                        firstTime: 0,
                        faceImg:'',
                        valence:'',
                        data: {
                            valence:[]
                        }
                    }
                )
            }
           
        },
	    getTodayTime: function(){
            let today = new Date();
            let week = today.getDay();
            let year = today.getFullYear();
            let month = today.getMonth() + 1;
            let day =  today.getDate();
            let hours = today.getHours();
            let minute = today.getMinutes();
            let second = today.getSeconds();
            let newWeek = "";
            let m = hours > 12 ? "下午":"上午";

            if(month < 10) mouth = '0' + month;
            if(day < 10) day = '0' + day;
            if(hours < 10) hours = '0' + hours;
            if(minute < 10) minute = '0' + minute;
            if(second < 10) second = '0' + second;
            

            switch(week){
                case 1:{
                    newWeek = "星期一"
                }break;
                case 2:{
                    newWeek = "星期二"
                }break;
                case 3:{
                    newWeek = "星期三"
                }break;
                case 4:{
                    newWeek = "星期四"
                }break;
                case 5:{
                    newWeek = "星期五"
                }break;
                case 6:{
                    newWeek = "星期六"
                }break;
                case 7:{
                    newWeek = "星期日"
                }
            }
          
           this.todayTime =  `${m}${hours}点${minute}分${second}秒`
           this.todayDate =  `${year}年${month}月${day}日，${newWeek}`
        
        },
    	
    	Toggle:function(){
    		console.log(111111);
    	},
        onPlayerReadied: function() {
        },
        // record current time
        onTimeupdate: function(e) {
//      console.log('currentTime', e.cache_.currentTime)
        },
        onPlayerPlay: function($event){

        },
        onPlayerPause: function($event){

        },
    
        setInitEcharts: function(number){
            var that = this;
			that.myCharts = [];
			 for ( var i =1; i<= number; i++){
                var echart = echarts.init(document.getElementById('main'+i));
                echart.setOption(this.getSetOption1 );	
				that.myCharts.push(echart)
            }
        },
        
        getVideo:function(){
            try {
                let that = this;
                requsestSer.get(camEmotion.LiveStart+'/4')
                    .then(function(result){
                        switch (result) {
                            case 0:
                                break;
                            case 1:
                                alert("请先至课堂采集页面进行下课操作，方可正常直播！");
                                return;
                            case 2:
                                alert("发送直播命令失败");
                                return;
                            case 3:
                                console.log("该socket已经连接，无需再连");
                                return;
                            case 4:
                                alert("没有找到主机信息");
                                return;
                        }
                        
                        that.ws = new WebSocket("ws://" + window.location.host + camEmotion.ServerSocketAddress)
                        that.ws.onopen = function () {
                            console.log("onpen");
                            that.ws.send("{'SendCode':1,'SendMessage':'你好服务端，我已经知道我们连接成功了'}");
                            that.ws.send("{'SendCode':5,'SendMessage':'"+ that.cameraip +"'}");
                        }
                        that.ws.onclose = function () {
                            console.log("onclose");
                        }

                        that.ws.onmessage = function (msg) {

                            var obj = JSON.parse(msg.data);
                            switch (obj.SendCode) {
                                case 1:
                                    console.log(obj.SendMessage);
                                    break;
                                case 2:
									
									console.log('now:' + common.getNowFormatDate());
								
								
                                    var frameObj = JSON.parse(obj.SendMessage);
                                    //画框
         
                                    //从webSocket结束情绪信息，并存至echarts数组中
                                    if(frameObj.cameraIp == that.cameraip){
                                    	that.draw(frameObj);
                                    	that.dealFaceInfo(frameObj);
                                        that.updateChartOption(frameObj.cameraIp);
                                    }
                                    
//                                        if (that.faceInfo.length > 0) {
//                                        	var time = frameObj.frameId - that.faceInfo[0].frameId;
//                                            if (time > 30*1000) {
//                                            	that.faceInfo.shift();
//    										}
//										}
                                    break;
                                default:
                                    console.log(obj.SendMessage);
                                    break;
                            }
                        }
                    })
            }catch (e) {
                bootAlert.alertDanger(e.message);
            }
        },
        /**
         * 初始化画布
         */
        initCanvas: function(){
            let that = this;
            //cameraViewJ  cameraList
            try{
            	for (var int = 0; int < that.cameraList.length; int++) {
                	
//                	if(that.cameraList[that.cameraShow].cameraip = that.cameraList[int].cameraip){
                        var videoFrame = $(`.videoCenter #videoFrame${int}`);
                       
						//var canvas = $(`.videoCenter #videoCanvas${int}`);
						
                    	var canvas = document.querySelector('.videoCenter #videoCanvas' + int);
						//querySelector
					
                    	canvas.height = videoFrame.height();
                        canvas.width = videoFrame.width();
                        var cxt = canvas.getContext('2d');
                        cxt.strokeStyle = "#FF0000"
                        
                    	var camera = {
                              	 cameraip:that.cameraList[int].cameraip,
                            	 canvas:canvas,		//画布对象
                            	 cxt: cxt,			//上下文
                            	 videoFrame: videoFrame	//视频框
                             };
                    	
        				that.cameraViewJ.push(camera);
    			}
            }catch(err){
            	console.log(err);
            }
        },
        /**
         * 在视频上画头像框
         */
        draw: function(frameObj){
            let that = this;
            var camera = null;
            for (var int = 0; int < that.cameraViewJ.length; int++) {
				if(that.cameraViewJ[int].cameraip == frameObj.cameraIp)
					camera = that.cameraViewJ[int];
			}
            
            if(!camera)
            	return;
            
            //清空画布
            that.CanvasClear(camera);
            //视频原始及现在的分辨率
            let framePixel = {
                orgWPixel : frameObj.wPixel,
                orgHPixel : frameObj.hPixel,
                newWPixel : camera.canvas.width,
                newHPixel : camera.canvas.height
            };
            //保存头像框坐标的数组
            let facePixelList = [];
            frameObj.faceList.forEach((e,i)=>{

                let pixel = {
                    xPixel : e.originalPoint.minPoint.x,
                    yPixel : e.originalPoint.minPoint.y,
                    width  : e.originalPoint.maxPoint.x - e.originalPoint.minPoint.x,
                    height : e.originalPoint.maxPoint.y - e.originalPoint.minPoint.y
                }
                //修正坐标点
                pixel = that.fixPixel(framePixel, pixel);
                let emotionsName = that.GetFaceEmotions(e.emotions);
                var obj = new Object();
                obj.pixel = pixel;
                obj.emotionsName = emotionsName;
                //存至数组
                facePixelList.push(obj);
            });
            //画框
            that.drawFaceList(facePixelList,camera);
        },
        /**
         * 按原始及现在的屏幕比率，调整坐标点
         */
        fixPixel: function(framePixel, pixel){

            let yRate = framePixel.newHPixel / framePixel.orgHPixel;
            let xRate = framePixel.newWPixel / framePixel.orgWPixel;
            pixel.xPixel = pixel.xPixel * xRate;
            pixel.yPixel = pixel.yPixel * yRate;
            pixel.width = pixel.width * xRate;
            pixel.height = pixel.height * yRate;
            return pixel;
        },
        /**
         * 根据坐标数组画框
         */
        drawFaceList: function(facePixelList,camera){
            let that = this;
            for(face of facePixelList){
            	camera.cxt.strokeRect(face.pixel.xPixel, face.pixel.yPixel, face.pixel.width, face.pixel.height);  //绘制矩形边框
                var img= document.getElementById(face.emotionsName);

                let size = 50;
//        		if (face.pixel.width > face.pixel.height) {
//        			size *= face.pixel.height;
//				} else {
//					size *= face.pixel.width;
//				}
                let x = face.pixel.xPixel;
                let y = face.pixel.yPixel - size;
                camera.cxt.drawImage(img, x, y, size, size);//绘制表情图片
            }
        },
        /**
         * 清空画布
         */
        CanvasClear:function(camera){
            let that = this;
            try{
            	if(!camera || !camera.cxt)
                	that.initCanvas();
                camera.cxt.clearRect(0,0,camera.canvas.width,camera.canvas.height);
                camera.canvas.height = camera.videoFrame.height();
                camera.canvas.width = camera.videoFrame.width();
                camera.cxt = camera.canvas.getContext('2d');
                camera.cxt.strokeStyle = "#FF0000";
            	
            }catch(err){
            	console.log(err);
            }
            
        },
        /**
         * 获取最大的表情值
         */
        GetFaceEmotions : function(emotions){
            let value = -1;
            let name = "noFace";

            if (emotions.anger > value) {
                value = emotions.anger;
                name = "anger";
            }
            if (emotions.disgust > value) {
                value = emotions.disgust;
                name = "disgust";
            }
            if (emotions.joy > value) {
                value = emotions.joy;
                name = "joy";
            }
            if (emotions.surprise > value) {
                value = emotions.surprise;
                name = "surprise";
            }
            if (emotions.joy < 0.1 && emotions.disgust < 0.1 && emotions.anger < 0.1 && emotions.surprise < 0.1) {
                name = "neutral";
            }
            return name;
        },
        /**
         * 从webSocket结束情绪信息，并存至echarts数组中
         * @param frameObj
         */
        dealFaceInfo: function(frameObj){
            let that = this;
            //更新chart数组的faceid
            that.updateFaceInfoId(frameObj);
            //更新chart数组的数据
            that.updateFaceInfoData(frameObj);
        },
        /**
         * 更新chart数组的faceid
         * @param frameObj
         */
        updateFaceInfoId: function(frameObj){
            let that = this;
            
            /*
             获取来源帧的id数组，只取前三个face
             */
            let orgIdDim = [];
            for(let i = 0 ; i < frameObj.faceList.length && i < 6; i++){
                orgIdDim.push(frameObj.faceList[i].id);
            }
            /*
             获取chart数组的id数组,去除-1
             */
            let nowIdDim = [];
            for(let value of that.faceInfo){
                if(value.id != -1)
                    nowIdDim.push(value.id);
            }
            /*
             获取要添加至chart数组的i的数组
             */
            let addIdDim = [];
            for(let id of orgIdDim){
                if(nowIdDim.indexOf(id) == -1)
                    addIdDim.push(id);
            }

            /*
             更新chart数组的id信息
             如[1,2,3] ，收到[2,3,4]后，更新为[4,2,3]
             */
            for(let value of that.faceInfo){
                /*
                 更新逻辑： 1. 需要添加chart数组的id数组有记录
                 2.  当前chart的id已经过时，过时的逻辑是id为-1，或者该id在新帧中不存在
                 */
                if(orgIdDim.indexOf(value.id) == -1){
                	if(addIdDim.length > 0){
                		value.id = addIdDim[0];
                		value.valence = "";
                		value.faceImg = "";
                		value.startTime = value.startTime ? value.startTime : frameObj.frameId;
                		if(orgIdDim.length != 1){
                			value.startTime = frameObj.frameId;
                           
							value.data.valence = [];
                          
                		}
                		addIdDim.shift();
                	}
                	else{
                		value.id = -1;
                		value.startTime = 0;
                		value.firstTime = 0;
                		value.valence = "";
                		value.faceImg = "";
						
                		value.data.valence = [];
                	}

                }
            }
        },
        /**
         * 更新chart数组的数据
         * @param frameObj
         */
        updateFaceInfoData: function(frameObj){
            let that = this;
            for(let i = 0 ; i < frameObj.faceList.length && i < 6; i++){
                for(let face of that.faceInfo){
                    if(face.id != frameObj.faceList[i].id)
                        continue;
                    //赋值
//                    if(!face.faceImg || (face.faceImg == "../images/happy@2x.png"))
                     face.faceImg = `http://${window.location.host}/EmotionWebResourceFiles/FaceImage/${frameObj.courseId}/${frameObj.cameraIp}/${frameObj.frameId}/${face.id}.png`;
                  
					 let valence = frameObj.faceList[i].emotions.valence;
                    let calmness = 0;
                    let nowTime = (!face.startTime) ? 0 : (frameObj.frameId - face.startTime)/1000;
//                    if(happiness < 0.1 && boredom < 0.1 && angry < 0.1 && surprise < 0.1)
//                        calmness = 100;                    
                    let value = valence;
                    let name = "";
					
					if( -100 <= value && value < -10 ){
					   face.valence = "思考";
					 }else if (-10 <= value && value < 10 ) {
					   face.valence = "倾听";
					 }else if (10 <= value && value < 100 ) {
					   face.valence = "活跃";
					 }
              
					 face.data.valence.push([nowTime, valence]);
					 console.log( face.data)
					
                    //删除过早数据
                    while(face.data.valence.length > 0 && face.data.valence[face.data.valence.length - 1][0] - face.data.valence[0][0] > that.totalTime){
                       
						face.data.valence.shift();
                    }
                    face.firstTime = face.data.valence[0][0];
                }
            }
        },
        /**
         * 更新chart报表
         */
        updateChartOption: function(){
            let that = this;
		console.log('-------now:' + common.getNowFormatDate());
            var newDay = new Date();
             
             that.myCharts.forEach(function(e,i){
			 if(i == that.myCharts.length){
				return;
			 }else{
			    that.faceImgs[i] = that.faceInfo[i].faceImg;
				that.faceVals[i] = that.faceInfo[i].valence;
                e.setOption({
                    xAxis: {
                        max : that.faceInfo[i].firstTime + that.totalTime,
                        min : that.faceInfo[i].firstTime
                    },
                    series: [{
                        data: that.faceInfo[i].data.valence
                    }]
                });
				}
            })     
			
        },
        /**
	      * 开启切换摄像头的定时器，每10秒切换一次
	      */
	     setStatusInterval: function () {
	    	 let that =this;
            that.statusInterval = setInterval(function () {
            	//cameraShow  showStatus
            	that.swithCamera();
               
            }, camEmotion.switchCameraInterval);
	     },
		 swithCamera:function(){
		 let that = this;
			that.cameraip = that.cameraList[that.cameraShow].cameraip;
               
               for (var int = 0; int < that.cameraList.length; int++) {
            	   if(that.cameraList[int].cameraip == that.cameraip){
            		   that.cameraList[int].showStatus = true;
   				}else{
   					that.cameraList[int].showStatus = false;
   				}			
			}
               
               if(that.cameraShow == that.cameraList.length - 1){
					that.cameraShow = 0;
				}else{
					that.cameraShow += 1;
				}
               if(that.ws)
                that.ws.send("{'SendCode':5,'SendMessage':'"+ that.cameraip +"'}");
               
               that.CanvasClear();
               that.initData(6);
		 },
	     initData:function(number){
	    	 var that = this;
             that.faceInfo = [];

			that.initFaceInfo(number,that.faceInfo);

            that.getSetOption1 = {//折线图
                grid: {
                    top: '5pt',
                    left: 0,
                    width: '95%',
                    height: '95%',
                    fontSize:9,
                    containLabel: true
                },
                color: ['#0ff'],

                tooltip: {
                    trigger: 'axis',
                    formatter: function (params) {
                    },
                    axisPointer: {
                        animation: false
                    }
                },
                xAxis: {
                    type: 'value',
                    position:'bottom',
                    splitLine: {
                        show: false
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#E0FFFF',
                            width:1,
                        }
                    }

                },
                yAxis: {
                    type: 'value',
                    boundaryGap: [0, '100%'],
                    splitLine: {
                        show: false
                    },
                    axisLine:{
                        lineStyle:{
                            color:'#E0FFFF',
                            width:1,
                        },
                    },
                    max: 100,
                    min: -100,
                    interval:50
                },
                series: [
                    {
                        name: 'Valence',
                        type: 'line',
                        showSymbol: false,
                        hoverAnimation: false,
                        animation: false,
                        data: []
                    }
                ]
            }
	    	        
	    	that.setInitEcharts(number);         
	     }
    }
});

var IframeOnClick = {
		resolution: 200,
		iframes: [],
		interval: null,
		Iframe: function() {
			this.element = arguments[0];
			this.cb = arguments[1]; 
			this.hasTracked = false;
		},
		track: function(element, cb) {
			this.iframes.push(new this.Iframe(element, cb));
			if (!this.interval) {
				var _this = this;
				this.interval = setInterval(function() { _this.checkClick(); }, this.resolution);
			}
		},
		checkClick: function() {
			if (document.activeElement) {
				var activeElement = document.activeElement;
				for (var i in this.iframes) {
					if (activeElement === this.iframes[i].element) { // user is in this Iframe
						if (this.iframes[i].hasTracked == false) { 
							this.iframes[i].cb.apply(window, []); 
							this.iframes[i].hasTracked = true;
						}
					} else {
						this.iframes[i].hasTracked = false;
					}
				}
			}
		}
	};
	IframeOnClick.track(document.getElementById("iframebar"), function() { 
		$("#videoCanvas").css("display","block"); 
		});
