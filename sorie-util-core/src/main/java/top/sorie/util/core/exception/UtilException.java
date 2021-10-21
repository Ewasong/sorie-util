package top.sorie.util.core.exception;

/**
 * 工具异常
 * @author soriee
 * @date 2021/10/21 22:59
 */
public class UtilException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UtilException(Throwable e) {
        super(e);
    }

    // todo
    public UtilException(String msgTemplate, Object ...pararms) {
        super();
    }

    public UtilException(String msg) {
        super(msg);
    }

    public UtilException(String msg, Throwable e) {
        super(msg, e);
    }

    public UtilException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
