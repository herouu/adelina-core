
package top.alertcode.adelina.framework.utils;

import com.alibaba.fastjson.util.TypeUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import top.alertcode.adelina.framework.commons.enums.ErrorCodeEnum;
import top.alertcode.adelina.framework.commons.model.ErrorCode;
import top.alertcode.adelina.framework.responses.FailedResponse.FailedResponseBuilder;
import top.alertcode.adelina.framework.responses.JsonResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

/**
 * <p>Abstract ResponseUtils class.</p>
 *
 * @author alertcode
 * @version $Id: $Id
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@SuppressWarnings("all")
public class ResponseUtils {

    /**
     * <p>sendFail.</p>
     *
     * @param code a {@link top.alertcode.adelina.framework.commons.model.ErrorCode} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    public static JsonResponse sendFail(ErrorCode code) {
        return sendFail(code, null);
    }


    /**
     * <p>sendFail.</p>
     *
     * @param codeEnum a {@link top.alertcode.adelina.framework.commons.enums.ErrorCodeEnum} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    public static JsonResponse sendFail(ErrorCodeEnum codeEnum) {
        return sendFail(codeEnum.convert(), null);
    }


    /**
     * <p>sendFail.</p>
     *
     * @param codeEnum a {@link top.alertcode.adelina.framework.commons.enums.ErrorCodeEnum} object.
     * @param exception a {@link java.lang.Exception} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    public static JsonResponse sendFail(ErrorCodeEnum codeEnum, Exception exception) {
        return sendFail(codeEnum.convert(), exception);
    }


    /**
     * <p>sendFail.</p>
     *
     * @param code a {@link top.alertcode.adelina.framework.commons.model.ErrorCode} object.
     * @param exception a {@link java.lang.Exception} object.
     * @return a {@link top.alertcode.adelina.framework.responses.JsonResponse} object.
     */
    public static JsonResponse sendFail(ErrorCode code, Exception exception) {
        return JsonResponse.failure(code, exception);
    }

    /**
     * <p>exceptionMsg.</p>
     *
     * @param failedResponseBuilder a {@link top.alertcode.adelina.framework.responses.FailedResponse.FailedResponseBuilder} object.
     * @param exception a {@link java.lang.Exception} object.
     * @return a {@link top.alertcode.adelina.framework.responses.FailedResponse.FailedResponseBuilder} object.
     */
    public static FailedResponseBuilder exceptionMsg(FailedResponseBuilder failedResponseBuilder, Exception exception) {
        if (exception instanceof MethodArgumentNotValidException) {
            StringBuilder builder = new StringBuilder("校验失败:");
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
            allErrors.stream().findFirst().ifPresent(error -> {
                builder.append(((FieldError) error).getField()).append("字段规则:").append(error.getDefaultMessage());
                failedResponseBuilder.msg(error.getDefaultMessage());
            });
            failedResponseBuilder.exception(builder.toString());
            return failedResponseBuilder;
        } else if (exception instanceof MissingServletRequestParameterException) {
            StringBuilder builder = new StringBuilder("参数字段");
            MissingServletRequestParameterException ex = (MissingServletRequestParameterException) exception;
            builder.append(ex.getParameterName());
            builder.append("校验不通过");
            failedResponseBuilder.exception(builder.toString()).msg(ex.getMessage());
            return failedResponseBuilder;
        } else if (exception instanceof MissingPathVariableException) {
            StringBuilder builder = new StringBuilder("路径字段");
            MissingPathVariableException ex = (MissingPathVariableException) exception;
            builder.append(ex.getVariableName());
            builder.append("校验不通过");
            failedResponseBuilder.exception(builder.toString()).msg(ex.getMessage());
            return failedResponseBuilder;
        } else if (exception instanceof ConstraintViolationException) {
            StringBuilder builder = new StringBuilder("方法.参数字段");
            ConstraintViolationException ex = (ConstraintViolationException) exception;
            Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
            if (first.isPresent()) {
                ConstraintViolation<?> constraintViolation = first.get();
                builder.append(constraintViolation.getPropertyPath().toString());
                builder.append("校验不通过");
                failedResponseBuilder.exception(builder.toString()).msg(constraintViolation.getMessage());
            }
            return failedResponseBuilder;
        }

        failedResponseBuilder.exception(TypeUtils.castToString(exception));
        return failedResponseBuilder;
    }

}
