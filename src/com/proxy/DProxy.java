package com.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.Map;
public class DProxy {
	public static void main(String[] args){
		Map a = new HashMap();
		a.get(new Object());
		Hello hello = (Hello) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[] {Hello.class}, new InvocationHandler(){
			  public Object invoke(Object proxy, Method method, Object[] args){
				  try {
					  proxy = new HelloImp();
					method.invoke(proxy, args);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return proxy;
				  
			  }
		});
		hello.sayHello("yg");
		hello.sayHello("yg", "presinet");
	}
}
