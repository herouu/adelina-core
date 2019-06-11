package top.alertcode.adelina.framework.service.impl;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;
import top.alertcode.adelina.framework.entity.service.impl.RepaymentAuditServiceImpl;
import top.alertcode.adelina.framework.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class CommonServiceTest extends BaseTest {
    @Autowired
    private RepaymentAuditServiceImpl service;


    private static final int invocationCount = 2;
    private static final int threadPoolSize = 2;


    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById() {
        service.cacheGetById(RepaymentAudit.class, 2);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheGetById1() {
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheInsertData() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setApplyRepaymentAmount(new BigDecimal(123));
        repaymentAudit.setLendingCode("setLendingCode");
        service.cacheInsertData(repaymentAudit);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteById() {
        service.cacheDeleteById(RepaymentAudit.class, 7L);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdateById() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setId(14L);
        repaymentAudit.setApplyRepaymentDate(DateUtils.now());
        service.cacheUpdateById(repaymentAudit);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheSaveBatch() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setLendingCode("setLendingCode123");
        repaymentAudit.setNeedAmount(new BigDecimal(123));
        RepaymentAudit clone = SerializationUtils.clone(repaymentAudit);
        List<RepaymentAudit> repaymentAudits = Arrays.asList(repaymentAudit, clone);
        service.cacheSaveBatch(repaymentAudits);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheRemove() {

    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteByIds() {
        service.cacheDeleteByIds(RepaymentAudit.class, new Long[]{5L});
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