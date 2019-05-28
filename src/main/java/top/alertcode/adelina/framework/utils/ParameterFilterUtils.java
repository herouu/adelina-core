package top.alertcode.adelina.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gizmo on 15/12/11.
 */
public final class ParameterFilterUtils {
    private static final Map<String, String> filterMap = new HashMap();

    static {
        filterMap.put("select", "");
        filterMap.put("insert", "");
        filterMap.put("update", "");
        filterMap.put("delete", "");
        filterMap.put("union", "");
        filterMap.put("into", "");
        filterMap.put("load_file", "");
        filterMap.put("outfile", "");
        filterMap.put("script", "");
        filterMap.put("!", "");
        filterMap.put("@", "");
        filterMap.put("#", "");
        filterMap.put("$", "");
        filterMap.put("^", "");
        filterMap.put("*", "");
        filterMap.put("%", "");
        filterMap.put("\\(", "");
        filterMap.put("\\)", "");
        filterMap.put("\\+", "");
        filterMap.put("\\-", "");
        filterMap.put("\\[", "");
        filterMap.put("\\]", "");
        filterMap.put("\\{", "");
        filterMap.put("\\}", "");
        filterMap.put(";", "");
        filterMap.put(":", "");
        filterMap.put("&", "&amp;");
        filterMap.put("<", "&lt;");
        filterMap.put(">", "&gt;");
        filterMap.put("\"", "&quot;");
        filterMap.put("\\'", "&apos;");
        filterMap.put("？", "&iexcl;");
        filterMap.put("?", "&not;");
        filterMap.put(",", "");
        filterMap.put(".", "");
        filterMap.put("/", "");
        filterMap.put("\r", "");
        filterMap.put("\n", "");
    }

    private ParameterFilterUtils() {
        throw new UnsupportedOperationException("不允许创建实例");
    }

    public static String filterParameter(String parameter) {
        if (StringUtils.isEmpty(parameter)) {
            return parameter;
        }

        for (Map.Entry<String, String> entry : filterMap.entrySet()) {
            if (parameter.contains(entry.getKey())) {
                parameter = parameter.replaceAll(entry.getKey(), entry.getValue());
            }
        }
        return parameter;
    }

    //Oracle 和 Mysql中 在like中必须转义的
    private static String[] EscapeChars = new String[]{"/","_","%"};
    static boolean inEscapeChars(Character character){
        String cs = character.toString();
        for(String s:EscapeChars){
            if(s.equals(cs)){
                return true;
            }
        }
        return false;
    }

    /**
     * 转义sql中like使用的字符串
     * @param parameter
     * @return
     */
    public static String escapeLike(String parameter) {
        if (StringUtils.isEmpty(parameter)) {
            return parameter;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : parameter.toCharArray()) {
            if(inEscapeChars(character)){
                stringBuilder.append("/");
            }
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    public static String escapeParameter(String parameter) {
        if (StringUtils.isEmpty(parameter)) {
            return parameter;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : parameter.toCharArray()) {
            stringBuilder.append("/").append(character);
        }
        return stringBuilder.toString();
    }

}
