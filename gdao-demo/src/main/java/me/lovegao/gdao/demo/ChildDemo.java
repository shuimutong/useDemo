package me.lovegao.gdao.demo;

public class ChildDemo extends ParentDemo<Integer> {
	public static void main(String[] args) {
		ParentDemo p = new ChildDemo();
		System.out.println(p.getEntityClass());
	}
}
