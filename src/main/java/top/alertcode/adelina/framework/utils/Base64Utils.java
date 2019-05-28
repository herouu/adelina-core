package top.alertcode.adelina.framework.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.nio.charset.StandardCharsets;

/**
 * Created by gizmo on 15/12/11.
 */
public final class Base64Utils {
    private Base64Utils() {
    }

    public static String decode(String base64String) {
        return new String(Base64.decodeBase64(base64String), StandardCharsets.UTF_8);
    }

    public static String encode(String string) {
        try {
            return Base64.encodeBase64String(string.getBytes());
        } catch (Exception e) {
            throw new FrameworkUtilException(e);
        }
    }

}
