package top.alertcode.adelina.framework.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import top.alertcode.adelina.framework.commons.enums.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * cache层接口
 *
 * @author fuqiang
 * @date 2019-06-05
 * @copyright fero.com.cn
 */
public interface IBaseCacheService<T> {

    /**
     * 根据实体id获取实体，若缓存不存在更新实体
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    T cacheGetById(Class<T> clazz, Serializable id);


    /**
     * 根据实体id获取实体，若缓存不存在更新实体
     *
     * @param clazz
     * @param id
     * @param model 锁类型 分段锁->缓存熔断 重入锁->线程阻塞
     * @param <T>
     * @return
     */
    <T> T cacheGetById(Class<T> clazz, Serializable id, Model model);

    /**
     * 添加实体并添加缓存
     *
     * @param entity
     * @param <T>
     * @return
     */
    <T> T cacheInsertData(T entity);

    /**
     * 根据id 删除缓存及数据库
     *
     * @param clazz
     * @param id
     * @return
     */
    boolean cacheDeleteById(Class clazz, Serializable id);

    /**
     * 根据id更新缓存及数据库
     * <p>
     *
     * @return
     */
    boolean cacheUpdateById(Object entity);

    /**
     * 批量保存 数据库及缓存
     *
     * @param entityList
     * @param <T>
     * @return
     */
    boolean cacheSaveBatch(Collection<T> entityList);


    /**
     * 根据 entity 条件，删除数据库及缓存
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    boolean cacheRemove(Class<T> clazz, Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）数据库及缓存
     *
     * @param idList 主键ID列表
     */
    boolean cacheDeleteByIds(Class<T> clazz, Long[] idList);


    /**
     * 根据 whereEntity 条件，更新数据库及缓存
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    void cacheUpdate(T entity, Wrapper<T> updateWrapper);


    /**
     * 根据ID 批量更新数据库与缓存
     */
    void cacheUpdateBatchById(Collection<T> entityList);

    /**
     * 批量更新数据库及缓存
     *
     * @param entityList
     * @param map
     * @param <T>
     */
    void cacheTbUpdateBatch(Collection<T> entityList, HashMap<String, String> map);
}
