
package top.alertcode.adelina.framework.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import top.alertcode.adelina.framework.commons.enums.OperateEnum;
import top.alertcode.adelina.framework.commons.model.Params;
import top.alertcode.adelina.framework.responses.JsonResponse;
import top.alertcode.adelina.framework.service.impl.BaseService;
import top.alertcode.adelina.framework.utils.ArrayUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * SuperController
 *
 * @author Bob
 * @version $Id: $Id
 */
@Slf4j
public class BaseController<M extends BaseService<T>, T> {

    /**
     * The Request.
     */
    @Resource
    protected HttpServletRequest request;


    /**
     * The Response.
     */
    @Resource
    protected HttpServletResponse response;


    @Resource
    ApplicationContext applicationContext;
    /**
     * 成功返回
     *
     * @param <T>    the type parameter
     * @param object the object
     * @return JsonResponse json response
     */
    @SuppressWarnings("JavaDoc")
    public <T> JsonResponse<T> jsonData(T object) {
        return JsonResponse.success(response, object);
    }

    /**
     * 成功返回
     *
     * @return JsonResponse json response
     */
    public JsonResponse<Void> jsonData() {
        return jsonData(HttpStatus.OK);
    }

    /**
     * 成功返回
     *
     * @param <T>    the type parameter
     * @param status the status
     * @param object the object
     * @return JsonResponse json response
     */
    public <T> JsonResponse<T> jsonData(HttpStatus status, T object) {
        return JsonResponse.success(response, status, object);
    }


    /**
     * 成功返回
     *
     * @param status the status
     * @return JsonResponse json response
     */
    public JsonResponse<Void> jsonData(HttpStatus status) {
        return JsonResponse.success(response, status);
    }


    private M getBean() {
        Class<M> clazz = (Class<M>) ReflectionKit.getSuperClassGenericType(getClass(), 0);
        return applicationContext.getBean(StringUtils.firstToLowerCase(clazz.getSimpleName()), clazz);
    }


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

    private QueryWrapper<T> gettQueryWrapper(@RequestBody Map<String, Params> object) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        Set<String> keys = object.keySet();
        keys.forEach(item -> buildQueryCondition(object, wrapper, item));
        return wrapper;
    }

    private void buildQueryCondition(Map<String, Params> object, QueryWrapper<T> wrapper, String item) {
        Params params = object.get(item);
        String dbColumn = StringUtils.camelToUnderline(item);
        OperateEnum operateEnum = OperateEnum.getOperateEnum(params.getType());
        if (OperateEnum.IN == operateEnum && ArrayUtils.isEmpty(params.getValues())) {
            return;
        } else if (StringUtils.isEmpty(params.getValue())) {
            return;
        }
        switch (operateEnum) {
            case EQ:
                wrapper.eq(dbColumn, params.getValue());
                break;
            case GE:
                wrapper.ge(dbColumn, params.getValue());
                break;
            case GT:
                wrapper.gt(dbColumn, params.getValue());
                break;
            case IN:
                wrapper.in(dbColumn, params.getValues());
                break;
            case LE:
                wrapper.le(dbColumn, params.getValue());
                break;
            case LT:
                wrapper.lt(dbColumn, params.getValue());
                break;
            case NE:
                wrapper.ne(dbColumn, params.getValue());
            case LIKE:
                wrapper.like(dbColumn, params.getValue());
                break;
            case LlIKE:
                wrapper.likeLeft(dbColumn, params.getValue());
                break;
            case RlIKE:
                wrapper.likeRight(dbColumn, params.getValue());
                break;
            default:
        }
    }
}
