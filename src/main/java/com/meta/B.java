package com.meta;

class A {
	public static void prt() {
		System.err.println("123");
	}

	public A() {
		System.err.println("A");
	}
}

public class B extends A {
	public static void prt() {
		System.err.println("456");
	}
	
	public B() {
		System.err.println("B");
	}
	
	public static void main(String[] args) {
		A a = new B();
		a = new A();
	}
}
