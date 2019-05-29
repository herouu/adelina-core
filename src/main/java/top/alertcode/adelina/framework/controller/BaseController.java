
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
@SuppressWarnings("ALL")
public class BaseController {

    @Resource
    protected HttpServletRequest request;

    @Resource
    protected HttpServletResponse response;

    /**
     * 成功返回
     *
     * @param object
     * @return JsonResponse 
     */
    @SuppressWarnings("JavaDoc")
    public <T> JsonResponse<T> success(T object) {
        return JsonResponse.success(response, object);
    }

    /**
     * 成功返回
     *
     * @return JsonResponse
     */
    public JsonResponse<Void> success() {
        return success(HttpStatus.OK);
    }

    /**
     * 成功返回
     *
     * @param status
     * @param object
     * @return JsonResponse  
     */
    public <T> JsonResponse<T> success(HttpStatus status, T object) {
        return JsonResponse.success(response, status, object);
    }


    /**
     * 成功返回
     *
     * @param status
     * @return JsonResponse
     */
    public JsonResponse<Void> success(HttpStatus status) {
        return JsonResponse.success(response, status);
    }


}
