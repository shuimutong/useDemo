package me.lovegao.gdao.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSONObject;

import me.lovegao.gdao.dao.UserDao;
import me.lovegao.gdao.dos.UserDo;
import me.lovegao.gdao.instancemanager.DaoResourceManager;
import me.lovegao.gdao.sqlexecute.ISqlExecutor;

/**
 * Hello world!
 *
 */
public class DaoTest {
	public static int THREAD_NUM = 5;
	public static ExecutorService ES = Executors.newFixedThreadPool(THREAD_NUM);
	public static void main(String[] args) throws Exception {
		ISqlExecutor sqlExecutor = initDao();
		addTest(sqlExecutor);
//		batchAddTest(sqlExecutor);
//		UserDao userDao = new UserDao(sqlExecutor);
//		queryUpdateDel(userDao);
//		batchTest(userDao);
	}
	
	public static void batchTest(UserDao userDao) throws Exception {
		List<Future<Long>> fList = new ArrayList();
		long t1 = System.currentTimeMillis();
		for(int i=0; i<THREAD_NUM; i++) {
			Future<Long> future = ES.submit(new Callable<Long>() {
				@Override
				public Long call() throws Exception {
					UserDo[] uds = listUser(Thread.currentThread().getName());
					long t1 = System.currentTimeMillis();
//					userDao.addBatch(Arrays.asList(uds));
					for(int i=1; i<uds.length; i++) {
						userDao.add(uds[i]);
					}
					long t2 = System.currentTimeMillis();
					return t2 - t1;
				}
			});
			fList.add(future);
		}
		for(Future<Long> f : fList) {
			Long useTime = f.get();
			System.out.println("useTime:" + useTime + "ms");
		}
		long t2 = System.currentTimeMillis();
		ES.shutdown();
		System.out.println("totalUseTime:" + (t2-t1) + "ms");
		//1000条
//		useTime:2508ms
//		useTime:2498ms
//		useTime:2501ms
//		useTime:2489ms
//		totalUseTime:2512ms
		
		//2000条
//		useTime:4473ms
//		useTime:4467ms
//		useTime:4488ms
//		useTime:4487ms
//		totalUseTime:4493ms
		
		//6c24g-2000
//		useTime:2508ms
//		useTime:2510ms
//		useTime:2501ms
//		useTime:2510ms
//		totalUseTime:2513ms
		//sameAsUp-6000
//		useTime:7158ms
//		useTime:7138ms
//		useTime:7173ms
//		useTime:7152ms
//		useTime:7152ms
//		totalUseTime:7182ms
	}
	
	public static void queryUpdateDel(UserDao userDao) throws Exception {
		long id = 5013L;
		UserDo user = userDao.queryByPK(id);
		System.out.println("查询---");
		System.out.println(JSONObject.toJSONString(user));
		
		user.setAge(user.getAge() + 1);
		userDao.update(user);
		user = userDao.queryByPK(id);
		System.out.println("更新之后---查询--(age+1)-");
		System.out.println(JSONObject.toJSONString(user));
		
		String sql = "select id,age,name from t_user where id>? limit 10";
		Object[] replaceValues = new Object[] {5000};
		List<UserDo> list = userDao.list(sql, replaceValues);
		System.out.println("listSize:" + list.size());
		System.out.println("list:" + JSONObject.toJSONString(list));
		
		List<Object[]> objList = userDao.normalList(sql, replaceValues);
		System.out.println("objListSize:" + objList.size());
		System.out.println("objList:" + JSONObject.toJSONString(objList));
		
		long delId = list.get(0).getId();
		userDao.deleteByPK(delId);
		user = userDao.queryByPK(delId);
		System.out.println("删除之后查询，user:" + user);
	}
	
	public static void batchAddTest(ISqlExecutor sqlExecutor) throws Exception {
		UserDao userDao = new UserDao(sqlExecutor);
		UserDo[] uds = listUser();
		List<UserDo> list = Arrays.asList(uds);
		long t1 = System.currentTimeMillis();
		userDao.addBatch(list);
		long t2 = System.currentTimeMillis();
		double useTime = t2 - t1;
		//1000条，346ms
		System.out.println("totalUseTime:" + useTime + "ms");
	}
	
	
	public static void addTest(ISqlExecutor sqlExecutor) throws Exception {
		UserDao userDao = new UserDao(sqlExecutor);
		UserDo[] uds = listUser();
		System.out.println("准备开始添加");
		userDao.add(uds[0]);
		System.out.println("开始添加---");
		long t1 = System.currentTimeMillis();
		for(int i=1; i<uds.length; i++) {
			userDao.add(uds[i]);
		}
		long t2 = System.currentTimeMillis();
		double useTime = t2 - t1;
		//5000条，
		//mac 2c8g totalUseTime:8629.0ms,avgUseTime:1.7261452290458092ms
		//win10 6c24 totalUseTime:5153.0ms,avgUseTime:1.0308061612322466ms
		System.out.println("totalUseTime:" + useTime + "ms,avgUseTime:" + useTime/(uds.length-1) + "ms");
	}
	
	public static UserDo[] listUser(String sign) {
		int size = 2000;
		UserDo[] uds = new UserDo[size];
		for(int i=0; i<size; i++) {
			UserDo user = new UserDo();
			user.setAge((i+2) % 90 + 1);
			user.setName(sign + "大家好Name-" + i);
			uds[i] = user;
		}
		return uds;
	}
	
	public static UserDo[] listUser() {
		int size = 5000;
		UserDo[] uds = new UserDo[size];
		for(int i=0; i<size; i++) {
			UserDo user = new UserDo();
			user.setAge((i+2) % 90 + 1);
			user.setName("BatchName-" + i);
			uds[i] = user;
		}
		return uds;
	}

	public static ISqlExecutor initDao() throws Exception {
		Properties prop = new Properties();
		String confPath = "/mysql.properties";
		prop.load(DaoTest.class.getResourceAsStream(confPath));
		DaoResourceManager daoResource = new DaoResourceManager(prop);
		ISqlExecutor sqlExecutor = daoResource.getSqlExecutor();
		return sqlExecutor;
	}
}
