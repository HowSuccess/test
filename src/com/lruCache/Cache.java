package com.lruCache;

public interface Cache<K extends Comparable, V> {
	   V get(K obj);  //查询
	   void put(K key, V obj); //插入和更新
	   void put(K key, V obj, long validTime);
	   void remove(K key); //删除
	   Pair[] getAll();
	   int size();
	}
