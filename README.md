
<div width="100%" align="center">
    <img src='https://raw.githubusercontent.com/alertcode/adelina-core/master/doc/img/adelina-core.png'/>
</div>


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
 datasource:
    url: ENC(rax4pFsibTzq+Gwwr4zom4mR1fK3HMMwLuFQYRHinrMkM1j0nwBDTi9XIVRkstB6jlFvLm8eY+W4M8lE1mQ2ZWCm60KlAmC1UN84x+E/r1CX2kLQh1Tutw3C4oExeGATTNoXAkeFqo/5ReUZ5nX9cg==)
    username: ENC(vunokJzoW3WYocMPrhbUrA==)
    password: ENC(E7WcA36p0hEAo+86ixtl2g==)
    driver-class-name:  com.mysql.cj.jdbc.Driver
  redis:
    host: ENC(xVyDwkDlZh+aY2OJ9G2CN+niaGr85SvK)
    port: 6379
    database: 0
    password:
```

### 单元测试 使用junit的超集，testNG

* 满足接口的并发测试，使用testNG测试框架
* 使用

```java

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest extends AbstractTestNGSpringContextTests {


}
```

### 缓存

&emsp;&emsp;考虑并发场景下的缓存与数据库的问题，预防缓存雪崩（暂未考虑缓存击穿下的布隆过滤器）
&emsp;&emsp;对于分页列表带有条件查询的未进行缓存处理的定义，主要出于以下考虑
* 对于数据量不是很大的情况可以优化数据库索引同样可以满足需求
* 如果数据体量比较大，可以考虑搜索引擎

&emsp;&emsp;以下是缓存的接口，泛型 `<T>`代表数据库实体类：

```java
public interface IBaseCacheService<T> {

    /**
     * 根据实体id获取实体，若缓存不存在更新实体
     *
     * @param id
     * @return
     */
    <T> T cacheGetById(Serializable id);


    /**
     * 根据实体id获取实体，若缓存不存在更新实体
     *
     * @param id
     * @param model 锁类型 分段锁->缓存熔断 重入锁->线程阻塞
     * @param <T>
     * @return
     */
    <T> T cacheGetById(Serializable id, Model model);

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
     * @param id
     * @return
     */
    boolean cacheDeleteById(Serializable id);

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
     * @return
     */
    boolean cacheSaveBatch(Collection<T> entityList);


    /**
     * 根据 entity 条件，删除数据库及缓存
     *
     * @param queryWrapper 实体包装类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */

    boolean cacheRemove(Wrapper<T> queryWrapper);

    /**
     * 删除（根据ID 批量删除）数据库及缓存
     *
     * @param idList 主键ID列表
     */
    boolean cacheDeleteByIds(Long[] idList);


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

}
```

### 分布式锁 

* 基于redisson，使用redisson的
`RLock lock = redisson.getLock("lockName")`

```java
    @Test
    public void lockDemo() {
        RLock lock = redissonClient.getLock(StringUtils.getLockKey("123"));
        try {
            // 1. 最常见的使用方法
            lock.lock();
            // 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
            //lock.lock(10, TimeUnit.SECONDS);
            // 3. 尝试加锁，最多等待2秒，上锁以后8秒自动解锁
            boolean res = lock.tryLock(2, 8, TimeUnit.SECONDS);
            if (res) { //成功
                //处理业务
            }
//            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }

```

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
### BaseController 提供6个默认的方法 

&emsp;&emsp;对于简单的单表业务，可以直接使用，对于复杂的业务需要自定义接口或重写接口

```java
   /**
     * <p>getById.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @GetMapping("/getById")
    public JsonResponse getById(@RequestParam Long id) {
        Object o = getBean().cacheGetById(id);
        return jsonData(o);
    }

    /**
     * <p>insertData.</p>
     *
     * @param obj a T object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @PostMapping("/insertData")
    public JsonResponse insertData(@RequestBody T obj) {
        return jsonData(getBean().cacheInsertData(obj));
    }

    /**
     * <p>deleteById.</p>
     *
     * @param id a {@link java.lang.String} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @DeleteMapping("/deleteById")
    public JsonResponse deleteById(@RequestParam String id) {
        return jsonData(getBean().cacheDeleteById(id));
    }

    /**
     * <p>updateById.</p>
     *
     * @param obj a T object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @PutMapping("/updateById")
    public JsonResponse updateById(@RequestBody T obj) {
        return jsonData(getBean().updateById(obj));
    }

    /**
     * <p>allList.</p>
     *
     * @param object a {@link java.util.Map} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @PostMapping("/allList")
    public JsonResponse allList(@RequestBody Map<String, Params> object) {
        QueryWrapper<T> wrapper = gettQueryWrapper(object);
        return jsonData(getBean().list(wrapper));
    }

    /**
     * <p>pageList.</p>
     *
     * @param pageNum  a {@link java.lang.Integer} object.
     * @param pageSize a {@link java.lang.Integer} object.
     * @param object   a {@link java.util.Map} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    @PostMapping("/pageList")
    public JsonResponse pageList(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false)
            Integer pageSize, @RequestBody Map<String, Params> object) {
        QueryWrapper<T> wrapper = gettQueryWrapper(object);
        return jsonData(getBean().pageList(wrapper));
    }
    
```

### 代码生成器
#### 接口

```java
    /**
     * 代码生成器
     *
     * @param author            the author 作者名称
     * @param modelName         the model name 模块名称
     * @param tableNames        the table names 表名 多个表'，'分割
     * @param parentPackageName the parent package name 包名
     * @param env               the env  包路径所在环境  dev or test
     * @param entityPackage     the entityPackage  自定义entityPackage包名称
     */
    public void exec(String author, final String modelName, String tableNames, String parentPackageName, String env,
                     String entityPackage);
```
#### 使用jRebel 无需重启项目，即可更新接口  

#### 使用

```java
 @Autowired
 private CodeGenerator codeGenerator;   
    
 @Test
    public void testExec() {
        codeGenerator.exec("alert", "user", "sys_user", "top.alertcode.adelina.framework", "test");
    }

```

### 分布式事务 todo 
目前没有什么实际应用的场景，暂不整合。不了解分布式事务的请参考这篇文章【[再有人问你分布式事务，把这篇扔给他
](https://juejin.im/post/5bbb0d8df265da0abd3533a5)】,有需要请关注这个大厂开源项目【[seata](https://github.com/seata/seata)】
 