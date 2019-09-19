package com.shuimutong.log4j.use1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4JTest1 {
	private final static Logger log = LoggerFactory.getLogger(Log4JTest1.class);
	private final static Logger logTest = LoggerFactory.getLogger("mytest1");

	public static void main(String[] args) {
		log.debug("log-----debug-----");
		log.error("log-----error-----");
		
		logTest.info("logTest-----info-----");
	}
	
}
