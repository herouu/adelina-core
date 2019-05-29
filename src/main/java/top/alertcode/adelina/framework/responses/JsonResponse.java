package top.alertcode.adelina.framework.responses;

import org.springframework.http.HttpStatus;
import top.alertcode.adelina.framework.commons.model.ErrorCode;
import top.alertcode.adelina.framework.utils.ResponseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * GET: 200 OK
 * POST: 201 Created
 * PUT: 200 OK
 * PATCH: 200 OK
 * DELETE: 204 No Content
 * 接口返回(多态)
 *
 * @author Caratacus
 */
public class JsonResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 不需要返回结果
     *
     * @param status HttpStatus
     */
    public static JsonResponse<Void> success(HttpServletResponse response, HttpStatus status) {
        response.setStatus(status.value());
        return SuccessResponses.<Void>builder().status(status.value()).build();

    }

    /**
     * 成功返回
     *
     * @param object 数据
     */
    public static <T> JsonResponse<T> success(HttpServletResponse response, T object) {
        return success(response, HttpStatus.OK, object);

    }

    /**
     * 成功返回
     *
     * @param status HttpStatus
     * @param object 数据
     */
    public static <T> JsonResponse<T> success(HttpServletResponse response, HttpStatus status, T object) {
        response.setStatus(status.value());
        return SuccessResponses.<T>builder().status(status.value()).result(object).build();

    }

    /**
     * 失败返回
     *
     * @param errorCode ErrorCode
     * @param exception exception
     */
    public static <T> JsonResponse<T> failure(ErrorCode errorCode, Exception exception) {
        return ResponseUtils.exceptionMsg(FailedResponse.builder().msg(errorCode.getMsg()), exception)
                .error(errorCode.getError())
                .show(errorCode.isShow())
                .time(LocalDateTime.now())
                .status(errorCode.getHttpCode())
                .build();
    }

}
