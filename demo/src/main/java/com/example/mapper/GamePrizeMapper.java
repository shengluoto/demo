package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.entity.GamePrize;

public interface GamePrizeMapper {

    @Select("select * from gamePrize")
    public List<GamePrize> selectAllGamePrizes();

    @Select("select count(*) from gamePrize")
    public int getCounts();

    @Select("select * from gamePrize where prizeName like '%#{prizeName}%'")
    public List<GamePrize> selectGamePrizeByName(String prizeName);
}
