package com.example.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.util.RedissLockUtil;

/**
 * redis分布式锁配置(暂未读取分布式配置文件)
 * @ClassName: RedissonConfig
 * @Description: 
 * @author 
 * 2018年3月28日  tck 创建
 */
@Configuration
public class RedissonConfig {
//	@Value("${spring.redis.host}")
//    private String host;
//	
//	@Value("${spring.redis.port}")
//	private String port;
//	
//	@Value("${spring.redis.password}")
//	private String password;
//	
//	@Value("${spring.redis.isSsl}")
//	private String isSsl;
//	
//	@Value("${spring.redis.database}")
//	private int database;
	
	@Autowired
	private RedisProperties redisProperties;
	
    @Bean
    public RedissonClient redisson() {
    	Config config = new Config();
    	String schema = "0".equals(redisProperties.isSsl()) ? "rediss://" : "redis://";
    	if (redisProperties.getSentinel() != null) {
            SentinelServersConfig sentinelServersConfig = config.useSentinelServers();
            sentinelServersConfig.setMasterName(redisProperties.getSentinel().getMaster());
            redisProperties.getSentinel().getNodes();
            sentinelServersConfig.addSentinelAddress(schema+redisProperties.getSentinel().getNodes());
            sentinelServersConfig.setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                sentinelServersConfig.setPassword(redisProperties.getPassword());
            }
        } else {
	    	SingleServerConfig singleServerConfig = config.useSingleServer();
	    	// format as redis://127.0.0.1:7181 or rediss://127.0.0.1:7181 for SSL
	    	singleServerConfig.setAddress(schema + redisProperties.getHost() + ":" + redisProperties.getPort());
	    	singleServerConfig.setDatabase(redisProperties.getDatabase());
	    	if (null != redisProperties.getPassword()) {
	    		singleServerConfig.setPassword(redisProperties.getPassword());
	    	}
        }
        return Redisson.create(config);
    }
    
//    /**
//     * 装配locker类，并将实例注入到RedissLockUtil中
//     * @return
//     */
//    @Bean
//    RedissLockUtil redissLockUtil(RedissonClient redissonClient) {
//    	RedissLockUtil redissLockUtil = new RedissLockUtil();
//    	redissLockUtil.setRedissonClient(redissonClient);
//		return redissLockUtil;
//    }
}
