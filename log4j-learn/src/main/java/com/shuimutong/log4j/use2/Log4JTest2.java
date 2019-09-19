package com.shuimutong.log4j.use2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4JTest2 {
	private final static Logger log = LoggerFactory.getLogger(Log4JTest2.class);
	private final static Logger logTest = LoggerFactory.getLogger("mytest1");

	public static void main(String[] args) {
		log.debug("log--use2---debug-----");
		log.error("log--use2---error-----");
		
		logTest.info("logTest--use2---info-----");
	}
	
}
