package top.alertcode.adelina.framework.entity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.alertcode.adelina.framework.controller.BaseController;
import top.alertcode.adelina.framework.entity.entity.RepaymentAudit;
import top.alertcode.adelina.framework.entity.service.impl.RepaymentAuditServiceImpl;
import top.alertcode.adelina.framework.responses.JsonResponse;

import java.math.BigDecimal;

/**
 * <p>
 * 还款审核表 前端控制器
 * </p>
 *
 * @author bob
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/entity/repayment-audit")

public class RepaymentAuditController extends BaseController {

    @Autowired
    private RepaymentAuditServiceImpl repaymentAuditService;

    @GetMapping("/getById")
    public JsonResponse getById(@RequestParam String id) {
        return jsonData(repaymentAuditService.getById(id));
    }

    @PutMapping("/insertData")
    public JsonResponse insertData() {
        RepaymentAudit repaymentAudit = new RepaymentAudit();
        repaymentAudit.setApplyRepaymentAmount(new BigDecimal(123));
        return jsonData(repaymentAuditService.cacheInsertData(repaymentAudit));
    }

    @PutMapping("/deleteData")
    public JsonResponse deleteData(@RequestParam String id) {
        return jsonData(repaymentAuditService.cacheDeleteById(RepaymentAudit.class, id));
    }

    @PutMapping("/updateById")
    public JsonResponse updateById(@RequestBody RepaymentAudit repaymentAudit) {
        return jsonData(repaymentAuditService.updateById(repaymentAudit));
    }


}

