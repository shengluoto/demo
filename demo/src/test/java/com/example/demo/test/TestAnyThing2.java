package com.example.demo.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class TestAnyThing2 {
	@Test
	public void test13(){
//		String host = "0.tcp.ngrok.io"; //		int port = 10666; 		
		String host = "10.10.58.242"; 		
		int port = 8082; 		
		try { 			
			while(true) { 				
				Socket client = new Socket(host, port); 				
				OutputStream outToServer = client.getOutputStream(); 				
				DataOutputStream out = new DataOutputStream(outToServer); 				 				
				out.writeUTF(">>cssp,0,SN0301201809180001,0,heartb,none,1,0,05\n"); 				
				InputStream inFromServer = client.getInputStream(); 				
				DataInputStream in = new DataInputStream(inFromServer); 				
				System.out.println("服务器响应： " + in.readUTF()); 				
				client.close(); 				
				Thread.sleep(1000*300); 			
				}	 		
		} catch (UnknownHostException e) { 			
			e.printStackTrace(); 		
		} catch (IOException e) { 			
			e.printStackTrace(); 		
		} catch (InterruptedException e) { 			
			// TODO Auto-generated catch block 			
			e.printStackTrace(); 		
		}
	}
}
