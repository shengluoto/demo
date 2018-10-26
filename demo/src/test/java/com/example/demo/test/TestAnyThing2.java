package com.example.demo.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

public class TestAnyThing2 {
    @Test
    public void test13() {
	String host = "0.tcp.ngrok.io";
	int port = 10666;
	String msg = ">>cssp,0,SN0301201809180001,0,heartb,none,1,0,05\n";
	// String host = "10.10.58.242";
	// int port = 8082;
	try {
	    while(true) {
		System.out.println(LocalDate.now().toString() + LocalTime.now().toString() + " 开始发送： ");
		Socket client = new Socket(host, port);
		OutputStream outToServer = client.getOutputStream();
		DataOutputStream out = new DataOutputStream(outToServer);
		byte[] bstream = msg.getBytes("GBK");
		out.write(bstream);
		InputStream inFromServer = client.getInputStream();
		//解析服务器返回的数据
		InputStreamReader reader = new InputStreamReader(inFromServer);
		BufferedReader bufReader = new BufferedReader(reader);
        String s = null;
        StringBuffer sb = new StringBuffer();
        while ((s = bufReader.readLine()) != null) {
            sb.append(s);
        }
		System.out.println(LocalDate.now().toString() + LocalTime.now().toString() + "服务器响应： " +sb.toString());
		client.close();
		Thread.sleep(1000 * 300);
	    }
	}catch (UnknownHostException e) {
	    e.printStackTrace();
	}catch (IOException e) {
	    e.printStackTrace();
	}catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
