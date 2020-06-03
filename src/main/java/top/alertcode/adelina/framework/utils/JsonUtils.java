package top.alertcode.adelina.framework.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public final class JsonUtils {
    private static ObjectMapper objectMapper;
    private static boolean hiddenNull = false;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //----------------------------------------------------------------------------
        //支持大数字及高精度浮点数模式
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        //objectMapper.configure(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS, true);
        //----------------------------------------------------------------------------
        objectMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtils() {
    }

    public static String toJson(Object object) {
        return toJson(object, true);
    }

    public static String toJson(Object object, boolean hiddenNull) {
        if (hiddenNull) {
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        } else {
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        }

        try {
            if (null == object) {
                return StringUtils.EMPTY;
            }

            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FrameworkUtilException(e);
        }
    }

    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(json)) {
                return null;
            }

            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new FrameworkUtilException(e);
        }
    }

    public static <T> List<T> toBeanList(String json, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(json)) {
                return Collections.EMPTY_LIST;
            }

            JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new FrameworkUtilException(e);
        }
    }

}
