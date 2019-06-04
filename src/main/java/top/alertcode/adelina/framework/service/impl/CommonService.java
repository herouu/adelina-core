package top.alertcode.adelina.framework.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.alertcode.adelina.framework.cache.TableCacheDao;
import top.alertcode.adelina.framework.mapper.BaseMapper;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * @author fuqiang // TODO: 2019/6/4 未考虑并发场景 
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
@Service
public class CommonService extends BaseService implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Autowired
    protected BaseMapper baseMapper;
    @Autowired
    private TableCacheDao tableCacheDao;


    @Override
    public void setBeanFactory(BeanFactory bFactory) throws BeansException {
        if (beanFactory == null && bFactory != null) {
            CommonService.beanFactory = bFactory;
        }
    }


    public <T> T getById(Class<T> clazz, Serializable id) {
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            return (T) tableCacheDao.get(getHName(clazz), Objects.toString(id));
        } else {
            Object byId = super.getById(id);
            if (Objects.isNull(byId)) {
                return null;
            }
            T t = (T) byId;
            tableCacheDao.add(getHName(clazz), getId(t), JSON.toJSONString(t));
            return t;
        }
    }

    public <T> T insertData(T entity) {
        super.save(entity);
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JSON.toJSONString(entity));
        return entity;
    }


    public boolean deleteById(Class clazz, Serializable id) {
        tableCacheDao.delete(getHName(clazz), id.toString());
        return super.removeById(id);
    }

    @Override
    public boolean updateById(Object entity) {
        boolean b = super.updateById(entity);
        tableCacheDao.delete(getHName(entity.getClass()), getId(entity));
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JSON.toJSONString(entity));
        return b;
    }


    private String getId(Object entity) {
        try {
            return BeanUtils.getProperty(entity, "id");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getHName(Class clazz) {
        return clazz.getSimpleName();
    }

}
