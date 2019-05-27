package top.alertcode.adelina.framework.exception;

/**
 * <p>
 * Crown异常类
 * </p>
 *
 * @author Caratacus
 */
public class ProjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(Throwable throwable) {
        super(throwable);
    }

    public ProjectException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
