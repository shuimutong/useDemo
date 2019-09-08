package com.shuimutong.testgmvc.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.shuimutong.gmvc.annotation.XAutowired;
import com.shuimutong.gmvc.annotation.XController;
import com.shuimutong.gmvc.annotation.XRequestMapping;
import com.shuimutong.gmvc.util.RequestResolveUtil;
import com.shuimutong.testgmvc.service.Test2Service;
import com.shuimutong.testgmvc.service.TestService;

@XController
@XRequestMapping("/test")
public class TestController {
	@XAutowired
	private Test2Service test2Service;
	@XAutowired
	private TestService testService;

    @XRequestMapping("/testA")
    public void testA(HttpServletRequest request, HttpServletResponse reponse) {
        System.out.println("Hi, this is TestA");
    }
    
    @XRequestMapping("/testB")
    public void testB(HttpServletRequest request, HttpServletResponse reponse) {
    	System.out.println("Hi, this is TestA");
    	JSONObject res = new JSONObject();
    	String tmpMsg = null;
    	Map<String, String[]> map = request.getParameterMap();
    	for(String k : map.keySet()) {
    		res.put(k, map.get(k));
    		if(tmpMsg == null) {
    			tmpMsg = map.get(k)[0];
    		}
    	}
    	System.out.println("----------testService.speak()------------");
    	testService.speak();
    	System.out.println("----------test2Service.convertString()------------");
    	String person = test2Service.convertString(tmpMsg);
    	res.put("person", person);
    	RequestResolveUtil.returnJson(request, reponse, res.toJSONString());
    }
}