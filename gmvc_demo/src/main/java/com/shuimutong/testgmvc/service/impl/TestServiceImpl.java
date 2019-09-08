package com.shuimutong.testgmvc.service.impl;

import com.shuimutong.gmvc.annotation.XAutowired;
import com.shuimutong.gmvc.annotation.XService;
import com.shuimutong.testgmvc.service.Test2Service;
import com.shuimutong.testgmvc.service.TestService;

@XService
public class TestServiceImpl implements TestService {
	@XAutowired
	private Test2Service test2Service;

	@Override
	public void speak() {
		test2Service.speak();
		System.out.println("----TestServiceImpl-----speak--");
	}

	@Override
	public String convertString(String s) {
		return s + "TestServiceImpl";
	}

}
