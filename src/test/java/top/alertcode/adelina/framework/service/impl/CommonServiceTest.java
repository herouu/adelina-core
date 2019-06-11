package top.alertcode.adelina.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
        repaymentAudit.setId(24L);
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
        QueryWrapper<RepaymentAudit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("lending_code", "setLendingCode1234");
        service.cacheRemove(RepaymentAudit.class, queryWrapper);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheDeleteByIds() {
        service.cacheDeleteByIds(RepaymentAudit.class, new Long[]{34L});
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdate() {
        UpdateWrapper<RepaymentAudit> wrapper = new UpdateWrapper<>();
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setLendingCode("ceshiceshi");
        repaymentAudit.setCreatedAt(DateUtils.now());
        wrapper.gt("id", 30);
        service.cacheUpdate(repaymentAudit, wrapper);
    }


    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheUpdateBatchById() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setLendingCode("ceshiceshi");
        repaymentAudit.setCreatedAt(DateUtils.now());
        repaymentAudit.setId(24L);
        repaymentAudit.setVersion("1");
        RepaymentAudit repaymentAudit1 = new RepaymentAudit();
        repaymentAudit1.setLendingCode("wocat");
        repaymentAudit1.setCreatedAt(DateUtils.now());
        repaymentAudit1.setId(27L);
        repaymentAudit1.setVersion("1");
        List<RepaymentAudit> repaymentAudits = Arrays.asList(repaymentAudit, repaymentAudit1);
        service.cacheUpdateBatchById(repaymentAudits);
    }

    @Test(invocationCount = invocationCount, threadPoolSize = threadPoolSize)
    public void testCacheTbUpdateBatch() {
    }

}