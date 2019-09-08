package com.shuimutong.testgmvc.bean;

public class Person {
	private int age;
	private String name;
	
	public Person() {
		this.age = (int) (Math.random() * 100);
		this.name = "name" + age;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
