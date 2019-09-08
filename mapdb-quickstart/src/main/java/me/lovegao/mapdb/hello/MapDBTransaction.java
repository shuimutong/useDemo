package me.lovegao.mapdb.hello;

import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class MapDBTransaction {

	public static void main(String[] args) {
		DB db = DBMaker
		        .fileDB("file3.db")
		        .fileMmapEnable()
//		        .transactionEnable()
//		        .closeOnJvmShutdown()
		        .make();
		ConcurrentMap<String,Long> map = db
		        .hashMap("mapsl3", Serializer.STRING, Serializer.LONG)
		        .createOrOpen();
		map.put("a", 1L);
		map.put("b", 2L);
		db.commit();
		System.out.println(map.get("a"));
		System.out.println(map.get("b"));
		map.put("c", 3L);
		System.out.println("rollback之前，c:" + map.get("c"));
		db.rollback();
		System.out.println("rollback之后，a:" + map.get("a"));
		System.out.println("rollback之后，c:" + map.get("c"));
	}

}
