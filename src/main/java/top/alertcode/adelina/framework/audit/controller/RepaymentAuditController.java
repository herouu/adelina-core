package top.alertcode.adelina.framework.audit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.alertcode.adelina.framework.audit.entity.RepaymentAudit;
import top.alertcode.adelina.framework.audit.service.impl.RepaymentAuditServiceImpl;
import top.alertcode.adelina.framework.controller.BaseController;

/**
 * <p>
 * 还款审核表 前端控制器
 * </p>
 *
 * @author bob
 * @since 2019-06-12
 */
@RestController
@RequestMapping("/audit/repayment-audit")
public class RepaymentAuditController extends BaseController<RepaymentAuditServiceImpl, RepaymentAudit> {

}

