package com.example.service;

import java.util.List;

import com.example.entity.GamePrize;

public interface GamePrizeService {
    
    public List<GamePrize> selectAllGamePrizes();
    
    public List<GamePrize> selectGamePrizeByName(String prizeName);
}
