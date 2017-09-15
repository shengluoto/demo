package com.example.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.AccessToken;
import com.example.mapper.AccessTokenMapper;
import com.example.util.Constants;
import com.example.util.PropertiesLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccessTokenMapperTest {
    @Autowired
    private AccessTokenMapper accessTokenMapper;

    @Test
    public void testInsert() throws Exception {
        String accessToken = "14fwfwef234";
        accessTokenMapper.insert(accessToken);
    }
    
    @Test
    public void testSelectByName() throws Exception {
        AccessToken at = accessTokenMapper.getLatestAccessToken();
        System.out.println(at.toString());
        String aaa = at.toString();
        int a =5;
    }
    
    @Test
    public void getProperties() throws Exception {
        String token =
                PropertiesLoader.loadProperties(Constants.wechatProPath).getProperty("token");
        System.out.println(token);
    }
}
