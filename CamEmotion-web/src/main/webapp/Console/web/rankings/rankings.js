new Vue({
    el : '#moodRankingsApp',
    data:{

        pageModel1: {
            currentPage:'1',
            pageRecorders:'5',
            rule:'DESC',
			expression:'puzzledRate',
            context:''
        },
		 pageModel2: {
            currentPage:'1',
            pageRecorders:'5',
            rule:'DESC',
			expression:'excitementRate',
            context:''
        },
		pageModel3: {
            currentPage:'1',
            pageRecorders:'5',
            rule:'DESC',
			expression:'calmRate',
            context:''
        },
        moodList: [],
        puzzledRate: [], //思考
        calmRate: [],    //平静
        excitementRate: [], //活跃
       
	
    },
    watch: {
    
    },
    created() {
       
    },
    computed: {
    },
    mounted: function() {
        
        this.GetList1();
		this.GetList2();
        this.GetList3();


        
    },
    components: {
    },
    methods : {
	     //    GetList: function(){
    //        var that = this;
    //        try{
    //            requsestSer.post(`../${camEmotion.findComprehensiveReportList}`, this.pageModel).then(function(data){
          
    //             that.moodList = data.data.objList;
                 
    //             // 思考
    //             that.puzzledRate = that.moodList.sort(that.compare('puzzledRate'));
    //             // 平静
    //             that.calmRate = that.moodList.sort(that.compare('calmRate'));
    //             // 活跃
    //             that.excitementRate = that.moodList.sort(that.compare('excitementRate'));
            
    //            })
              
    //        }catch{
    //           bootAlert.alertDanger(e.message);
            
    //        }
       
    //    },
	  
       GetList1: function(){
           var that = this;
		  try{
               requsestSer.post(`../${camEmotion.findComprehensiveReportList}`, that.pageModel1).then(function(data){
               console.log(that.pageModel)
                
				that.puzzledRate = data.data.objList;
				
     
               })
              
           }catch(e){
              bootAlert.alertDanger(e.message);
           
           }
          
             
       },
	    GetList2: function(){
           var that = this;
		   
           try{
               requsestSer.post(`../${camEmotion.findComprehensiveReportList}`, that.pageModel2).then(function(data){
               console.log(that.pageModel)
                
				that.excitementRate = data.data.objList;
				
     
               })
              
           }catch(e){
              bootAlert.alertDanger(e.message);
           
           }
 
       },
	    GetList3: function(){
           var that = this;
		 
           try{
               requsestSer.post(`../${camEmotion.findComprehensiveReportList}`, that.pageModel3).then(function(data){
              
                console.log(that.pageModel)
				that.calmRate = data.data.objList;
				
     
               })
              
           }catch(e){
              bootAlert.alertDanger(e.message);
           
           }
 
       },
      
    
       compare:function compare(property){
        return function(a,b){
            var value1 =parseFloat(a[property]);
            var value2 =parseFloat(b[property]);
            return value2 - value1;
        }
    }
   

    }
});

