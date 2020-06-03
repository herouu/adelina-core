package top.alertcode.adelina.framework.base.exception;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class VerficationException extends IllegalArgumentException {

    //具体CODE参照ExceptionCode枚举类
    private Integer code;
    private String param;
    private String message;

    public VerficationException() {
        super();
    }

    public VerficationException(String message) {
        super(message);
        this.message = message;
    }

    public VerficationException(String message, Integer code) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public VerficationException(String message, Integer code, String param) {
        super(message);
        this.code = code;
        this.param = param;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        Map<String, Object> json = new HashMap<>();
        json.put("code", code);
        json.put("param", param);
        json.put("message", message);
        return JSON.toJSONString(json);
    }
}
