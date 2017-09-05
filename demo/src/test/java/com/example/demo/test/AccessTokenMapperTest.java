package com.example.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.AccessToken;
import com.example.mapper.AccessTokenMapper;

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
    public void testIntPlusSpace() throws Exception {
        int a = 5;
        String b = a + "";
        System.out.println(b);
        String c = null;
        System.out.println(c.equals(""));
    }
}
