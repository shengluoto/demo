package com.example.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import com.example.service.MyRedisService;

@Service
public class MyRedisImpl implements MyRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Override
    public boolean set(final String key,final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>(){
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String get(String key) {
        String result = redisTemplate.execute(new RedisCallback<String>(){
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

	@Override
	public Set<String> getKeys(String keyParten) {
		return redisTemplate.keys(keyParten);
	}

}
