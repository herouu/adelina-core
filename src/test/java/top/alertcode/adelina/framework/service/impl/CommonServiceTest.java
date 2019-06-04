package top.alertcode.adelina.framework.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.alertcode.adelina.framework.BaseTest;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;

import java.math.BigDecimal;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
public class CommonServiceTest extends BaseTest {
    @Autowired
    private CommonService commonService;

    @Test
    public void getById() {
        Object object = commonService.getById(1);
    }

    @Test
    public void insertData() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setApplyRepaymentAmount(new BigDecimal(123));
        commonService.save(repaymentAudit);
    }
}