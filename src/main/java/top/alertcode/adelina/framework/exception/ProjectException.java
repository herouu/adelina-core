package top.alertcode.adelina.framework.exception;

/**
 * <p>
 * Crown异常�?
 * </p>
 *
 * @author Bob
 * @version $Id: $Id
 */
public class ProjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * <p>Constructor for ProjectException.</p>
     *
     * @param message a {@link java.lang.String} object.
     */
    public ProjectException(String message) {
        super(message);
    }

    /**
     * <p>Constructor for ProjectException.</p>
     *
     * @param throwable a {@link java.lang.Throwable} object.
     */
    public ProjectException(Throwable throwable) {
        super(throwable);
    }

    /**
     * <p>Constructor for ProjectException.</p>
     *
     * @param message   a {@link java.lang.String} object.
     * @param throwable a {@link java.lang.Throwable} object.
     */
    public ProjectException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
