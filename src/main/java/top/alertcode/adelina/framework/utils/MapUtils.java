package top.alertcode.adelina.framework.utils;

import org.apache.commons.collections4.*;
import org.apache.commons.collections4.map.*;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * <p>MapUtils class.</p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class MapUtils {
    /**
     * Constant <code>EMPTY_SORTED_MAP</code>
     */
    public static final SortedMap EMPTY_SORTED_MAP =
            UnmodifiableSortedMap.unmodifiableSortedMap(new TreeMap<Object, Object>());
    private static final String INDENT_STRING = "    ";

    private MapUtils() {
    }

    /**
     * <p>getObject.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a V object.
     */
    public static <K, V> V getObject(final Map<? super K, V> map, final K key) {
        if (map != null) {
            return map.get(key);
        }
        return null;
    }

    /**
     * <p>getString.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.String} object.
     */
    public static <K> String getString(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    /**
     * <p>getBoolean.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Boolean} object.
     */
    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;
                }
                if (answer instanceof String) {
                    return Boolean.valueOf((String) answer);
                }
                if (answer instanceof Number) {
                    final Number n = (Number) answer;
                    return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }

    /**
     * <p>getNumber.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Number} object.
     */
    public static <K> Number getNumber(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Number) {
                    return (Number) answer;
                }
                if (answer instanceof String) {
                    try {
                        final String text = (String) answer;
                        return NumberFormat.getInstance().parse(text);
                    } catch (final ParseException e) { // NOPMD
                        // failure means null is returned
                    }
                }
            }
        }
        return null;
    }

    /**
     * <p>getByte.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Byte} object.
     */
    public static <K> Byte getByte(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Byte) {
            return (Byte) answer;
        }
        return Byte.valueOf(answer.byteValue());
    }

    /**
     * <p>getShort.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Short} object.
     */
    public static <K> Short getShort(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Short) {
            return (Short) answer;
        }
        return Short.valueOf(answer.shortValue());
    }

    /**
     * <p>getInteger.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Integer} object.
     */
    public static <K> Integer getInteger(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Integer) {
            return (Integer) answer;
        }
        return Integer.valueOf(answer.intValue());
    }

    /**
     * <p>getLong.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Long} object.
     */
    public static <K> Long getLong(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Long) {
            return (Long) answer;
        }
        return Long.valueOf(answer.longValue());
    }

    /**
     * <p>getFloat.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Float} object.
     */
    public static <K> Float getFloat(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Float) {
            return (Float) answer;
        }
        return Float.valueOf(answer.floatValue());
    }

    /**
     * <p>getDouble.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.lang.Double} object.
     */
    public static <K> Double getDouble(final Map<? super K, ?> map, final K key) {
        final Number answer = getNumber(map, key);
        if (answer == null) {
            return null;
        }
        if (answer instanceof Double) {
            return (Double) answer;
        }
        return Double.valueOf(answer.doubleValue());
    }

    /**
     * <p>getMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a {@link java.util.Map} object.
     */
    public static <K> Map<?, ?> getMap(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map<?, ?>) answer;
            }
        }
        return null;
    }

    /**
     * <p>getObject.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a V object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a V object.
     */
    public static <K, V> V getObject(final Map<K, V> map, final K key, final V defaultValue) {
        if (map != null) {
            final V answer = map.get(key);
            if (answer != null) {
                return answer;
            }
        }
        return defaultValue;
    }

    /**
     * <p>getString.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.String} object.
     * @param <K> a K object.
     * @return a {@link java.lang.String} object.
     */
    public static <K> String getString(final Map<? super K, ?> map, final K key, final String defaultValue) {
        String answer = getString(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getBoolean.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Boolean} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Boolean} object.
     */
    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key, final Boolean defaultValue) {
        Boolean answer = getBoolean(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getNumber.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Number} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Number} object.
     */
    public static <K> Number getNumber(final Map<? super K, ?> map, final K key, final Number defaultValue) {
        Number answer = getNumber(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getByte.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Byte} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Byte} object.
     */
    public static <K> Byte getByte(final Map<? super K, ?> map, final K key, final Byte defaultValue) {
        Byte answer = getByte(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getShort.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Short} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Short} object.
     */
    public static <K> Short getShort(final Map<? super K, ?> map, final K key, final Short defaultValue) {
        Short answer = getShort(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getInteger.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Integer} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Integer} object.
     */
    public static <K> Integer getInteger(final Map<? super K, ?> map, final K key, final Integer defaultValue) {
        Integer answer = getInteger(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getLong.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Long} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Long} object.
     */
    public static <K> Long getLong(final Map<? super K, ?> map, final K key, final Long defaultValue) {
        Long answer = getLong(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getFloat.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Float} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Float} object.
     */
    public static <K> Float getFloat(final Map<? super K, ?> map, final K key, final Float defaultValue) {
        Float answer = getFloat(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getDouble.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.lang.Double} object.
     * @param <K> a K object.
     * @return a {@link java.lang.Double} object.
     */
    public static <K> Double getDouble(final Map<? super K, ?> map, final K key, final Double defaultValue) {
        Double answer = getDouble(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a {@link java.util.Map} object.
     * @param <K> a K object.
     * @return a {@link java.util.Map} object.
     */
    public static <K> Map<?, ?> getMap(final Map<? super K, ?> map, final K key, final Map<?, ?> defaultValue) {
        Map<?, ?> answer = getMap(map, key);
        if (answer == null) {
            answer = defaultValue;
        }
        return answer;
    }

    /**
     * <p>getBooleanValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a boolean.
     */
    public static <K> boolean getBooleanValue(final Map<? super K, ?> map, final K key) {
        return Boolean.TRUE.equals(getBoolean(map, key));
    }

    /**
     * <p>getByteValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a byte.
     */
    public static <K> byte getByteValue(final Map<? super K, ?> map, final K key) {
        final Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return 0;
        }
        return byteObject.byteValue();
    }

    /**
     * <p>getShortValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a short.
     */
    public static <K> short getShortValue(final Map<? super K, ?> map, final K key) {
        final Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return 0;
        }
        return shortObject.shortValue();
    }

    /**
     * <p>getIntValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a int.
     */
    public static <K> int getIntValue(final Map<? super K, ?> map, final K key) {
        final Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return 0;
        }
        return integerObject.intValue();
    }

    /**
     * <p>getLongValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a long.
     */
    public static <K> long getLongValue(final Map<? super K, ?> map, final K key) {
        final Long longObject = getLong(map, key);
        if (longObject == null) {
            return 0L;
        }
        return longObject.longValue();
    }

    /**
     * <p>getFloatValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a float.
     */
    public static <K> float getFloatValue(final Map<? super K, ?> map, final K key) {
        final Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return 0f;
        }
        return floatObject.floatValue();
    }

    /**
     * <p>getDoubleValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param <K> a K object.
     * @return a double.
     */
    public static <K> double getDoubleValue(final Map<? super K, ?> map, final K key) {
        final Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return 0d;
        }
        return doubleObject.doubleValue();
    }

    /**
     * <p>getBooleanValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a boolean.
     * @param <K> a K object.
     * @return a boolean.
     */
    public static <K> boolean getBooleanValue(final Map<? super K, ?> map, final K key, final boolean defaultValue) {
        final Boolean booleanObject = getBoolean(map, key);
        if (booleanObject == null) {
            return defaultValue;
        }
        return booleanObject.booleanValue();
    }

    /**
     * <p>getByteValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a byte.
     * @param <K> a K object.
     * @return a byte.
     */
    public static <K> byte getByteValue(final Map<? super K, ?> map, final K key, final byte defaultValue) {
        final Byte byteObject = getByte(map, key);
        if (byteObject == null) {
            return defaultValue;
        }
        return byteObject.byteValue();
    }

    /**
     * <p>getShortValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a short.
     * @param <K> a K object.
     * @return a short.
     */
    public static <K> short getShortValue(final Map<? super K, ?> map, final K key, final short defaultValue) {
        final Short shortObject = getShort(map, key);
        if (shortObject == null) {
            return defaultValue;
        }
        return shortObject.shortValue();
    }

    /**
     * <p>getIntValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a int.
     * @param <K> a K object.
     * @return a int.
     */
    public static <K> int getIntValue(final Map<? super K, ?> map, final K key, final int defaultValue) {
        final Integer integerObject = getInteger(map, key);
        if (integerObject == null) {
            return defaultValue;
        }
        return integerObject.intValue();
    }

    /**
     * <p>getLongValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a long.
     * @param <K> a K object.
     * @return a long.
     */
    public static <K> long getLongValue(final Map<? super K, ?> map, final K key, final long defaultValue) {
        final Long longObject = getLong(map, key);
        if (longObject == null) {
            return defaultValue;
        }
        return longObject.longValue();
    }

    /**
     * <p>getFloatValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a float.
     * @param <K> a K object.
     * @return a float.
     */
    public static <K> float getFloatValue(final Map<? super K, ?> map, final K key, final float defaultValue) {
        final Float floatObject = getFloat(map, key);
        if (floatObject == null) {
            return defaultValue;
        }
        return floatObject.floatValue();
    }

    /**
     * <p>getDoubleValue.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param defaultValue a double.
     * @param <K> a K object.
     * @return a double.
     */
    public static <K> double getDoubleValue(final Map<? super K, ?> map, final K key, final double defaultValue) {
        final Double doubleObject = getDouble(map, key);
        if (doubleObject == null) {
            return defaultValue;
        }
        return doubleObject.doubleValue();
    }

    /**
     * <p>toProperties.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Properties} object.
     */
    public static <K, V> Properties toProperties(final Map<K, V> map) {
        final Properties answer = new Properties();
        if (map != null) {
            for (final Map.Entry<K, V> entry2 : map.entrySet()) {
                final Map.Entry<?, ?> entry = entry2;
                final Object key = entry.getKey();
                final Object value = entry.getValue();
                answer.put(key, value);
            }
        }
        return answer;
    }

    /**
     * <p>toMap.</p>
     *
     * @param resourceBundle a {@link java.util.ResourceBundle} object.
     * @return a {@link java.util.Map} object.
     */
    public static Map<String, Object> toMap(final ResourceBundle resourceBundle) {
        final Enumeration<String> enumeration = resourceBundle.getKeys();
        final Map<String, Object> map = new HashMap<String, Object>();

        while (enumeration.hasMoreElements()) {
            final String key = enumeration.nextElement();
            final Object value = resourceBundle.getObject(key);
            map.put(key, value);
        }

        return map;
    }

    /**
     * <p>verbosePrint.</p>
     *
     * @param out a {@link java.io.PrintStream} object.
     * @param label a {@link java.lang.Object} object.
     * @param map a {@link java.util.Map} object.
     */
    public static void verbosePrint(final PrintStream out, final Object label, final Map<?, ?> map) {
        verbosePrintInternal(out, label, map, new ArrayStack<Map<?, ?>>(), false);
    }

    private static void verbosePrintInternal(final PrintStream out, final Object label, final Map<?, ?> map,
                                             final ArrayStack<Map<?, ?>> lineage, final boolean debug) {
        printIndent(out, lineage.size());

        if (map == null) {
            if (label != null) {
                out.print(label);
                out.print(" = ");
            }
            out.println("null");
            return;
        }
        if (label != null) {
            out.print(label);
            out.println(" = ");
        }

        printIndent(out, lineage.size());
        out.println("{");

        lineage.push(map);

        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            final Object childKey = entry.getKey();
            final Object childValue = entry.getValue();
            if (childValue instanceof Map && !lineage.contains(childValue)) {
                verbosePrintInternal(
                        out,
                        childKey == null ? "null" : childKey,
                        (Map<?, ?>) childValue,
                        lineage,
                        debug);
            } else {
                printIndent(out, lineage.size());
                out.print(childKey);
                out.print(" = ");

                final int lineageIndex = lineage.indexOf(childValue);
                if (lineageIndex == -1) {
                    out.print(childValue);
                } else if (lineage.size() - 1 == lineageIndex) {
                    out.print("(this Map)");
                } else {
                    out.print(
                            "(ancestor["
                                    + (lineage.size() - 1 - lineageIndex - 1)
                                    + "] Map)");
                }

                if (debug && childValue != null) {
                    out.print(' ');
                    out.println(childValue.getClass().getName());
                } else {
                    out.println();
                }
            }
        }

        lineage.pop();

        printIndent(out, lineage.size());
        out.println(debug ? "} " + map.getClass().getName() : "}");
    }

    private static void printIndent(final PrintStream out, final int indent) {
        for (int i = 0; i < indent; i++) {
            out.print(INDENT_STRING);
        }
    }

    /**
     * <p>invertMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
    public static <K, V> Map<V, K> invertMap(final Map<K, V> map) {
        final Map<V, K> out = new HashMap<V, K>(map.size());
        for (final Map.Entry<K, V> entry : map.entrySet()) {
            out.put(entry.getValue(), entry.getKey());
        }
        return out;
    }

    /**
     * <p>safeAddToMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param key a K object.
     * @param value a {@link java.lang.Object} object.
     * @param <K> a K object.
     * @throws java.lang.NullPointerException if any.
     */
    public static <K> void safeAddToMap(final Map<? super K, Object> map, final K key, final Object value)
            throws NullPointerException {
        map.put(key, value == null ? "" : value);
    }

    /**
     * <p>putAll.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param array an array of {@link java.lang.Object} objects.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
    public static <K, V> Map<K, V> putAll(final Map<K, V> map, final Object[] array) {
        map.size();  // force NPE
        if (array == null || array.length == 0) {
            return map;
        }
        final Object obj = array[0];
        if (obj instanceof Map.Entry) {
            for (final Object element : array) {
                // cast ok here, type is checked above
                final Map.Entry<K, V> entry = (Map.Entry<K, V>) element;
                map.put(entry.getKey(), entry.getValue());
            }
        } else if (obj instanceof KeyValue) {
            for (final Object element : array) {
                // cast ok here, type is checked above
                final KeyValue<K, V> keyval = (KeyValue<K, V>) element;
                map.put(keyval.getKey(), keyval.getValue());
            }
        } else if (obj instanceof Object[]) {
            for (int i = 0; i < array.length; i++) {
                final Object[] sub = (Object[]) array[i];
                if (sub == null || sub.length < 2) {
                    throw new IllegalArgumentException("Invalid array element: " + i);
                }
                // these casts can fail if array has incorrect types
                map.put((K) sub[0], (V) sub[1]);
            }
        } else {
            for (int i = 0; i < array.length - 1; ) {
                // these casts can fail if array has incorrect types
                map.put((K) array[i++], (V) array[i++]);
            }
        }
        return map;
    }

    /**
     * <p>emptyIfNull.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
    public static <K, V> Map<K, V> emptyIfNull(final Map<K, V> map) {
        return map == null ? Collections.emptyMap() : map;
    }

    /**
     * <p>isEmpty.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @return a boolean.
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * <p>isNotEmpty.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @return a boolean.
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !MapUtils.isEmpty(map);
    }

    /**
     * <p>synchronizedMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
    public static <K, V> Map<K, V> synchronizedMap(final Map<K, V> map) {
        return Collections.synchronizedMap(map);
    }

    /**
     * <p>predicatedMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param keyPred a {@link org.apache.commons.collections4.Predicate} object.
     * @param valuePred a {@link org.apache.commons.collections4.Predicate} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> predicatedMap(final Map<K, V> map, final Predicate<? super K> keyPred,
                                                         final Predicate<? super V> valuePred) {
        return PredicatedMap.predicatedMap(map, keyPred, valuePred);
    }

    /**
     * <p>unmodifiableMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.Map} object.
     */
    public static <K, V> Map<K, V> unmodifiableMap(final Map<? extends K, ? extends V> map) {
        return UnmodifiableMap.unmodifiableMap(map);
    }

    /**
     * <p>transformedMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param valueTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> transformedMap(final Map<K, V> map,
                                                          final Transformer<? super K, ? extends K> keyTransformer,
                                                          final Transformer<? super V, ? extends V> valueTransformer) {
        return TransformedMap.transformingMap(map, keyTransformer, valueTransformer);
    }

    /**
     * <p>fixedSizeMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> fixedSizeMap(final Map<K, V> map) {
        return FixedSizeMap.fixedSizeMap(map);
    }

    /**
     * <p>lazyMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param factory a {@link org.apache.commons.collections4.Factory} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> lazyMap(final Map<K, V> map, final Factory<? extends V> factory) {
        return LazyMap.lazyMap(map, factory);
    }

    /**
     * <p>lazyMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param transformerFactory a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> lazyMap(final Map<K, V> map,
                                                   final Transformer<? super K, ? extends V> transformerFactory) {
        return LazyMap.lazyMap(map, transformerFactory);
    }

    /**
     * <p>orderedMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.OrderedMap} object.
     */
    public static <K, V> OrderedMap<K, V> orderedMap(final Map<K, V> map) {
        return ListOrderedMap.listOrderedMap(map);
    }

    /**
     * <p>multiValueMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.map.MultiValueMap} object.
     */
    public static <K, V> MultiValueMap<K, V> multiValueMap(final Map<K, ? super Collection<V>> map) {
        return MultiValueMap.multiValueMap(map);
    }

    /**
     * <p>multiValueMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param collectionClass a {@link java.lang.Class} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.map.MultiValueMap} object.
     */
    public static <K, V, C extends Collection<V>> MultiValueMap<K, V> multiValueMap(final Map<K, C> map,
                                                                                    final Class<C> collectionClass) {
        return MultiValueMap.multiValueMap(map, collectionClass);
    }

    /**
     * <p>multiValueMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param collectionFactory a {@link org.apache.commons.collections4.Factory} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.map.MultiValueMap} object.
     */
    public static <K, V, C extends Collection<V>> MultiValueMap<K, V> multiValueMap(final Map<K, C> map,
                                                                                    final Factory<C> collectionFactory) {
        return MultiValueMap.multiValueMap(map, collectionFactory);
    }

    /**
     * <p>synchronizedSortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> synchronizedSortedMap(final SortedMap<K, V> map) {
        return Collections.synchronizedSortedMap(map);
    }

    /**
     * <p>unmodifiableSortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> unmodifiableSortedMap(final SortedMap<K, ? extends V> map) {
        return UnmodifiableSortedMap.unmodifiableSortedMap(map);
    }

    /**
     * <p>predicatedSortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param keyPred a {@link org.apache.commons.collections4.Predicate} object.
     * @param valuePred a {@link org.apache.commons.collections4.Predicate} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> predicatedSortedMap(final SortedMap<K, V> map,
                                                             final Predicate<? super K> keyPred, final Predicate<? super V> valuePred) {
        return PredicatedSortedMap.predicatedSortedMap(map, keyPred, valuePred);
    }

    /**
     * <p>transformedSortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param valueTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> transformedSortedMap(final SortedMap<K, V> map,
                                                              final Transformer<? super K, ? extends K> keyTransformer,
                                                              final Transformer<? super V, ? extends V> valueTransformer) {
        return TransformedSortedMap.transformingSortedMap(map, keyTransformer, valueTransformer);
    }

    /**
     * <p>fixedSizeSortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> fixedSizeSortedMap(final SortedMap<K, V> map) {
        return FixedSizeSortedMap.fixedSizeSortedMap(map);
    }

    /**
     * <p>lazySortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param factory a {@link org.apache.commons.collections4.Factory} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> lazySortedMap(final SortedMap<K, V> map, final Factory<? extends V> factory) {
        return LazySortedMap.lazySortedMap(map, factory);
    }

    /**
     * <p>lazySortedMap.</p>
     *
     * @param map a {@link java.util.SortedMap} object.
     * @param transformerFactory a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link java.util.SortedMap} object.
     */
    public static <K, V> SortedMap<K, V> lazySortedMap(final SortedMap<K, V> map,
                                                       final Transformer<? super K, ? extends V> transformerFactory) {
        return LazySortedMap.lazySortedMap(map, transformerFactory);
    }

    /**
     * <p>populateMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param elements a {@link java.lang.Iterable} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     */
    public static <K, V> void populateMap(final Map<K, V> map, final Iterable<? extends V> elements,
                                          final Transformer<V, K> keyTransformer) {
        populateMap(map, elements, keyTransformer, TransformerUtils.nopTransformer());
    }

    /**
     * <p>populateMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param elements a {@link java.lang.Iterable} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param valueTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @param <E> a E object.
     */
    public static <K, V, E> void populateMap(final Map<K, V> map, final Iterable<? extends E> elements,
                                             final Transformer<E, K> keyTransformer,
                                             final Transformer<E, V> valueTransformer) {
        final Iterator<? extends E> iter = elements.iterator();
        while (iter.hasNext()) {
            final E temp = iter.next();
            map.put(keyTransformer.transform(temp), valueTransformer.transform(temp));
        }
    }

    /**
     * <p>populateMap.</p>
     *
     * @param map a {@link org.apache.commons.collections4.MultiMap} object.
     * @param elements a {@link java.lang.Iterable} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     */
    public static <K, V> void populateMap(final MultiMap<K, V> map, final Iterable<? extends V> elements,
                                          final Transformer<V, K> keyTransformer) {
        populateMap(map, elements, keyTransformer, TransformerUtils.nopTransformer());
    }

    /**
     * <p>populateMap.</p>
     *
     * @param map a {@link org.apache.commons.collections4.MultiMap} object.
     * @param elements a {@link java.lang.Iterable} object.
     * @param keyTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param valueTransformer a {@link org.apache.commons.collections4.Transformer} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @param <E> a E object.
     */
    public static <K, V, E> void populateMap(final MultiMap<K, V> map, final Iterable<? extends E> elements,
                                             final Transformer<E, K> keyTransformer,
                                             final Transformer<E, V> valueTransformer) {
        final Iterator<? extends E> iter = elements.iterator();
        while (iter.hasNext()) {
            final E temp = iter.next();
            map.put(keyTransformer.transform(temp), valueTransformer.transform(temp));
        }
    }

    /**
     * <p>iterableMap.</p>
     *
     * @param map a {@link java.util.Map} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableMap} object.
     */
    public static <K, V> IterableMap<K, V> iterableMap(final Map<K, V> map) {
        if (map == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        return map instanceof IterableMap ? (IterableMap<K, V>) map : new AbstractMapDecorator<K, V>(map) {
        };
    }

    /**
     * <p>iterableSortedMap.</p>
     *
     * @param sortedMap a {@link java.util.SortedMap} object.
     * @param <K> a K object.
     * @param <V> a V object.
     * @return a {@link org.apache.commons.collections4.IterableSortedMap} object.
     */
    public static <K, V> IterableSortedMap<K, V> iterableSortedMap(final SortedMap<K, V> sortedMap) {
        if (sortedMap == null) {
            throw new IllegalArgumentException("Map must not be null");
        }
        return sortedMap instanceof IterableSortedMap ? (IterableSortedMap<K, V>) sortedMap :
                new AbstractSortedMapDecorator<K, V>(sortedMap) {
                };
    }
}
