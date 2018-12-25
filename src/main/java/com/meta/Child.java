package com.meta;

class Parent {

	protected String value = "123";

	public String getValue() {
		return value;
	}
}

public class Child extends Parent {

	protected String value = "456";

	public static void main(String[] args) {
		Parent a = new Child();
		System.err.println(a.getValue());
		a.value = "789";
		System.err.println(a.getValue());
		Child b = new Child();
		System.err.println(b.getValue());
		b.value = "000";
		System.err.println(b.getValue());
	}
}
