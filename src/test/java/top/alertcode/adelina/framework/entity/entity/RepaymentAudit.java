package top.alertcode.adelina.framework.entity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 还款审核表
 * </p>
 *
 * @author bob
 * @since 2019-06-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "RepaymentAudit对象", description = "还款审核表")
public class RepaymentAudit implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "版本号")
    private String version;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "更新人")
    private String updatedBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updatedAt;

    @ApiModelProperty(value = "还款主id")
    private Long repaymentId;

    @ApiModelProperty(value = "当期还款状态 PRIOR-提前还款 NORMAL-正常还款")
    private String auditType;

    @ApiModelProperty(value = "项目code")
    private String projectCode;

    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @ApiModelProperty(value = "放款id")
    private Long lendingId;

    @ApiModelProperty(value = "放款code")
    private String lendingCode;

    @ApiModelProperty(value = "放款时间")
    private LocalDateTime operateLendingDatetime;

    @ApiModelProperty(value = "放款方式")
    private String repaymentMethod;

    @ApiModelProperty(value = "放款金额")
    private BigDecimal operateLendingAmount;

    @ApiModelProperty(value = "申请还款日期")
    private Date applyRepaymentDate;

    @ApiModelProperty(value = "申请还款金额")
    private BigDecimal applyRepaymentAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "可抵扣金额")
    private BigDecimal deductibleAmount;

    @ApiModelProperty(value = "还款子单据id,多个‘，’分割")
    private String repaymentSubId;

    @ApiModelProperty(value = "还款子单据期数，多个‘，分割’")
    private String repaymentSubTerm;

    @ApiModelProperty(value = "审核状态")
    private String auditStatus;

    @ApiModelProperty(value = "需要还款总金额")
    private BigDecimal needAmount;

    @ApiModelProperty(value = "需要还款本金")
    private BigDecimal needCaptail;

    @ApiModelProperty(value = "冻结抵扣的金额")
    private BigDecimal needDeductible;

    @ApiModelProperty(value = "需要还款的利息")
    private BigDecimal needInterest;


}
