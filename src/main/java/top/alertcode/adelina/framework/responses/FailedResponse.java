package top.alertcode.adelina.framework.responses;

import java.time.LocalDateTime;

/**
 * 失败返回
 *
 * @author Bob
 * @version $Id: $Id
 */
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


    @java.lang.SuppressWarnings("all")
    /**
     * <p>Constructor for FailedResponse.</p>
     *
     * @param status a {@link java.lang.Integer} object.
     * @param error a {@link java.lang.String} object.
     * @param msg a {@link java.lang.String} object.
     * @param exception a {@link java.lang.String} object.
     * @param show a {@link java.lang.Boolean} object.
     * @param time a {@link java.time.LocalDateTime} object.
     */
    public FailedResponse(final Integer status, final String error, final String msg, final String exception, final Boolean show, final LocalDateTime time) {
        this.status = status;
        this.error = error;
        this.msg = msg;
        this.exception = exception;
        this.show = show;
        this.time = time;
    }

    @java.lang.SuppressWarnings("all")
    /**
     * <p>Constructor for FailedResponse.</p>
     */
    public FailedResponse() {
    }

    /**
     * <p>builder.</p>
     *
     * @return a {@link top.alertcode.adelina.framework.responses.FailedResponse.FailedResponseBuilder} object.
     */
    @java.lang.SuppressWarnings("all")
    public static FailedResponseBuilder builder() {
        return new FailedResponseBuilder();
    }

    /**
     * http 状码
     *
     * @return a {@link java.lang.Integer} object.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 错误状态码
     *
     * @return a {@link java.lang.String} object.
     */
    @java.lang.SuppressWarnings("all")
    public String getError() {
        return this.error;
    }

    /**
     * 错误描述
     *
     * @return a {@link java.lang.String} object.
     */
    @java.lang.SuppressWarnings("all")
    public String getMsg() {
        return this.msg;
    }

    /**
     * 异常信息
     *
     * @return a {@link java.lang.String} object.
     */
    @java.lang.SuppressWarnings("all")
    public String getException() {
        return this.exception;
    }

    /**
     * 客户端是否展
     *
     * @return a {@link java.lang.Boolean} object.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getShow() {
        return this.show;
    }

    /**
     * 当前时间
     *
     * @return a {@link java.time.LocalDateTime} object.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getTime() {
        return this.time;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "FailedResponse(status=" + this.getStatus() + ", error=" + this.getError() + ", msg=" + this.getMsg() + ", exception=" + this.getException() + ", show=" + this.getShow() + ", time=" + this.getTime() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof FailedResponse)) return false;
        final FailedResponse other = (FailedResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$error = this.getError();
        final java.lang.Object other$error = other.getError();
        if (this$error == null ? other$error != null : !this$error.equals(other$error)) return false;
        final java.lang.Object this$msg = this.getMsg();
        final java.lang.Object other$msg = other.getMsg();
        if (this$msg == null ? other$msg != null : !this$msg.equals(other$msg)) return false;
        final java.lang.Object this$exception = this.getException();
        final java.lang.Object other$exception = other.getException();
        if (this$exception == null ? other$exception != null : !this$exception.equals(other$exception)) return false;
        final java.lang.Object this$show = this.getShow();
        final java.lang.Object other$show = other.getShow();
        if (this$show == null ? other$show != null : !this$show.equals(other$show)) return false;
        final java.lang.Object this$time = this.getTime();
        final java.lang.Object other$time = other.getTime();
        if (this$time == null ? other$time != null : !this$time.equals(other$time)) return false;
        return true;
    }

    /**
     * <p>canEqual.</p>
     *
     * @param other a {@link java.lang.Object} object.
     * @return a boolean.
     */
    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof FailedResponse;
    }

    /**
     * {@inheritDoc}
     */
    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $error = this.getError();
        result = result * PRIME + ($error == null ? 43 : $error.hashCode());
        final java.lang.Object $msg = this.getMsg();
        result = result * PRIME + ($msg == null ? 43 : $msg.hashCode());
        final java.lang.Object $exception = this.getException();
        result = result * PRIME + ($exception == null ? 43 : $exception.hashCode());
        final java.lang.Object $show = this.getShow();
        result = result * PRIME + ($show == null ? 43 : $show.hashCode());
        final java.lang.Object $time = this.getTime();
        result = result * PRIME + ($time == null ? 43 : $time.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public static class FailedResponseBuilder {
        @java.lang.SuppressWarnings("all")
        private Integer status;
        @java.lang.SuppressWarnings("all")
        private String error;
        @java.lang.SuppressWarnings("all")
        private String msg;
        @java.lang.SuppressWarnings("all")
        private String exception;
        @java.lang.SuppressWarnings("all")
        private Boolean show;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime time;

        @java.lang.SuppressWarnings("all")
        FailedResponseBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder status(final Integer status) {
            this.status = status;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder error(final String error) {
            this.error = error;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder msg(final String msg) {
            this.msg = msg;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder exception(final String exception) {
            this.exception = exception;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder show(final Boolean show) {
            this.show = show;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponseBuilder time(final LocalDateTime time) {
            this.time = time;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public FailedResponse build() {
            return new FailedResponse(status, error, msg, exception, show, time);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "FailedResponse.FailedResponseBuilder(status=" + this.status + ", error=" + this.error + ", msg=" + this.msg + ", exception=" + this.exception + ", show=" + this.show + ", time=" + this.time + ")";
        }
    }
}
