package top.alertcode.adelina.framework.service.impl;

import org.junit.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;
import top.alertcode.adelina.framework.entity.service.impl.RepaymentAuditServiceImpl;

import java.util.Iterator;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class BaseServiceTest extends BaseTest {

    @Autowired
    private RepaymentAuditServiceImpl service;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CommonService commonService;

    @Autowired
    private RedissonClient redisson;

    @Test
    public void getById() {
        RKeys keys = redisson.getKeys();
        Iterator<String> iterator = keys.getKeys().iterator();
        if (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        RepaymentAudit byId = service.getById(1);
        System.out.println(byId.toString());
    }

    @Test
    public void testRedis() {
        Object sss = redisTemplate.opsForValue().get("sss");
        System.out.println(sss.toString());
    }

    @Test
    public void testCommonService() {
        RepaymentAudit byId = commonService.cacheGetById(RepaymentAudit.class, 1);
        System.out.println(byId);
    }
}