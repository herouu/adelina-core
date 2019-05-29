package top.alertcode.adelina.framework.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.nio.charset.StandardCharsets;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class Base64Utils {
    private Base64Utils() {
    }

    /**
     * <p>decode.</p>
     *
     * @param base64String a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String decode(String base64String) {
        return new String(Base64.decodeBase64(base64String), StandardCharsets.UTF_8);
    }

    /**
     * <p>encode.</p>
     *
     * @param string a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String encode(String string) {
        try {
            return Base64.encodeBase64String(string.getBytes());
        } catch (Exception e) {
            throw new FrameworkUtilException(e);
        }
    }

}
