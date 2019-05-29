package top.alertcode.adelina.framework.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class IDGeneratorUtils {
    private static final String DATE_FORMAT = "yyyyMMddHHmm";

    private IDGeneratorUtils() {
    }

    /**
     * <p>generateNano.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String generateNano() {
        String dateStr = DateFormatUtils.format(DateUtils.now(), DATE_FORMAT);
        String nanoStr = String.valueOf(System.nanoTime());
        String disturbNanoStr = distrubString(nanoStr);

        return dateStr + disturbNanoStr;
    }

    /**
     * <p>generateNano.</p>
     *
     * @param append a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateNano(String append) {
        return generateNano() + append;
    }

    /**
     * <p>generateMillis.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String generateMillis() {
        String dateStr = DateFormatUtils.format(DateUtils.now(), DATE_FORMAT);
        String millsStr = String.valueOf(System.currentTimeMillis());
        String distrubMillsStr = distrubString(millsStr);

        return dateStr + distrubMillsStr;
    }

    /**
     * <p>generateMillis.</p>
     *
     * @param append a {@link java.lang.String} object.
     * @return a {@link java.lang.String} object.
     */
    public static String generateMillis(String append) {
        return generateMillis() + append;
    }

    private static String distrubString(String nanoStr) {
        List<Character> characterList = new ArrayList<>(nanoStr.length());
        for (Character character : nanoStr.toCharArray()) {
            characterList.add(character);
        }
        Collections.shuffle(characterList);
        Character[] shuffleCharacterArray = new Character[characterList.size()];
        characterList.toArray(shuffleCharacterArray);
        return StringUtils.join(shuffleCharacterArray, "");
    }
}
