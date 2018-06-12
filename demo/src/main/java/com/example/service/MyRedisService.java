package com.example.service;

import java.util.Set;

public interface MyRedisService {
    public boolean set(String key, String value);

    public String get(String key);
    
    public Set<String> getKeys(String keyParten);
}
