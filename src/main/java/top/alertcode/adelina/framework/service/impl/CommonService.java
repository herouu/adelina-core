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
import java.util.stream.Collectors;

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


    private final static String QUERY = "query";
    private final static String DELETE = "delete";


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
        final String lockKey = QUERY + getHName(clazz) + id;
        try {
            lock = mapLock.putIfAbsent(lockKey, "true") == null;
            if (lock) {
                return getT(id, getHName(clazz));
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
    @Override
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
            return getT(id, getHName(clazz));
        } finally {
            lock.unlock();
        }
    }

    private <T> T getT(Serializable id, String hName) {
        T t = (T) super.getById(id);
        if (Objects.isNull(t)) {
            return null;
        }
        log.info("从数据库中取数据：线程={}", Thread.currentThread().getId());
        tableCacheDao.add(hName, getId(t), JsonUtils.writeValueAsString(t));
        return t;
    }

    @Override
    public <T> T cacheInsertData(T entity) {
        super.save(entity);
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JsonUtils.writeValueAsString(entity));
        return entity;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean cacheDeleteById(Class clazz, Serializable id) {
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            log.info("删除缓存，线程：{}", Thread.currentThread().getId());
            tableCacheDao.delete(getHName(clazz), id.toString());
            log.info("删除数据库，线程：{}", Thread.currentThread().getId());
            return super.removeById(id);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean cacheUpdateById(Object entity) {
        if (tableCacheDao.exists(getHName(entity.getClass()), getId(entity))) {
            boolean b = super.updateById(entity);
            tableCacheDao.delete(getHName(entity.getClass()), getId(entity));
            tableCacheDao.add(getHName(entity.getClass()), getId(entity), JsonUtils.writeValueAsString(entity));
            return b;
        }
        return false;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized <T> boolean cacheSaveBatch(Collection<T> entityList) {
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
    public synchronized <T> boolean cacheRemove(Class<T> clazz, Wrapper<T> queryWrapper) {
        List list = super.list(queryWrapper);
        Collection collect = CollectionUtils.collect(list, this::getId);
        if (CollectionUtils.isNotEmpty(collect)) {
            cacheDeleteByIds(clazz, collect);
        }
        return true;
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    @Override
    public synchronized <T> boolean cacheDeleteByIds(Class<T> clazz, Collection<? extends Serializable> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            List<T> list = tableCacheDao.mutiGet(getHName(clazz),
                    idList.stream().map(Objects::toString).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(list)) {
                tableCacheDao.delete(getHName(clazz),
                        list.stream().map(this::getId).collect(Collectors.toSet()).toArray(new String[]{}));
                return super.removeByIds(idList);
            }
        }
        return false;
    }


    /**
     * 根据 whereEntity 条件，更新记录
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    @Override
    public synchronized <T> void cacheUpdate(T entity, Wrapper<T> updateWrapper) {
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
    public synchronized <T> void cacheUpdateBatchById(Collection<T> entityList) {
        HashMap<String, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(entityList)) {
            super.updateBatchById(entityList);
            cacheTbUpdateBatch(entityList, map);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized <T> void cacheTbUpdateBatch(Collection<T> entityList, HashMap<String, String> map) {
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
