package me.lovegao.gdao.demo;

import java.lang.reflect.ParameterizedType;

public class ParentDemo<T> {
	private Class<T> entityClass;
	
	protected ParentDemo() {
		entityClass = (Class<T>) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
}
