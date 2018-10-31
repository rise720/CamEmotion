$(function () {
    vipspa.start({
        view: '#ui-views',
        router: {
            '/Users': { // 用户信息
                templateUrl: './modules/Users/views/users.html?bust=' + (new Date()).getTime(),
                controller: ['./modules/Users/controllers/users.js']
            },
            '/Setup': { // 用户信息
                templateUrl: './modules/Setup/views/Setup.html?bust=' + (new Date()).getTime(),
                controller: ['./modules/Setup/controllers/Setup.js']
            },
            '/ClassTable': { // 课程列表
                templateUrl: 'modules/Class/views/ClassTable.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/ClassTable.js']
            },
            '/ClassVideo': { // 课程视频
                templateUrl: 'modules/Class/views/ClassVideo.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/ClassVideo.js']
            },
            '/ClassStudentsList': { // 学生分析
                templateUrl: 'modules/Class/views/ClassStudentsList.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/ClassStudentsList.js']
            },
            '/ClassStudentVideo': { // 学生分析-详细
                templateUrl: 'modules/Class/views/ClassStudentVideo.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/ClassStudentVideo.js']
            },
            '/OpenClass': { // 课堂采集
                templateUrl: 'modules/OpenClass/views/OpenClass.html?bust=' + (new Date()).getTime(),
                controller: ['modules/OpenClass/controllers/OpenClass.js']
            },

            '/ContrastTransverse': { // 对比-横向
                templateUrl: 'modules/Contrast/views/ContrastTransverse.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Contrast/controllers/ContrastTransverse.js']
            },
            '/ContrastLongitudinal': { // 对比-纵向
                templateUrl: 'modules/Contrast/views/ContrastLongitudinal.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Contrast/controllers/ContrastLongitudinal.js']
            },
            '/AllSelect':{  // 综合查询
                templateUrl: 'modules/Contrast/views/AllSelect.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Contrast/controllers/AllSelect.js']
            },
            '/Teacherlist': { // 教师列表
                templateUrl: 'modules/Teacherlist/views/Teacherlist.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Teacherlist/controllers/Teacherlist.js']
            },
            '/ClassComment': { // 课程评论
                templateUrl: 'modules/Class/views/ClassComment.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/ClassComment.js']
            },
            '/school': { // 学校管理
                templateUrl: 'modules/School/views/school.html?bust=' + (new Date()).getTime(),
                controller: ['modules/School/controllers/school.js']
            },
            '/LiveVideo': { // 视频直播
                templateUrl: 'modules/LiveVideo/views/LiveVideo.html?bust=' + (new Date()).getTime(),
                controller: ['modules/LiveVideo/controllers/LiveVideo.js']
            },
            '/LiveVideoByBrowser': { // 视频直播
                templateUrl: 'modules/LiveVideo/views/LiveVideoByBrowser.html?bust=' + (new Date()).getTime(),
                controller: ['modules/LiveVideo/controllers/LiveVideoByBrowser.js']
            },
            '/SpectatorAnalyze': { // 建行分析
                templateUrl: 'modules/Class/views/SpectatorAnalyze.html?bust=' + (new Date()).getTime(),
                controller: ['modules/Class/controllers/SpectatorAnalyze.js']
            },
            '/moreCharts':{
                templateUrl: 'modules/demoChart/moreChart.html?bust=' + (new Date()).getTime(),
                controller: ['modules/demoChart/moreChart.js']
            },
            // 'rankings':{

            // }
           
            //默认路由
            'defaults': '/ClassTable'
        },
        //可选的错误模板，用来处理加载html模块异常时展示错误内容
        errorTemplateId: '#error'
    });
});
