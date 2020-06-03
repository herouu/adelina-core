package top.alertcode.adelina.framework.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import top.alertcode.adelina.framework.base.annotation.DBColumnInfo;

import java.util.Date;

@Getter
@Setter
public class BaseEntity {
    @ApiModelProperty("主键ID")
    @DBColumnInfo(length = 11)
    private Long id;
    @ApiModelProperty("创建人")
    @DBColumnInfo(length = 64)
    private String createdBy;
    @ApiModelProperty("创建时间")
    @DBColumnInfo
    private Date createdAt;
    @ApiModelProperty("更新人")
    @DBColumnInfo(length = 64)
    private String updatedBy;
    @ApiModelProperty("更新时间")
    @DBColumnInfo
    private Date updatedAt;
    @ApiModelProperty("版本号")
    @DBColumnInfo(length = 11,DEFAULT = "1")
    private Integer version ;
}
