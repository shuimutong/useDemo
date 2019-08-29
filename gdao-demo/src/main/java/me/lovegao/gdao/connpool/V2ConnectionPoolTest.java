package me.lovegao.gdao.connpool;

import java.sql.Connection;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.lovegao.gdao.bean.SystemConstant;
import me.lovegao.gdao.connection.IConnectionPool;
import me.lovegao.gdao.connection.SimpleV2ConnectionPool;

public class V2ConnectionPoolTest {
	private final static Logger log = LoggerFactory.getLogger(V2ConnectionPoolTest.class);

	public static void main(String[] args) throws Exception {
		String dbPath = "mysql2.properties";
		log.info("hello-----------------");
		log.warn("hello-----------------");
		periodCheckConnection(dbPath);
	}
	
	/**
	 * 连接泄露检测
	 * @param dbPath
	 * @throws Exception
	 */
	public static void checkConnectionLeak(String dbPath) throws Exception {
		Properties dbProp = CommonUtil.loadProp(dbPath);
		dbProp.setProperty(SystemConstant.STR_PERIOD_CHECK_CONNECTION_TIME_MIN, "10");
		dbProp.setProperty(SystemConstant.STR_CONNECTION_LEAK_CHECK, "true");
		IConnectionPool connPool = new SimpleV2ConnectionPool(dbProp);
		Connection conn = connPool.getConnection();
		Thread.sleep(240000);
		connPool.returnConnection(conn);
		Thread.sleep(61000);
		connPool.closeConnectionPool();
	}
	
	/**
	 * 连接定时检测
	 * @param dbPath
	 * @throws Exception
	 */
	public static void periodCheckConnection(String dbPath) throws Exception {
		Properties dbProp = CommonUtil.loadProp(dbPath);
		dbProp.setProperty(SystemConstant.STR_PERIOD_CHECK_CONNECTION_TIME_MIN, "1");
		IConnectionPool connPool = new SimpleV2ConnectionPool(dbProp);
		Thread.sleep(120000);
		Connection conn = connPool.getConnection();
		connPool.returnConnection(conn);
		Thread.sleep(2061000);
//		connPool.closeConnectionPool();
	}
	
	/**
	 * 归还连接检测
	 * @param dbPath
	 * @throws Exception
	 */
	public static void checkWhenReturn(String dbPath) throws Exception {
		Properties dbProp = CommonUtil.loadProp(dbPath);
		IConnectionPool connPool = new SimpleV2ConnectionPool(dbProp);
		Connection conn = connPool.getConnection();
		conn.close();
		connPool.returnConnection(conn);
		Thread.sleep(20000);
		connPool.closeConnectionPool();
	}

}
