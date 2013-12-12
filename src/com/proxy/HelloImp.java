package com.proxy;

public class HelloImp implements Hello {

	@Override
	public void sayHello(String name) {
		// TODO Auto-generated method stub
		System.out.println("hello "+name);
	}

	@Override
	public void sayHello(String name, String title) {
		// TODO Auto-generated method stub
		System.out.println("hello "+name+" "+title);
	}

}
