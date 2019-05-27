
package top.alertcode.adelina.framework.controller;


import org.springframework.http.HttpStatus;
import top.alertcode.adelina.framework.responses.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SuperController
 *
 * @author Caratacus
 */
public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    /**
     * 成功返回
     *
     * @param object
     * @return
     */
    public <T> JsonResponse<T> success(T object) {
        return JsonResponse.<T>success(response, object);
    }

    /**
     * 成功返回
     *
     * @return
     */
    public JsonResponse<Void> success() {
        return success(HttpStatus.OK);
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     * @return
     */
    public <T> JsonResponse<T> success(HttpStatus status, T object) {
        return JsonResponse.<T>success(response, status, object);
    }


    /**
     * 成功返回
     *
     * @param status
     * @return
     */
    public JsonResponse<Void> success(HttpStatus status) {
        return JsonResponse.<Void>success(response, status);
    }


}
