/**
 * Created by houpp on 2018/1/5.
 */
/**
 * 本地工程
 * @type {{post: requsestSer.post, get: requsestSer.get}}
 */
var serRequest = axios.create({

    // `timeout`选项定义了请求发出的延迟毫秒数
    // 如果请求花费的时间超过延迟的时间，那么请求会被终止
    timeout: 60000,

    // 选项是需要被发送的自定义请求头信息
    headers: {
        'Content-Type': 'application/json'
    },

    // 返回数据的格式
    // 其可选项是arraybuffer,blob,document,json,text,stream
    responseType: 'json'// default
});

/**
 * 对应国泰跨域请求
 */
var GTRequest = axios.create({

    // `timeout`选项定义了请求发出的延迟毫秒数
    // 如果请求花费的时间超过延迟的时间，那么请求会被终止
    timeout: 20000,

    // 选项是需要被发送的自定义请求头信息
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    },

    // 返回数据的格式
    // 其可选项是arraybuffer,blob,document,json,text,stream
    responseType: 'json'// default
});

/**
 * 对应文件上传
 */
var FileRequest = axios.create({

    // `timeout`选项定义了请求发出的延迟毫秒数
    // 如果请求花费的时间超过延迟的时间，那么请求会被终止
    timeout: 20000,

    // 选项是需要被发送的自定义请求头信息
    headers: {
        'Content-Type': 'multipart/form-data'
    },

    // 返回数据的格式
    // 其可选项是arraybuffer,blob,document,json,text,stream
    responseType: 'json',// default
});

var requsestSer = {
    /**
     * post
     * @param url
     * @param parameter
     * @returns {Promise}
     */
    post: function (url, parameter) {
        return new Promise(function (resolve, reject) {
            serRequest.post(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    },
    /**
     * post
     * @param url
     * @param parameter
     * @returns {Promise}
     */
    posts: function (url, parameter, outTime) {
        return new Promise(function (resolve, reject) {
        	serRequest.timeout = outTime;
            serRequest.post(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    },
    /**
     * get
     * @param url
     * @param parameter
     * @returns {Promise}
     */
    get: function (url, parameter) {
        return new Promise(function (resolve, reject) {
            serRequest.get(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    }
};

/**
 * 跨域请求
 * @type {{post: crossRequsestSer.post, get: crossRequsestSer.get}}
 */
var crossRequsestSer = {
    /**
     * post
     * @param url
     * @param parameter
     */
    post: function (url, parameter) {
        return new Promise(function (resolve, reject) {
            GTRequest.post(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    },

    /**
     * get
     * @param url
     * @param parameter
     */
    get: function (url, parameter) {
        return new Promise(function (resolve, reject) {
            GTRequest.get(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    }
};

/**
 * 跨域请求
 * @type {{post: crossRequsestSer.post, get: crossRequsestSer.get}}
 */
var fileRequsestSer = {
    /**
     * post
     * @param url
     * @param parameter
     */
    post: function (url, parameter) {
        return new Promise(function (resolve, reject) {
            FileRequest.post(url, parameter).then(function (data) {
                resolve(data.data);
            }).catch(function (data) {
                reject(data.data);
            })
        });
    },

    /**
     * 上传 并设置进度
     * @param url
     * @param parameter 参数
     * @param callback1 上传进度
     * @param callback2 完成回调
     */
    uploadPhoto: function (url, parameter, callback1, callback2,callback3) {
        axios({
            url: url,
            method: 'post',
            headers: {
                'Content-Type': 'multipart/form-data'
            },
            onUploadProgress: function (progressEvent) { //原生获取上传进度的事件
                if (progressEvent.lengthComputable) {
                    //属性lengthComputable主要表明总共需要完成的工作量和已经完成的工作是否可以被测量
                    //如果lengthComputable为false，就获取不到progressEvent.total和progressEvent.loaded
                    callback1(progressEvent);
                }
            },
            data: parameter
        }).then(function (res) {
            callback2(res.data);
        }).catch(function(res){
            callback3(res.data);
        })
    }
};

