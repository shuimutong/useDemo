package me.lovegao.gdao.test;

import java.io.IOException;
import java.util.Properties;

import me.lovegao.gdao.demo.DaoTest;

public class PropTest {

	public static void main(String[] args) throws IOException {
		loadProp();
	}
	
	public static void loadProp() throws IOException {
		Properties prop = new Properties();
		String confPath = "/mysql.properties";
		prop.load(PropTest.class.getResourceAsStream(confPath));
		System.out.println(prop.getProperty("validationQuery"));
	}

}
