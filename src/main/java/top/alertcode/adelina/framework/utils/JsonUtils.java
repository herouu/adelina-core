package top.alertcode.adelina.framework.utils;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.io.IOException;

/**
 * Created by gizmo on 15/12/8.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class JsonUtils extends JSON {


    private static ObjectMapper objectMapper = new ObjectMapper();


    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FrameworkUtilException("jackJson writeValueAsString exception");
        }
    }

    public static <T> T readValue(String value, Class<T> clazz) {
        try {
            return objectMapper.readValue(value, clazz);
        } catch (IOException e) {
            throw new FrameworkUtilException();
        }
    }
}
