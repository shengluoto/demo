package com.example.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.service.MyRedisService;
import com.example.start.DemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = DemoApplication.class
    )
public class TestMyRedisService {
    @Autowired
    private MyRedisService myRedisService;
    
    @Test
    public void testSet(){
        boolean isOk = myRedisService.set("name", "tck");
    }
    
    @Test
    public void testGet(){
        String result = myRedisService.get("name");
        System.out.println(result);
    }
}
