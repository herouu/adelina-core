package top.alertcode.adelina.framework.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author fuqiang
 * @date 2019-06-05
 * @copyright fero.com.cn
 */
@Component
public class DisLock {

    @Resource
    private RedissonClient redisson;

    public RLock getLock(String key) {
        return redisson.getLock(key);
    }
}
