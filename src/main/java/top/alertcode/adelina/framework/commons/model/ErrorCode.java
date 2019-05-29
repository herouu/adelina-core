
package top.alertcode.adelina.framework.commons.model;

import lombok.*;

/**
 * 业务异常�?
 *
 * @author Bob
 * @version $Id: $Id
 */
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ErrorCode {

    /**
     * 错误
     */
    private String error;
    /**
     * http状�?�码
     */
    private int httpCode;
    /**
     * 是否展示
     */
    private boolean show;
    /**
     * 错误消息
     */
    private String msg;

}
