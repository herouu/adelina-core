package top.alertcode.adelina.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gizmo on 15/12/11.
 */
public final class DataBaseUtils {
    private static final Logger log = LoggerFactory.getLogger(DataBaseUtils.class);

    private DataBaseUtils() {
    }

    public static boolean isAddDuplicate(DataSource dataSource, Object entity, String tableName, String... properties) {
        checkParameter(dataSource, entity, tableName, properties);

        Connection connection = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("select count(1) from ").append(tableName).append(" where 1 = 1");
            concatCheckProperties(stringBuilder, entity, properties);

            connection = dataSource.getConnection();
            return checkDuplicate(connection, stringBuilder.toString());
        } catch (Exception e) {
            throw new FrameworkUtilException(e);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    public static boolean isUpdateDuplicate(DataSource dataSource, Object entity, String tableName, String... properties) {
        checkParameter(dataSource, entity, tableName, properties);

        Connection connection = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("select count(1) from ").append(tableName).append(" where id <> ").append(ReflectionUtils.getFieldValue(entity, "id"));
            concatCheckProperties(stringBuilder, entity, properties);

            connection = dataSource.getConnection();
            return checkDuplicate(connection, stringBuilder.toString());
        } catch (Exception e) {
            throw new FrameworkUtilException(e);
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (SQLException e) {
                log.error(ExceptionUtils.getStackTrace(e));
            }
        }
    }

    private static void checkParameter(DataSource dataSource, Object entity, String tableName, String[] properties) {
        AssertUtils.notNull(dataSource, "datasource is null");
        AssertUtils.notNull(entity, "entity is null");
        AssertUtils.notNull(tableName, "tableName is null");
        AssertUtils.notEmpty(properties, "properties is null");
    }

    private static void concatCheckProperties(StringBuilder stringBuilder, Object entity, String[] properties) throws IllegalAccessException {
        for (String property : properties) {
            String convertProperty = removePropertyUnderline(property);
            Field field = ReflectionUtils.getAccessibleField(entity, convertProperty);

            if (field.get(entity) instanceof Number) {
                stringBuilder.append(" and ").append(property).append(" = ").append(ReflectionUtils.getFieldValue(entity, convertProperty));
            } else if (field.get(entity) instanceof Boolean) {
                stringBuilder.append(" and ").append(property).append(" = ").append((Boolean) ReflectionUtils.getFieldValue(entity, convertProperty) ? 1 : 0);
            } else {
                stringBuilder.append(" and ").append(property).append(" = '").append(ReflectionUtils.getFieldValue(entity, convertProperty)).append("'");
            }
        }
    }

    private static String removePropertyUnderline(String property) {
        String[] words = property.split("_");
        String convertProperty = words[0];

        if (words.length >= 2) {
            for (int a = 1; a < words.length; a++) {
                String tmp = words[a];
                tmp = Character.toUpperCase(tmp.charAt(0)) + tmp.substring(1);
                convertProperty += tmp;
            }
        }

        return convertProperty;
    }

    private static Boolean checkDuplicate(Connection connection, String sql) throws SQLException {
        log.info("check duplicate sql: {}", sql);

        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        while (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }

        return false;
    }
}
