package com.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Client {
	private static SocketChannel sc = null;
	private static int port = 2222;
	public static void main(String[] args) throws IOException{
		sc  = SocketChannel.open();
		sc.configureBlocking(false);
		Selector s = Selector.open();
		sc.register(s, SelectionKey.OP_CONNECT);
		sc.connect(new InetSocketAddress("localhost",port));
		ByteBuffer dst = ByteBuffer.allocate(1024);
	
		while(true){
			int n = s.select();
			if(n>0){
				System.out.println(" clinent send message ");
				Iterator<SelectionKey> it = s.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey skey =  it.next();
					it.remove();
					if(skey.isConnectable()){
						System.out.println(" is connected ");
						SocketChannel sclient = (SocketChannel) skey.channel();
						sclient.configureBlocking(false);
						System.out.println(sclient.isConnected()+" "+sclient.isConnectionPending());
						if(sclient.isConnectionPending()){
							sclient.finishConnect();
							dst.clear();
							dst.put("hallo server".getBytes());
							dst.flip();
							sclient.write(dst);
						}
						sclient.register(s, SelectionKey.OP_WRITE);
					}else if(skey.isReadable()){
						System.out.println("key is readable *");	
						SocketChannel tmpsc = (SocketChannel) skey.channel();
						tmpsc.configureBlocking(false);
						ByteBuffer dstt = ByteBuffer.allocate(10);
						int count = tmpsc.read(dstt);
						System.out.println("count is " +count);
							while(count>0){
								dst.flip();
								System.out.print(new String(dstt.array(),0,count));
//								dst.compact();
								dst.clear();
								count = tmpsc.read(dstt);
							}
							tmpsc.register(s, SelectionKey.OP_WRITE);
					}else if(skey.isWritable()){
						System.out.println(" is writable ");
						SocketChannel sclient = (SocketChannel) skey.channel();
						sclient.configureBlocking(false);
						dst.clear();
//						System.out.println(" write a letter start:");
						BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
//						System.out.println(" write a letter end :");
						String tmp = null;
						tmp = bf.readLine();
						dst.put(tmp.getBytes());
						dst.flip();
						sclient.write(dst);
						sclient.register(s, SelectionKey.OP_READ);
						
					}
				}
			}
		}
	}

}
