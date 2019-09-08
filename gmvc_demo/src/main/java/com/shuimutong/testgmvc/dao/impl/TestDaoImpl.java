package com.shuimutong.testgmvc.dao.impl;

import com.shuimutong.gmvc.annotation.XRepository;
import com.shuimutong.testgmvc.bean.Person;
import com.shuimutong.testgmvc.dao.TestDao;

@XRepository
public class TestDaoImpl implements TestDao {

	@Override
	public Person findPerson() {
		return new Person();
	}

}
