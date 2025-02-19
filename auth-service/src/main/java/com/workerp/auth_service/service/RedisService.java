package com.workerp.auth_service.service;

import com.workerp.common_lib.service.BaseRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService extends BaseRedisService {
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }
}