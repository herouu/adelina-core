package top.alertcode.adelina.framework.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
@SuppressWarnings("ALL")
public final class IdUtils {
    private static SecureRandom random = new SecureRandom();

    private IdUtils() {
    }

    /**
     * Constant <code>chars="new String[] { a, b, c, d, e, f,g, h, i"{trunked}</code>
     */
    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * <p>uuid.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * <p>randomLong.</p>
     *
     * @return a long.
     */
    public static long randomLong() {
        return random.nextLong();
    }

    /**
     * <p>randomBase62.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String randomBase62() {
        return EncodeUtils.encodeBase62(random.nextLong());
    }

    /**
     *
     * 获取8位的UUID字符�?
     * 采用将UUID字符串转换成26位小写字�?+26位大写字�?+0-9十个数字组成�?8位字符串
     * 测试时有千万分之�?的重复机�?
     * 注：字符串区分大小写
     *
     * @return a {@link java.lang.String} object.
     */
    public static String generateBaseShortUUID() {
        return generateBaseShortUUID();
    }

    /**
     *
     * 获取8位的UUID字符�?
     * 采用将UUID字符串转换成26位小写字�?+26位大写字�?+0-9十个数字组成�?8位字符串
     * 测试时有千万分之�?的重复机�?
     * 注：字符串区分大小写
     *
     * @param prefix 前缀随机字符位数
     * @return String
     */
    public static String generateBaseShortUUID(int prefix) {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 62
        int CHAR_ARRAY_LENGTH = 0x3E;
        if(prefix >0) {
            for(int i = 0; i < prefix; i++){
                Random random = new Random();
                int r = random.nextInt(CHAR_ARRAY_LENGTH-1);
                shortBuffer.append(chars[r]);
            }
        }
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % CHAR_ARRAY_LENGTH]);
        }
        return shortBuffer.toString();
    }

    /**
     *
     * 获取8位的UUID字符�?
     * 采用将UUID字符串转换成26位小写字�?+26位大写字�?+0-9十个数字组成�?8位字符串
     * 测试时有千万分之�?的重复机�?
     * 注：字符串区分大小写
     *
     * @return String
     */
    public static String generateShortUUID8() {
        return generateBaseShortUUID(0);
    }

    /**
     *
     * 获取10位的UUID字符�?(两位前缀随机�?)
     * 采用将UUID字符串转换成26位小写字�?+26位大写字�?+0-9十个数字组成�?8位字符串
     * 在generateShortUUID8方法基础上添加随机数降低重复机率
     * 注：字符串区分大小写
     *
     * @return String
     */
    public static String generateShortUUID10() {
        return generateBaseShortUUID(2);
    }

}
