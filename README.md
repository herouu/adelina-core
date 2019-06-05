## train-framework-core 

![](https://bj.bcebos.com/v1/alertcode-blog/adelina-core.png)

根据开源的一些框架进行整合，只做增强，不做改变，主要方便快速开发。


### 特性
#### application.yml敏感信息的加密
* 利用jasypt进行加密，添加jasypt加密salt

```yaml
jasypt:
  encryptor:
    password: 7nZPf)pMwh
```

形如`ENC(**)`为jasypt的加密后的密文，下例中将mysql、redis的一些配置信息做了加密处理

```yaml
spring:
  datasource:
    url: ENC(YHeZy7uYWls7/xwSY9gnau+68QFs0i4fA7TmZbijkE+oKiimwZVJOt3DOouWEiM8npx7vhzVt5CgcKya/eNamd6FZoL87jU5b2hjDwPgDafwuB3iJwc/UVWapM2kVE5C4St5dK7pmfHeO51slu9WiiF2vH4/cqJINrDdNx305jI=)
    username: ENC(+8iClaFiWU5/OaZ1zzJPzw==)
    password: ENC(c2oZh4lcJK3Q+w7MSIL2pQ==)
    driver-class-name:  com.mysql.cj.jdbc.Driver # mysql数据库新驱动class
  redis:
    host: ENC(oiy7f0H5Ttd7PnSlyZjSrcxavFjsD01L30SV5LXDXyk=)
    port: 6379
    database: 0
    password: ENC(hgVNi5XVRechSZcGj0utvubfesSQqkJ7)
```

### 缓存 TODO: 暂未考虑缓存的线程安全

&emsp;&emsp;对于分页列表带有条件查询的未进行缓存处理的定义，主要出于以下考虑
* 对于数据量不是很大的情况可以优化数据库索引同样可以满足需求
* 如果数据体量比较大，可以考虑搜索引擎

&emsp;&emsp;以下是缓存的接口，泛型 `<T>`代表数据库实体类：

```java

public interface IBaseCacheService {

    /**
     * 根据实体id获取实体，若缓存不存在更新实体
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T cacheGetById(Class<T> clazz, Serializable id);

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
    <T> boolean cacheSaveBatch(Collection<T> entityList);


    /**
     * 根据 entity 条件，删除数据库及缓存
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    <T> boolean cacheRemove(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）数据库及缓存
     *
     * @param idList 主键ID列表
     */
    <T> boolean cacheDeleteByIds(Collection<T> idList);


    /**
     * 根据 whereEntity 条件，更新数据库及缓存
     *
     * @param entity        实体对象
     * @param updateWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper}
     */
    <T> void cacheUpdate(T entity, Wrapper<T> updateWrapper);


    /**
     * 根据ID 批量更新数据库与缓存
     */
    <T> void cacheUpdateBatchById(Collection<T> entityList);

    /**
     * 批量更新数据库及缓存
     *
     * @param entityList
     * @param map
     * @param <T>
     */
    <T> void cacheTbUpdateBatch(Collection<T> entityList, HashMap<String, String> map);
```

### 分布式锁 
* 基于redisson，使用redisson的
`RLock lock = redisson.getLock("lockName")`

```java
    public void lockDemo() {
            RLock lock = redisson.getLock("lockName");
            try {
                // 1. 最常见的使用方法
                //lock.lock();
                // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
                //lock.lock(10, TimeUnit.SECONDS);
                // 3. 尝试加锁，最多等待2秒，上锁以后8秒自动解锁
                boolean res = lock.tryLock(2, 8, TimeUnit.SECONDS);
                if (res) { //成功
                    //处理业务
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
        }
```
* zookeeper TODO://暂未实现

### 分布式事务 TODO:暂未实现

### 列表分页 

 dao层使用的是[mybatis-plus](),列表分页有两种方式：

*  使用默认的page分页插件
*  使用[PageHelper](https://pagehelper.github.io/)分页插件

```java
@Configuration
public class ApplicationConfig {


    @Bean
    public PageInterceptor paginationInterceptor() {
        return new PageInterceptor();
    }
}
```

* PageHelper使用Demo

```java
@Service
public class UserServiceImpl extends BaseService implements IUserService {


    @Override
    public PageInfo getList() {
        //分页参数        
        PageHelper.startPage(1, 10);
        // 分页数据集合        
        List list = list();
        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
```