package top.alertcode.adelina.framework.exception;


/**
 * The type Framework util exception.
 *
 * @author Bob
 * @version $Id: $Id
 */
public class FrameworkUtilException extends RuntimeException {
    /**
     * Instantiates a new Framework util exception.
     */
    public FrameworkUtilException() {
        super();
    }

    /**
     * Instantiates a new Framework util exception.
     *
     * @param message the message
     */
    public FrameworkUtilException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Framework util exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public FrameworkUtilException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Framework util exception.
     *
     * @param cause the cause
     */
    public FrameworkUtilException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Framework util exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    protected FrameworkUtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
