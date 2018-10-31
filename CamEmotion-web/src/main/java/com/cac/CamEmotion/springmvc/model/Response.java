package com.cac.CamEmotion.springmvc.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * 类说明: 通用响应数据结果<br>
 * 
 * <P>
 *     通用response响应数据结构:<br>
 *     {<br>
 *     	"meta": {<br>
 *     		"success": true,<br>
 *     		"message": "ok"<br>
 *     	},<br>
 *     	"data": ...<br>
 *     }<br>
 *     data为T,即响应的业务数据对象<br>
 * </P>
 *
 * @author zhangsh
 * @data 2017年1月17日
 */
public class Response<T> {
	private static Logger logger = LogManager.getLogger(Response.class);
	
	/* 操作处理成功的消息定义 */
	private static final String OK = "ok";  
	
	/* 操作处理失败的消息定义 */
    private static final String ERROR = "error"; 
    
    /* 响应元数据 */
    private Meta meta;
    
    /* 业务响应数据 */
    private T data;
    
    /**
     * 
     * 方法说明: 默认成功响应
     * <P>
     *     
     * </P>
     * 
     * @author zhangsh
     * @date 2017年1月17日
     * @return
     */
    public Response<T> success() {  
        this.meta = new Meta(true, OK);  
        return this;  
    }  
  
    /**
     * 
     * 方法说明: 带业务数据的成功响应
     * <P>
     *     
     * </P>
     * 
     * @author zhangsh
     * @date 2017年1月17日
     * @param data 业务数据
     * @return
     */
    public Response<T> success(T data) {  
        this.meta = new Meta(true, OK);  
        this.data = data;  
        return this;  
    }  
  
    /**
     * 
     * 
     * 方法说明: 默认失败响应
     * <P>
     *     
     * </P>
     * 
     * @author zhangsh
     * @date 2017年1月17日
     * @return
     */
    @Deprecated
    public Response<T> failure() {  
        this.meta = new Meta(false, ERROR);  
        return this;  
    }  
  
    /**
     * 
     * 方法说明: 指定错误码和错误消息的统一错误定义
     * <P>
     *     
     * </P>
     *
     * @author zhangsh
     * @data 2017年4月21日
     * @param retCode 错误码
     * @param message 错误描述
     * @return
     */
    public Response<T> failure(String retCode, String message) {
        this.meta = new Meta(false, retCode, message);  
        logger.warn("返回状态: false, 返回码: " + retCode + ", 提示消息: " + message);
        return this;  
    }  
    
    /**
     * 方法说明: 指定错误码和错误消息的统一错误定义  并带上业务数据
     * <P>
     *     
     * </P>
     *
     * @author yudongyang
     * @data 2017年6月16日
     * @param retCode 错误码
     * @param message 错误描述
     * @param data 业务数据
     * @return
     */
    public Response<T> failure(String retCode, String message,T data) {
        this.meta = new Meta(false, retCode, message);  
        this.data = data;
        logger.warn("返回状态: false, 返回码: " + retCode + ", 提示消息: " + message);
        return this;  
    }  
    public Meta getMeta() {  
        return meta;  
    }  
  
    public T getData() {  
        return data;  
    }  
  
    
    /**
     * 
     * 
     * 类说明: 响应元数据
     * <P>
     *     用于响应客户端的请求通用请求，每个处理的请求都必定包含这个元素数据
     * </P>
     * <P>
     * 	响应定义
     * </P>
     * <ol>
     * 	<li>处理成功时返回{success:true,...}</li>
     * 	<li>处理失败返回{success:false,...}</li>
     * 	<li>元数据中的message是否选的，一般用于处理失败时返回错误提示</li>
     * </ol>
     *
     * @author zhangsh
     * @data 2017年1月17日
     */
    public class Meta {
    	// 状态
        private boolean success;
        
        // 错误码
        private String retCode = "0";
        
        // 处理说明
        private String message;
  
        public Meta(boolean success) {
            this.success = success;  
        }  
  
        public Meta(boolean success, String message) {
            this.success = success;  
            this.message = message;  
        } 
        
        public Meta(boolean success, String retCode, String message) {
            this.success = success;  
            this.retCode = retCode;
            this.message = message;  
        }  
  
        public boolean isSuccess() {
            return success;  
        }  
        
        public String getRetCode() {
        	return retCode;
        }  
  
        public String getMessage() {
            return message;  
        }
    }  
}
