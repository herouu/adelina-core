package top.alertcode.adelina.framework.redission;

import org.redisson.api.RKeys;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.utils.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang
 * @date 2019-06-13
 * @copyright fero.com.cn
 */
public class RedissionTest extends BaseTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void redissonTest() {
        RKeys keys = redissonClient.getKeys();
        for (String key : keys.getKeys()) {
            logger.info(key);
        }
    }

    @Test
    public void lockDemo() {
        RLock lock = redissonClient.getLock(StringUtils.getLockKey("123"));
        try {
            // 1. 最常见的使用方法
            lock.lock();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待2秒，上锁以后8秒自动解锁
            boolean res = lock.tryLock(2, 8, TimeUnit.SECONDS);
            if (res) { //成功
                //处理业务
            }
//            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }


    @Test
    public void lockDemo1() {
        RLock lock = redissonClient.getLock("lockName1");
        try {
            // 1. 最常见的使用方法
            lock.lock();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待2秒，上锁以后8秒自动解锁
//            boolean res = lock.tryLock(2, 8, TimeUnit.SECONDS);
//            if (res) { //成功
            //处理业务
//            }
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
