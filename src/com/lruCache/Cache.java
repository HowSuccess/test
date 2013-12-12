package com.lruCache;

public interface Cache<K extends Comparable, V> {
	   V get(K obj);  //��ѯ
	   void put(K key, V obj); //����͸���
	   void put(K key, V obj, long validTime);
	   void remove(K key); //ɾ��
	   Pair[] getAll();
	   int size();
	}
