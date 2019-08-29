package me.lovegao.gdao.connpool;

import java.util.Properties;

public class CommonUtil {
	public static Properties loadProp(String propName) throws Exception {
		Properties prop = new Properties();
		prop.load(CommonUtil.class.getResourceAsStream("/" + propName));
		return prop;
	}
}
