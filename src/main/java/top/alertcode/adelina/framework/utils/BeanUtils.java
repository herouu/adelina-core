//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package top.alertcode.adelina.framework.utils;

import java.util.Map;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;

public class BeanUtils extends org.springframework.beans.BeanUtils {
    private static Logger log = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    public static <T> T getPropertyValue(Object source, String filedName) {
        try {
            T t = (T) Ognl.getValue(filedName, source);
            return t;
        } catch (OgnlException var3) {
            log.error("className:{}, 设置属性名：{}", source.getClass().getName(), filedName);
            throw new FrameworkUtilException(var3);
        }
    }

    public static void setPropertyValue(Object source, String filedName, Object value) {
        try {
            Ognl.setValue(filedName, source, value);
        } catch (OgnlException var4) {
            log.error("className:{}, 设置属性名：{}", source.getClass().getName(), filedName);
            throw new FrameworkUtilException(var4);
        }
    }

    public static <T> T mapToBean(Map<String, Object> map, Class<T> classType) {
        Object bean = null;

        try {
            bean = classType.newInstance();
            org.apache.commons.beanutils.BeanUtils.populate(bean, map);
            return (T) bean;
        } catch (Exception var4) {
            throw new FrameworkUtilException(var4);
        }
    }

    public static Map<String, String> beanToMap(Object bean) {
        try {
            ConvertUtilsBean convertUtils = BeanUtilsBean.getInstance().getConvertUtils();
            DateConverter dateConverter = new DateConverter();
            dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
            convertUtils.register(dateConverter, String.class);
            return org.apache.commons.beanutils.BeanUtils.describe(bean);
        } catch (Exception var3) {
            throw new FrameworkUtilException(var3);
        }
    }
}
