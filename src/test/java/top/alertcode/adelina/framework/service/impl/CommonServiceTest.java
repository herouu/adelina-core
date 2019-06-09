package top.alertcode.adelina.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class CommonServiceTest extends BaseTest {
    @Autowired
    private CommonService commonService;

    private static final int invocationCount = 100;
    private static final int threadPoolSize = 100;


    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById() {
        commonService.cacheGetById(RepaymentAudit.class, 2);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById1() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheInsertData() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteById() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdateById() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheSaveBatch() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheRemove() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteByIds() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdate() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdateBatchById() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheTbUpdateBatch() {
    }
}