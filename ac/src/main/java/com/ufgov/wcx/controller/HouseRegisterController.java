package com.ufgov.wcx.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.ufgov.wcx.service.HouseRegisterService;
import com.util.ResultConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/housemanage/houseegister")
@Slf4j
public class HouseRegisterController {
	
	@Autowired
	private HouseRegisterService houseRegisterService;
	
	//查询
	@RequestMapping(value = "/select",method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> selectHouseRegister(HttpServletRequest request) {
		Map<Object, Object> responseMap = new HashMap<Object, Object>(16);
		try {
			//将前台的json字符串转为map格式的
			Map<String, Object> requestMap = (Map<String, Object>) JSON.parseObject(request.getParameter("info"));
			responseMap.put(ResultConstant.FLAG,ResultConstant.FLAG_SUCCESS);
			responseMap.put(ResultConstant.DATA, houseRegisterService.select(requestMap));
		} catch (Exception e) {
			responseMap.put(ResultConstant.FLAG, ResultConstant.FLAG_ERROR);
		}
		return responseMap;
	}
	
	//保存办公用房
	@RequestMapping(value = "/save",method = RequestMethod.GET)
	public @ResponseBody Map<Object, Object> saveHouseRegister(HttpServletRequest request) {
		Map<Object, Object> responseMap = new HashMap<Object, Object>(16);
		try {
			//将前台的json字符串转为map格式的
			Map<String, Object> requestMap = (Map<String, Object>) JSON.parseObject(request.getParameter("info"));
			//生成UUID,当主键
			requestMap.put("id", UUID.randomUUID().toString().replace("-", "").toLowerCase());
			houseRegisterService.save(requestMap);
			responseMap.put(ResultConstant.FLAG,ResultConstant.FLAG_SUCCESS);
		} catch (Exception e) {
			responseMap.put(ResultConstant.FLAG,ResultConstant.FLAG_ERROR);
		}
		return responseMap;
	}
}
