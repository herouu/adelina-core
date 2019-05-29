package top.alertcode.adelina.framework.responses;

import lombok.*;

/**
 * 成功返回
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
public class SuccessResponses<T> extends JsonResponse<T> {

    private static final long serialVersionUID = 1L;
    /**
     * http 状�?�码
     */
    private Integer status;
    /**
     * 结果集返�?
     */
    private T result;

}
