
package top.alertcode.adelina.framework.responses;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 失败返回
 *
 * @author Bob
 * @version $Id: $Id
 */
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class FailedResponse extends JsonResponse {

    private static final long serialVersionUID = 1L;
    /**
     * http 状码
     */
    private Integer status;
    /**
     * 错误状态码
     */
    private String error;
    /**
     * 错误描述
     */
    private String msg;
    /**
     * 异常信息
     */
    private String exception;
    /**
     * 客户端是否展
     */
    private Boolean show;
    /**
     * 当前时间
     */
    private LocalDateTime time;

}
