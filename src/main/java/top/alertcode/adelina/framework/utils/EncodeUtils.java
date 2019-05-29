package top.alertcode.adelina.framework.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.text.StringEscapeUtils;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * <p>EncodeUtils class.</p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class EncodeUtils {
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private EncodeUtils() {
    }

    /**
     * <p>encodeHex.</p>
     *
     * @param input an array of byte.
     * @return a {@link java.lang.String} object.
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * <p>decodeHex.</p>
     *
     * @param input a {@link java.lang.String} object.
     * @return an array of byte.
     */
    public static byte[] decodeHex(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    /**
     * <p>encodeBase64.</p>
     *
     * @param input an array of byte.
     * @return a {@link java.lang.String} object.
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * <p>encodeUrlSafeBase64.</p>
     *
     * @param input an array of byte.
     * @return a {@link java.lang.String} object.
     */
    public static String encodeUrlSafeBase64(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * <p>decodeBase64.</p>
     *
     * @param input a {@link java.lang.String} object.
     * @return an array of byte.
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * <p>encodeBase62.</p>
     *
     * @param num a long.
     * @return a {@link java.lang.String} object.
     */
    public static String encodeBase62(long num) {
        return alphabetEncode(num, 62);
    }

    /**
     * <p>decodeBase62.</p>
     *
     * @param str a {@link java.lang.String} object.
     * @return a long.
     */
    public static long decodeBase62(String str) {
        return alphabetDecode(str, 62);
    }

    private static String alphabetEncode(long num, int base) {
        num = Math.abs(num);
        StringBuilder sb = new StringBuilder();

        for (; num > 0; num /= base) {
            sb.append(ALPHABET.charAt((int) (num % base)));
        }

        return sb.toString();
    }

    private static long alphabetDecode(String str, int base) {
        AssertUtils.notNull(str);

        long result = 0;
        for (int i = 0; i < str.length(); i++) {
            result += ALPHABET.indexOf(str.charAt(i)) * Math.pow(base, i);
        }

        return result;
    }

    /**
     * <p>urlEncode.</p>
     *
     * @param part a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String urlEncode(String part) {
        try {
            return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new FrameworkUtilException(e);
        }
    }

    /**
     * <p>urlDecode.</p>
     *
     * @param part a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String urlDecode(String part) {
        try {
            return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new FrameworkUtilException(e);
        }
    }

    /**
     * <p>htmlEscape.</p>
     *
     * @param html a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml3(html);
    }

    /**
     * <p>htmlUnescape.</p>
     *
     * @param htmlEscaped a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml3(htmlEscaped);
    }

    /**
     * <p>xmlEscape.</p>
     *
     * @param xml a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeHtml3(xml);
    }

    /**
     * <p>xmlUnescape.</p>
     *
     * @param xmlEscaped a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String xmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
}

