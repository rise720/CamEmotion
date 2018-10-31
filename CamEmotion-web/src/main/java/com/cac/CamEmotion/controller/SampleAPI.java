package com.cac.CamEmotion.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cac.CamEmotion.annotation.Token;
import com.cac.CamEmotion.common.Constants;
import com.cac.CamEmotion.jsonModel.Response;
import com.cac.CamEmotion.model.Sample;
import com.cac.CamEmotion.service.SampleService;

/**
 * 
 * 
 * 类说明: 示例代码
 * <P>
 * 
 * </P>
 *
 * @author zhangsh
 * @data 2017年1月18日
 */
@RestController // 表示本类的所有方法都是直接返回json
@RequestMapping("/rest/sample")
public class SampleAPI extends AbstractBaseAPI<Sample> {
	private static Logger logger = Logger.getLogger(Constants.SYSTEM_LOGGER);
	// private static Gson gson = new
	// GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	@Resource
	private SampleService sampleService;

	@Override
	@PostConstruct
	public void init() {
		super.baseService = sampleService;
	}

	/**
	 * 
	 * 
	 * 方法说明: 测试重复提交
	 * 
	 */
	@RequestMapping(value = "/TestDuplication/5456/{Token}", method = RequestMethod.GET)
	@Token(avoidDuplication = true, handler = Token.DEFAULT_PROCESS)
	public Response<Sample> TestDuplication(HttpServletRequest request) {
		System.out.println("121Test");

		logger.info("DUPLICATE_SUBMIT getParameter:" + request.getParameter(Constants.DUPLICATE_SUBMIT));
		return new Response<Sample>().success();
	}

	/**
	 * 
	 * 
	 * 方法说明: 测试获取Token
	 * <P>
	 */
	@RequestMapping(value = "/TestGetToken/5456", method = RequestMethod.GET)
	@Token(create = true)
	public Response<Sample> TestGetToken(HttpServletRequest request) {
		System.out.println("121Test");
		System.out.println(request.getAttribute("token"));
		return new Response<Sample>().success();
	}

	/**
	 * 
	 * 
	 * 方法说明: 获取Redis中保存的Token
	 * <P>
	 */
	@RequestMapping(value = "/TestGetRedisToken/5456", method = RequestMethod.GET)
	@Token(create = true)
	public Response<Sample> TestGetRedisToken(HttpServletRequest request) {
		System.out.println("121Test");
		return new Response<Sample>().success();
	}
}
