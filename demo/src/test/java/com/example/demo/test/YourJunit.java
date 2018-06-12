package com.example.demo.test;

import java.util.Random;

public class YourJunit extends Thread{  
    public void run(){  
        //开10个线程，模拟10个用户  
        for(int i = 0;i < 1; i++){  
            Thread thread = new YourThreadTest();  
            thread.setName(" user NO."+(i+1));  
            thread.start();  
//            try {  
//                //线程休眠时间1-5秒，每间隔1-5秒开启一个线程，模拟一个用户进行访问  
//                Thread.sleep(1000*(new Random().nextInt(5)+1));  
//            } catch (InterruptedException e) {  
//                e.printStackTrace();  
//            }  
            System.out.println(thread.getName());
        }  
    }  
    public static void main(String[] args){  
    	long startTime = System.currentTimeMillis(); 
        new YourJunit().start();  
        System.out.println("结束");
    }  
}  
