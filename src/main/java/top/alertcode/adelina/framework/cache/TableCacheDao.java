package top.alertcode.adelina.framework.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 表的缓存，主要操作redis hset结构
 *
 * @author alertcode
 * @version $Id: $Id
 */
@Component
public class TableCacheDao {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * <p>add.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param key       a {@link java.lang.String} object.
     * @param value     a {@link java.lang.String} object.
     */
    public void add(String tableName, String key, Object value) {
        redisTemplate.opsForHash().put(tableName, key, value);
    }

    /**
     * <p>mutiGet.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param keys a {@link java.util.Collection} object.
     * @return a {@link java.util.List} object.
     */
    public List<String> mutiGet(String tableName, Collection<String> keys) {
        return redisTemplate.opsForHash().multiGet(tableName, keys);
    }

    /**
     * <p>addAll.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param entry a {@link java.util.Map} object.
     */
    public void addAll(String tableName, Map<String, String> entry) {
        redisTemplate.opsForHash().putAll(tableName, entry);
    }

    /**
     * <p>add.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param key a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @param expire    失效时间
     */
    public void add(String tableName, String key, String value, int expire) {
        redisTemplate.opsForHash().put(tableName, key, value);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    /**
     * <p>addAll.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param entry a {@link java.util.Map} object.
     * @param expire a int.
     */
    public void addAll(String tableName, Map<String, String> entry, int expire) {
        redisTemplate.opsForHash().putAll(tableName, entry);
        redisTemplate.expire(tableName, expire, TimeUnit.SECONDS);
    }

    /**
     * <p>delete.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param keys a {@link java.lang.String} object.
     * @return a long.
     */
    public long delete(String tableName, String... keys) {
        return redisTemplate.opsForHash().delete(tableName, keys);
    }

    /**
     * <p>exists.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param key a {@link java.lang.String} object.
     * @return a boolean.
     */
    public boolean exists(String tableName, String key) {
        return redisTemplate.opsForHash().hasKey(tableName, key);
    }

    /**
     * <p>size.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @return a long.
     */
    public long size(String tableName) {
        return redisTemplate.opsForHash().size(tableName);
    }

    /**
     * <p>get.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param keys a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<String> get(String tableName, String... keys) {
        return redisTemplate.opsForHash().multiGet(tableName, Arrays.asList(keys));
    }

    /**
     * <p>get.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @param key a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public Object get(String tableName, String key) {
        return redisTemplate.opsForHash().get(tableName, key);
    }

    /**
     * <p>getAll.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @return a {@link java.util.Map} object.
     */
    public Map<String, String> getAll(String tableName) {
        return redisTemplate.opsForHash().entries(tableName);
    }

    /**
     * <p>keys.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @return a {@link java.util.Set} object.
     */
    public Set<String> keys(String tableName) {
        return redisTemplate.opsForHash().keys(tableName);
    }

    /*hash类型:返回 key 指定的哈希集包含的字段的数量*/

    /**
     * <p>values.</p>
     *
     * @param tableName a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<String> values(String tableName) {
        return redisTemplate.opsForHash().values(tableName);
    }


}
