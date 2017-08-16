package com.example.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.config.RedisClusPropertiesConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisClusterProperties {
    @Autowired
    private RedisClusPropertiesConfig redisCluserProperties;
    @Test
    public void testProperties(){
        List<String> nodes = redisCluserProperties.getNodes();
        for (String string : nodes) {
            System.out.println(string);
        }
    }
}
