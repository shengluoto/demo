package com.example.demo.test;

import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestHttpPost {
    @Test
    public void testSplit() {
    	FileWriter fwriter = null;  
    	 try {  
    	  fwriter = new FileWriter("E:\\a.txt");  
    	  for (int i=0;i<100000; i++) {
    		  fwriter.write("set click_Scheme_"+"8a8aba7262193f0e016219489c0c0004"+ i +"_2018-03-19 "+ i + "\n");  
    	  }
    	  fwriter.flush();
    	  fwriter.close();
    	 } catch (IOException ex) {  
    	  ex.printStackTrace();  
    	 } 
    }
    
    @Test
    public void testSplit2() {
//        String touser1 = "0000.13200";
//        String touser2 = "00100.13200";
//        String touser3 = "100.13200";
//        String touser4 = "000.00000";
//        String touser5 = "000000";
//        System.out.println(new BigDecimal(touser1).toString());
//        System.out.println(new BigDecimal(touser2).toString());
//        System.out.println(new BigDecimal(touser3).toString());
//        System.out.println(new BigDecimal(touser4).toString());
//        System.out.println(new BigDecimal(touser5).toString());
//    	Set<String> userMobileNoSet = new HashSet<>();
//    	System.out.println(userMobileNoSet.size());
//    	String str = "公司";
//    	String[] strArray = str.split("-");
//    	int a = 5;
	    String appSecret = "sfci50a7s8yqi";
	    Random rand = new Random();
	    int randomIndex = rand.nextInt(1000);
	    long timestamp = new Date().getTime();
	    String str = appSecret + randomIndex + timestamp;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
			messageDigest.update(str.getBytes());  
			System.out.println("随机:"+randomIndex + "; 时间戳:"+ timestamp + ";加密后:" + getFormattedText(messageDigest.digest())); 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		LocalDateTime dt = LocalDateTime.now();
//		System.out.println(dt);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		String dateStr = df.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(1527128962268L),ZoneId.of("Asia/Shanghai")));
		System.out.println(dateStr);
		LocalDateTime date =
			    LocalDateTime.ofInstant(Instant.ofEpochMilli(1527128962268L), ZoneId.systemDefault());
		System.out.println(date);
    }
    
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',  
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'}; 

    private static String getFormattedText(byte[] bytes) {  
        int len = bytes.length;  
        StringBuilder buf = new StringBuilder(len * 2);  
        // 把密文转换成十六进制的字符串形式  
        for (int j = 0; j < len; j++) {  
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);  
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);  
        }  
        return buf.toString();  
    }  

    @Test
    public void testFor() {
        String touser = "";
        for (int i = 0; i < 3; i++) {
            if ("".equals(touser)) {
                System.out.println(touser);
                break;
            }
            touser = Integer.toString(i);
        }
        System.out.println(1 << 4);
    }
    @Test
    public void testForhh() {
    	String[] valueStrArray = StringUtils.split("#63b2f5;#35cc7e;#c8cbd5;#f2ca6a", ';');
		int length = valueStrArray.length;
		Random rand = new Random();  
		int randomIndex = 0;
    	for (int i = 0; i < 1000; i++) {
    		randomIndex = rand.nextInt(length);
    		if(randomIndex>length){
    			System.out.println(randomIndex);
    		}
    	}
    }
}
