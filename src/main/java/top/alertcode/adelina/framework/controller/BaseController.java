
package top.alertcode.adelina.framework.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import top.alertcode.adelina.framework.responses.JsonResponse;
import top.alertcode.adelina.framework.service.impl.BaseService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * SuperController
 *
 * @author Bob
 * @version $Id: $Id
 */
public class BaseController<T> {

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


    @Resource
    protected BaseService baseService;


    @GetMapping("/getById")
    public JsonResponse getById(@RequestParam Long id) {
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        Class c = (Class) p.getActualTypeArguments()[0];
        return jsonData(baseService.cacheGetById(c, id));
    }

    @PutMapping("/insertData")
    public JsonResponse insertData(@RequestBody T obj) {
        return jsonData(baseService.cacheInsertData(obj));
    }

    @PutMapping("/deleteById")
    public JsonResponse deleteById(@RequestParam String id) {
        Class clazz = this.getClass();
        Type type = clazz.getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) type;
        Class c = (Class) p.getActualTypeArguments()[0];
        return jsonData(baseService.cacheDeleteById(c, id));
    }

    @PutMapping("/updateById")
    public JsonResponse updateById(@RequestBody T obj) {
        return jsonData(baseService.updateById(obj));
    }
}
