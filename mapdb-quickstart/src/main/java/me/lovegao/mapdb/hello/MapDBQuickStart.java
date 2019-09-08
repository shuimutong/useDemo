package me.lovegao.mapdb.hello;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class MapDBQuickStart {
	private final static String DEMO_KEY = "Hello";
	private final static String DEMO_VAL = "simple";

	public static void main(String[] args) {
		System.out.println("--Hello,simple----");
		fileMapMemoryMapTest();
	}
	
	/**
	 * 对外内存map
	 */
	public static void offHeapMapTest1() {
		DB db = DBMaker.memoryDB().make();
		ConcurrentMap map = db.hashMap("map").createOrOpen();
		map.put(DEMO_KEY, DEMO_VAL);
		System.out.println("第1次取值，" + map.get(DEMO_KEY));
	}
	
	/**
	 * 基于磁盘的存储
	 */
	public static void fileMapTest1() {
		DB db = DBMaker.fileDB("file.db").make();
		ConcurrentMap map = db.hashMap("map").createOrOpen();
		
		map.put(DEMO_KEY, DEMO_VAL);
		System.out.println("第1次取值，" +map.get(DEMO_KEY));
		db.close();
		System.out.println("----------重新打开----------");
		db = DBMaker.fileDB("file.db").make();
		map = db.hashMap("map").createOrOpen();
		System.out.println("第2次取值，" +map.get(DEMO_KEY));
		db.close();
	}
	
	/**
	 * 在64位操作系统中，开启内存映射
	 * 个性化序列化
	 */
	public static void fileMapMemoryMapTest() {
		DB db = DBMaker
		        .fileDB("file.db")
		        .fileMmapEnable()
		        .make();
		ConcurrentMap<String,Long> map = db
		        .hashMap("mapsl", Serializer.STRING, Serializer.LONG)
		        .createOrOpen();
		long val = 51;
		map.put(DEMO_KEY, val);
		System.out.println("第1次取值，期望值：" + val + "，取到的值：" +map.get(DEMO_KEY));
		db.close();
		db = DBMaker
		        .fileDB("file.db")
		        .fileMmapEnable()
		        .make();
		 map = db.hashMap("mapsl", Serializer.STRING, Serializer.LONG)
			      .createOrOpen();
		System.out.println("第2次取值，期望值：" + val + "，取到的值：" +map.get(DEMO_KEY));
		db.close();
	}

}
