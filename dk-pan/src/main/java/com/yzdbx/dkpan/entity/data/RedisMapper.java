package com.yzdbx.dkpan.entity.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@SuppressWarnings("all")
@Component
public class RedisMapper {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 添加到redis
     *
     * @param key
     * @param val
     * @return
     */
    public boolean setKey(String key, Object val) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, val);
        return redisTemplate.hasKey(key);
    }

    /**
     * 添加hash结构的键值到redis
     *
     * @param key redis存储的键
     * @param hk  hash键值对的键
     * @param hv  hash键值对的值
     * @return
     */
    public boolean setKey(String key, Object hk, Object hv) {
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hk.toString(), hv);
        return hasKey(key, hk.toString());
    }

    /**
     * 添加并设置过期时间
     *
     * @param key
     * @param val
     * @param timeout
     * @param unit
     * @return
     */
    public boolean setKey(String key, Object val, long timeout, TimeUnit unit) {
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        operations.set(key, val, timeout, unit);
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key删除
     *
     * @param key
     * @return
     */
    public boolean delKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 根据hash键删除hash结构中的键值对
     *
     * @param key redis存储的键
     * @param hks hash键值对存储的键值对数组
     * @return 删除的个数
     */
    public Long delKey(String key, Object... hks) {
        if (Objects.isNull(hks)) return 0L;
        String[] vs = Arrays.stream(hks).map(hk -> hk.toString()).toArray(length -> new String[length]);
        return redisTemplate.opsForHash().delete(key, vs);
    }

    /**
     * 根据键获得值
     *
     * @param key
     * @return
     */
    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 根据建获取hash中的值
     *
     * @param key redis储存的键
     * @param hk  hash键
     * @return
     */
    public Object getKey(String key, Object hk) {
        return redisTemplate.opsForHash().get(key, hk.toString());
    }
    /**
     * 判断键是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    /**
     * 判断hash数据结构中的key是否存在
     *
     * @param key redis中存储的key
     * @param hk  hash中的key
     * @return
     */
    public boolean hasKey(String key, Object hk) {
        return redisTemplate.opsForHash().hasKey(key, hk.toString());
    }

    /**
     * 返回指定键Hash结构中的所有值的集合
     *
     * @param key         存储hash数据的键
     * @param genericType
     * @param <T>         返回集合的泛型
     * @return
     */
    public <T> List<T> valuesList(String key, Class<T> genericType) {
        List<Object> values = redisTemplate.opsForHash().values(key);
        if (Objects.isNull(values) || values.isEmpty())
            return Arrays.asList();

        return values
                .stream().map(obj -> genericType.cast(obj)).collect(Collectors.toList());
    }

}
