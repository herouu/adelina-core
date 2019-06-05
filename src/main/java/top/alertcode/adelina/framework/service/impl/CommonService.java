package top.alertcode.adelina.framework.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.alertcode.adelina.framework.cache.TableCacheDao;
import top.alertcode.adelina.framework.mapper.BaseMapper;
import top.alertcode.adelina.framework.service.IBaseCacheService;
import top.alertcode.adelina.framework.utils.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author fuqiang // TODO: 2019/6/4 未考虑并发场景
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
@Service
public class CommonService extends BaseService implements BeanFactoryAware, IBaseCacheService {

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


    @Override
    public <T> T cacheGetById(Class<T> clazz, Serializable id) {
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            return (T) tableCacheDao.get(getHName(clazz), Objects.toString(id));
        } else {
            T t = (T) super.getById(id);
            tableCacheDao.add(getHName(clazz), getId(t), JSON.toJSONString(t));
            return t;
        }
    }

    @Override
    public <T> T cacheInsertData(T entity) {
        super.save(entity);
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JSON.toJSONString(entity));
        return entity;
    }


    @Override
    public boolean cacheDeleteById(Class clazz, Serializable id) {
        tableCacheDao.delete(getHName(clazz), id.toString());
        return super.removeById(id);
    }

    @Override
    public boolean cacheUpdateById(Object entity) {
        boolean b = super.updateById(entity);
        tableCacheDao.delete(getHName(entity.getClass()), getId(entity));
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JSON.toJSONString(entity));
        return b;
    }


    @Override
    public <T> boolean cacheSaveBatch(Collection<T> entityList) {
        super.saveBatch(entityList);
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            for (Object o : entityList) {
                map.put(getId(o), JSON.toJSONString(o));
            }
            String name = getHName(CollectionUtils.get(entityList, 0).getClass());
            tableCacheDao.addAll(name, map);
        }
        return true;
    }


    /**
     * 根据 entity 条件，删除记录
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    @Override
    public <T> boolean cacheRemove(Wrapper<T> queryWrapper) {
        List list = super.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            cacheDeleteByIds(list);
        }
        return true;
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    @Override
    public <T> boolean cacheDeleteByIds(Collection<T> idList) {
        boolean b = super.removeByIds(idList);
        if (CollectionUtils.isNotEmpty(idList)) {
            for (T t : idList) {
                tableCacheDao.delete(getHName(t.getClass()), getId(t));
            }
        }
        return b;
    }


    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    @Override
    public <T> void cacheUpdate(T entity, Wrapper<T> updateWrapper) {
        List<T> list = super.list(updateWrapper);
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(list)) {
            super.removeByIds(list);
            cacheTbUpdateBatch(list, map);
        }
    }


    /**
     * 根据ID 批量更新
     */
    @Override
    public <T> void cacheUpdateBatchById(Collection<T> entityList) {
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            super.updateBatchById(entityList);
            cacheTbUpdateBatch(entityList, map);
        }
    }

    @Override
    public <T> void cacheTbUpdateBatch(Collection<T> entityList, HashMap<String, String> map) {
        for (T t : entityList) {
            map.put(getId(t), JSON.toJSONString(entityList));
        }
        String[] str = null;
        tableCacheDao.delete(getHName(entityList.getClass()), map.keySet().toArray(str));
        tableCacheDao.addAll(getHName(entityList.getClass()), map);
    }


//    region


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


//  endregion

}
