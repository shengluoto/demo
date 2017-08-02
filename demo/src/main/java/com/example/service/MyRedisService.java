package com.example.service;

public interface MyRedisService {
    public boolean set(String key, String value);

    public String get(String key);
}
