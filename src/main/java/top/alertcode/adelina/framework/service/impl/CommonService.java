package top.alertcode.adelina.framework.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.alertcode.adelina.framework.mapper.BaseMapper;

import java.io.Serializable;

/**
 * @author fuqiang
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
@Service
public class CommonService extends BaseService implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    @Autowired
    protected BaseMapper baseMapper;


    @Override
    public void setBeanFactory(BeanFactory bFactory) throws BeansException {
        if (beanFactory == null && bFactory != null) {
            CommonService.beanFactory = bFactory;
        }
    }


    public <T> T getById(Class<T> clazz, Serializable id) {
        return (T) super.getById(id);
    }


    public <T> T insertData(T entity) {
        super.save(entity);
        return entity;
    }

    public boolean deleteById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean updateById(Object entity) {
        return super.updateById(entity);
    }

}
