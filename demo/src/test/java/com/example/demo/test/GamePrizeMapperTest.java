package com.example.demo.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.entity.GamePrize;
import com.example.mapper.GamePrizeMapper;
import com.github.pagehelper.PageHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GamePrizeMapperTest {
    @Autowired
    private GamePrizeMapper gamePrizeMapper;

    @Test
    public void testSelect() throws Exception {
        List<GamePrize> listGamePrizes = gamePrizeMapper.selectAllGamePrizes();
        for (GamePrize gamePrize : listGamePrizes) {
            System.out.println(gamePrize.toString());
        }
    }
    
    @Test
    public void testSelectByName() throws Exception {
        String prizeName = "本";
        List<GamePrize> listGamePrizes = gamePrizeMapper.selectGamePrizeByName(prizeName);
        for (GamePrize gamePrize : listGamePrizes) {
            System.out.println(gamePrize.toString());
        }
        PageHelper ph = new PageHelper();
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
