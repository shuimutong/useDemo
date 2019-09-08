package com.shuimutong.testgmvc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shuimutong.gmvc.annotation.XAutowired;
import com.shuimutong.gmvc.annotation.XService;
import com.shuimutong.testgmvc.bean.Person;
import com.shuimutong.testgmvc.dao.TestDao;
import com.shuimutong.testgmvc.service.Test2Service;

@XService
public class Test2ServiceImpl implements Test2Service {
	@XAutowired
	private TestDao testDao;

	@Override
	public void speak() {
		System.out.println("----Test2ServiceImpl-----speak----");
	}

	@Override
	public String convertString(String s) {
		Person p = testDao.findPerson();
		p.setName(p.getName() + s);
		return JSONObject.toJSONString(p);
	}

}
