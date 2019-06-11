package top.alertcode.adelina.framework.service.impl;

import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.alertcode.adelina.framework.cache.TableCacheDao;
import top.alertcode.adelina.framework.commons.constant.PageCons;
import top.alertcode.adelina.framework.commons.enums.Model;
import top.alertcode.adelina.framework.exception.FrameworkUtilException;
import top.alertcode.adelina.framework.exception.ProjectException;
import top.alertcode.adelina.framework.utils.ArrayUtils;
import top.alertcode.adelina.framework.utils.CollectionUtils;
import top.alertcode.adelina.framework.utils.JsonUtils;
import top.alertcode.adelina.framework.utils.NumberUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * <p>
 * 基础Service实现 继承于Mybatis-plus
 * </p>
 *
 * @author Bob
 * @version $Id: $Id
 */
@Slf4j
public class BaseService<T> extends ServiceImpl {
    @Resource
    protected HttpServletRequest request;
    /**
     * <p>getPage.</p>
     *
     * @param <T> a T object.
     * @return a {@link com.baomidou.mybatisplus.extension.plugins.pagination.Page} object.
     */
    protected <T> Page<T> getPage() {
        int index = 1;
        // 页数
        Integer cursor = ObjectUtils.defaultIfNull(TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE)),
                index);
        // 分页大小
        Integer limit = ObjectUtils.defaultIfNull(TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS)),
                PageCons.DEFAULT_LIMIT);
        // 是否查询分页
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;
        Page<T> page = new Page<>(cursor, limit);
        return page;
    }


    @Autowired
    private TableCacheDao tableCacheDao;

    ReentrantLock lock = new ReentrantLock();

    private ConcurrentHashMap<Serializable, String> mapLock = new ConcurrentHashMap();



    private final static String QUERY = "query";
    private final static String DELETE = "delete";


//    public void setBeanFactory(BeanFactory bFactory) throws BeansException {
//        if (beanFactory == null && bFactory != null) {
//            BaseService.beanFactory = bFactory;
//        }
//    }


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

    public <T> T cacheInsertData(T entity) {
        super.save(entity);
        tableCacheDao.add(getHName(entity.getClass()), getId(entity), JsonUtils.writeValueAsString(entity));
        return entity;
    }


    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean cacheDeleteById(Serializable id) {
        Class clazz = ReflectionKit.getSuperClassGenericType(getClass(), 0);
        if (tableCacheDao.exists(getHName(clazz), Objects.toString(id))) {
            log.info("删除缓存，线程：{}", Thread.currentThread().getId());
            tableCacheDao.delete(getHName(clazz), id.toString());
            log.info("删除数据库，线程：{}", Thread.currentThread().getId());
            return super.removeById(id);
        }
        return false;
    }

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


    @Transactional(rollbackFor = Exception.class)
    public synchronized boolean cacheSaveBatch(Collection<T> entityList) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            HashMap<String, String> map = new HashMap<>(16);
            saveBatch(entityList);
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

    public synchronized boolean cacheRemove(Class<T> clazz, Wrapper<T> queryWrapper) {
        List<T> list = list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            Long[] ids =
                    list.stream().map(this::getId).filter(Objects::nonNull).map(NumberUtils::toLong).collect(Collectors.toList()).toArray(new Long[]{});
            if (ArrayUtils.isNotEmpty(ids)) {
                cacheDeleteByIds(clazz, ids);
            }
        }
        return true;
    }

    /**
     * 删除（根据ID 批量删除）
     *
     * @param idList 主键ID列表
     */
    public boolean cacheDeleteByIds(Class<T> clazz, Long[] idList) {
        if (ArrayUtils.isNotEmpty(idList)) {
            List<String> ids = Stream.of(idList).map(Objects::toString).collect(Collectors.toList());
            lock.lock();
            try {
                List<String> collect = tableCacheDao.mutiGet(getHName(clazz), ids)
                        .stream().filter(Objects::nonNull).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    tableCacheDao.delete(getHName(clazz),
                            collect.stream().map(item -> JsonUtils.readValue(item, clazz)).map(this::getId).collect(Collectors.toSet()).toArray(new String[]{}));
                    return super.removeByIds(Stream.of(idList).collect(Collectors.toList()));
                }
            } finally {
                lock.unlock();
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
    @Transactional(rollbackFor = Exception.class)
    public synchronized <T> void cacheUpdate(T entity, Wrapper<T> updateWrapper) {
        update(entity, updateWrapper);
        List<T> list = list(updateWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            tableCacheDao.delete(getHName(entity.getClass()),
                    list.stream().map(this::getId).collect(Collectors.toSet()).toArray(new String[]{}));
            HashMap<String, String> map = new HashMap<>(16);
            for (Object o : list) {
                map.put(getId(o), JsonUtils.writeValueAsString(o));
            }
            tableCacheDao.addAll(getHName(entity.getClass()), map);
        }
    }


    /**
     * 根据ID 批量更新
     */
    public synchronized void cacheUpdateBatchById(Collection<T> entityList) {
        if (CollectionUtils.isNotEmpty(entityList)) {
            super.updateBatchById(entityList);
            Collection<T> collection = listByIds(entityList.stream().map(this::getId).collect(Collectors.toList()));
            T t = CollectionUtils.get(entityList, 0);
            cacheBatchReset(t.getClass(), collection);
        }
    }

    private synchronized void cacheBatchReset(Class<?> clazz, Collection<T> entityList) {
        HashMap<String, String> map = new HashMap<>(16);
        for (T t : entityList) {
            map.put(getId(t), JsonUtils.writeValueAsString(t));
        }
        tableCacheDao.delete(getHName(clazz), map.keySet().toArray(new String[]{}));
        tableCacheDao.addAll(getHName(clazz), map);
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
