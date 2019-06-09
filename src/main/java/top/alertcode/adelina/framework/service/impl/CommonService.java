package top.alertcode.adelina.framework.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.alertcode.adelina.framework.cache.TableCacheDao;
import top.alertcode.adelina.framework.commons.enums.Model;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;
import top.alertcode.adelina.framework.exception.ProjectException;
import top.alertcode.adelina.framework.mapper.BaseMapper;
import top.alertcode.adelina.framework.service.IBaseCacheService;
import top.alertcode.adelina.framework.utils.CollectionUtils;
import top.alertcode.adelina.framework.utils.JsonUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author fuqiang // TODO: 2019/6/4 未考虑并发场景
 * @date 2019-06-03
 * @copyright fero.com.cn
 */
@Service
@Slf4j
public class CommonService extends BaseService implements BeanFactoryAware, IBaseCacheService {

    private static BeanFactory beanFactory;

    @Autowired
    protected BaseMapper baseMapper;
    @Autowired
    private TableCacheDao tableCacheDao;

    ReentrantLock lock = new ReentrantLock();

    private ConcurrentHashMap<Serializable, String> mapLock = new ConcurrentHashMap();


    @Override
    public void setBeanFactory(BeanFactory bFactory) throws BeansException {
        if (beanFactory == null && bFactory != null) {
            CommonService.beanFactory = bFactory;
        }
    }


    @Override
    public <T> T cacheGetById(Class<T> clazz, Serializable id) {
        return cacheGetByIdSegmentLock(clazz, id);
    }

    private <T> T cacheGetByIdSegmentLock(Class<T> clazz, Serializable id) {
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            log.info("从缓存中取数据：线程={}", Thread.currentThread().getId());
            return JsonUtils.readValue(tableCacheDao.get(getHName(clazz), Objects.toString(id)), clazz);
        }
        boolean lock = false;
        final String lockKey = getHName(clazz) + id;
        try {
            lock = mapLock.putIfAbsent(lockKey, "true") == null;
            if (lock) {
                T t = (T) super.getById(id);
                log.info("从数据库中取数据：线程={}", Thread.currentThread().getId());
                tableCacheDao.add(getHName(clazz), getId(t), JsonUtils.writeValueAsString(t));
                return t;
            } else {
                log.info("没拿到lock 降级：{}", Thread.currentThread().getId());
                throw new ProjectException("当前请求忙，请稍后再试");
            }
        } finally {
            if (lock) {
                mapLock.remove(lockKey);
            }
        }
    }

    /**
     * @param clazz
     * @param id
     * @param model SegmentLock 分段锁 ReentrantLock 重入锁
     * @param <T>
     * @return
     */
    public <T> T cacheGetById(Class<T> clazz, Serializable id, Model model) {
        if (model == Model.SegmentLock) {
            return cacheGetByIdSegmentLock(clazz, id);
        }
        return cacheGetByIdLock(clazz, id);
    }

    private <T> T cacheGetByIdLock(Class<T> clazz, Serializable id) {
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            log.info("从缓存中取数据：线程={}", Thread.currentThread().getId());
            return JsonUtils.readValue(tableCacheDao.get(getHName(clazz), Objects.toString(id)), clazz);
        }
        lock.lock();
        try {
            if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
                log.info("从缓存中取数据：线程={}", Thread.currentThread().getId());
                return JsonUtils.readValue(tableCacheDao.get(getHName(clazz), Objects.toString(id)), clazz);
            }
            T t = (T) super.getById(id);
            log.info("从数据库中取数据：线程={}", Thread.currentThread().getId());
            tableCacheDao.add(getHName(clazz), getId(t), JsonUtils.writeValueAsString(t));
            return t;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public <T> T cacheInsertData(T entity) {
        super.save(entity);
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JsonUtils.writeValueAsString(entity));
        return entity;
    }


    @Override
    @Transactional
    public boolean cacheDeleteById(Class clazz, Serializable id) {
        tableCacheDao.delete(getHName(clazz), id.toString());
        return super.removeById(id);
    }

    @Override
    @Transactional
    public boolean cacheUpdateById(Object entity) {
        boolean b = super.updateById(entity);
        tableCacheDao.delete(getHName(entity.getClass()), getId(entity));
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JsonUtils.writeValueAsString(entity));
        return b;
    }


    @Override
    @Transactional
    public <T> boolean cacheSaveBatch(Collection<T> entityList) {
        super.saveBatch(entityList);
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            for (Object o : entityList) {
                map.put(getId(o), JsonUtils.writeValueAsString(o));
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
    @Transactional
    public <T> void cacheTbUpdateBatch(Collection<T> entityList, HashMap<String, String> map) {
        for (T t : entityList) {
            map.put(getId(t), JsonUtils.writeValueAsString(entityList));
        }
        String[] str = null;
        tableCacheDao.delete(getHName(entityList.getClass()), map.keySet().toArray(str));
        tableCacheDao.addAll(getHName(entityList.getClass()), map);
    }


//    region


    private String getId(Object entity) {
        try {
            return BeanUtils.getProperty(entity, "id");
        } catch (Exception e) {
            throw new FrameworkUtilException("getId method exception");
        }
    }

    private String getHName(Class clazz) {
        return clazz.getSimpleName();
    }


//  endregion

}
