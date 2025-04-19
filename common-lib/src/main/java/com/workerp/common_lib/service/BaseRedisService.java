package com.workerp.common_lib.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.objectweb.asm.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Data
public class BaseRedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveWithTTL(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public void setValue(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> T getValue(String key, Class<T> clazz) {
        Object rawValue = redisTemplate.opsForValue().get(key);
        if (rawValue == null) {
            return null;
        }
        try {
            return objectMapper.convertValue(rawValue, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize data from Redis to " + clazz.getSimpleName(), e);
        }
    }

    public <T> List<T> getList(String key, Class<T> elementType) {
        Object rawValue = redisTemplate.opsForValue().get(key);
        if (rawValue == null) {
            return null;
        }
        try {
            return objectMapper.convertValue(rawValue, objectMapper.getTypeFactory().constructCollectionType(List.class, elementType));
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize data from Redis to List<" + elementType.getSimpleName() + ">", e);
        }
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
