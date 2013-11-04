package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {
	private static ServerSocketChannel ssc  = null;
	private static Selector s = null;
	private static int port = 2222;
	private static String serverDiff = "server : ";
	private static String tmpStr = null;
	public static void main(String[] args) throws IOException{
		s = Selector.open();
		ssc  = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.socket().bind(new InetSocketAddress(port));
		ssc.register(s, SelectionKey.OP_ACCEPT);
		
		while(true){
			int n = s.select();
			if(n>0){
				Iterator<SelectionKey> it = s.selectedKeys().iterator();
				while(it.hasNext()){
					SelectionKey key = it.next();
					it.remove();
					if(key.isAcceptable()){
					System.out.println("key is acceptable *");	
					ServerSocketChannel tmpsc = (ServerSocketChannel) key.channel();
					SocketChannel sc = tmpsc.accept();
					sc.configureBlocking(false);
					sc.register(s, SelectionKey.OP_READ);
					}else if(key.isReadable()){
						System.out.println("key is readable *");	
						SocketChannel tmpsc = (SocketChannel) key.channel();
						tmpsc.configureBlocking(false);
						ByteBuffer dst = ByteBuffer.allocate(10);
						int count = tmpsc.read(dst);
							while(count>0){
								dst.flip();
								tmpStr+=new String(dst.array(),0,count);
//								dst.compact();
								dst.clear();
								count = tmpsc.read(dst);
							}
							System.out.println(tmpStr);
						tmpsc.register(s, SelectionKey.OP_WRITE);
						
					}else if (key.isWritable()){
						SocketChannel tmpsc = (SocketChannel) key.channel();
						tmpsc.configureBlocking(false);
						ByteBuffer dst = ByteBuffer.allocate(1024);
						dst.put((serverDiff+tmpStr).getBytes());
						dst.flip();
						tmpsc.write(dst);
						System.out.println(" tmpsc.write(dst); key is writable *");
						tmpsc.register(s, SelectionKey.OP_READ);
					}else if(key.isWritable()){
						
					}
				}
			}
		}
		
	}
}
