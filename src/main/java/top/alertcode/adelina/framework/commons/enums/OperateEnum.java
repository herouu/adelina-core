package top.alertcode.adelina.framework.commons.enums;

import lombok.Getter;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.util.Arrays;

@Getter
public enum OperateEnum {

    IN("in"),
    LlIKE("%like"),
    RlIKE("like%"),
    LIKE("like"),
    EQ("="),
    NE("<>"),
    GT(">"),
    GE(">="),
    LT("<"),
    LE("<=");

    private String type;

    OperateEnum(String type) {
        this.type = type;
    }


    public static OperateEnum getOperateEnum(String type) {
        OperateEnum[] values = OperateEnum.values();
        return Arrays.stream(values).filter(item -> item.getType().equals(type)).findFirst().orElseThrow(FrameworkUtilException::new);
    }
}
