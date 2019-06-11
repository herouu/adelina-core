package top.alertcode.adelina.framework.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;
import top.alertcode.adelina.framework.entity.service.impl.RepaymentAuditServiceImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class BaseServiceTest extends BaseTest {


    @Autowired
    RepaymentAuditServiceImpl repaymentAuditService;

    @Test
    public void testSaveBatch() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setLendingCode("setLendingCode123");
        repaymentAudit.setNeedAmount(new BigDecimal(123));
        List<RepaymentAudit> repaymentAudits = Arrays.asList(repaymentAudit, repaymentAudit);
        repaymentAuditService.saveBatch(repaymentAudits);
    }
}