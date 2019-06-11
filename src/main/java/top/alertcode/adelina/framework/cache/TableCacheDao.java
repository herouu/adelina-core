package top.alertcode.adelina.framework.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 表的缓存，主要操作redis hset结构
 */
@Component
public class TableCacheDao {

    @Autowired
    private RedisTemplate redisTemplate;


    public void add(String tableName, String key, String value) {
        redisTemplate.opsForHash().put(tableName, key, value);
    }

    public List<String> mutiGet(String tableName, Collection<String> keys) {
        return redisTemplate.opsForHash().multiGet(tableName, keys);
    }

    public void addAll(String tableName, Map<String, String> entry) {
        redisTemplate.opsForHash().putAll(tableName, entry);
    }

    /**
     * @param tableName
     * @param key
     * @param value
     * @param expire    失效时间
     * @return
     */
    public void add(String tableName, String key, String value, int expire) {
        redisTemplate.opsForHash().put(tableName, key, value);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    public void addAll(String tableName, Map<String, String> entry, int expire) {
        redisTemplate.opsForHash().putAll(tableName, entry);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    public long delete(String tableName, String... keys) {
        return redisTemplate.opsForHash().delete(tableName, keys);
    }

    public boolean exists(String tableName, String key) {
        return redisTemplate.opsForHash().hasKey(tableName, key);
    }

    public long size(String tableName) {
        return redisTemplate.opsForHash().size(tableName);
    }

    public List<String> get(String tableName, String... keys) {
        return redisTemplate.opsForHash().multiGet(tableName, Arrays.asList(keys));
    }

    public String get(String tableName, String key) {
        return Objects.toString(redisTemplate.opsForHash().get(tableName, key));
    }

    public Map<String, String> getAll(String tableName) {
        return redisTemplate.opsForHash().entries(tableName);
    }

    public Set<String> keys(String tableName) {
        return redisTemplate.opsForHash().keys(tableName);
    }

    /*hash类型:返回 key 指定的哈希集包含的字段的数量*/
    public List<String> values(String tableName) {
        return redisTemplate.opsForHash().values(tableName);
    }


}
