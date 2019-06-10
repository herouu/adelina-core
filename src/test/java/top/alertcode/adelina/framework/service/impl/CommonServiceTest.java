package top.alertcode.adelina.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;
import top.alertcode.adelina.framework.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class CommonServiceTest extends BaseTest {
    @Autowired
    private CommonService commonService;

    private static final int invocationCount = 4;
    private static final int threadPoolSize = 4;


    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById() {
        commonService.cacheGetById(RepaymentAudit.class, 2);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById1() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheInsertData() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setApplyRepaymentAmount(new BigDecimal(123));
        repaymentAudit.setLendingCode("setLendingCode");
        commonService.cacheInsertData(repaymentAudit);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteById() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdateById() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setId(14L);
        repaymentAudit.setApplyRepaymentDate(DateUtils.now());
        commonService.cacheUpdateById(repaymentAudit);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheSaveBatch() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheRemove() {

    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteByIds() {
        commonService.cacheDeleteByIds(RepaymentAudit.class, Collections.singletonList(11));
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

    @Test(invocationCount = 5, threadPoolSize = 5)
    @Rollback
    public void cacheDeleteById() {
        commonService.cacheDeleteById(RepaymentAudit.class, 13);
    }
}