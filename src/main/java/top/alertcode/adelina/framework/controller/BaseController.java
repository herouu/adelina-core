
package top.alertcode.adelina.framework.controller;


import org.springframework.http.HttpStatus;
import top.alertcode.adelina.framework.responses.JsonResponse;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SuperController
 *
 * @author Bob
 * @version $Id: $Id
 */
public class BaseController {

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


}
