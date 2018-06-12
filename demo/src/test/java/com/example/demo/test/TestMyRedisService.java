package com.example.demo.test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.DemoApplication;
import com.example.service.MyRedisService;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class
    )
public class TestMyRedisService {
	@Autowired
    private MyRedisService myRedisService;
	
//    @Test
    public void testSet(){
    	for (int i=0;i<2000; i++) {
    		Random random = new Random();
    		int randomI = random.nextInt(200);
    		myRedisService.set("click_Scheme_"+"8a8aba7262193f0e016219489c0c0004"+ i+"_2018-05-06","Scheme_"+"8a8aba7262193f0e016219489c0c0004"+ i+"_2018-05-01_"+ randomI);  
  	  }
    }
    
    @Test
    public void getRunTime(){
    	LocalDate yesterday = LocalDate.now().minusDays(1L);
		// 键格式为 click_模块_id__日期
    	LocalDate today = LocalDate.now();
		// 键格式为 click_模块_id__日期
		String pattern = "click*" + yesterday;
//		String pattern = "click*(?<!("+today+"))" ;
		Instant inst1 = Instant.now();
		Set<String> keys = myRedisService.getKeys(pattern);
		Instant inst2 = Instant.now();
		System.out.println(Duration.between(inst1, inst2).toMillis());
    }
}
