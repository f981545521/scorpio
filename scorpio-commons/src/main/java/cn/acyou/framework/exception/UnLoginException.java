package cn.acyou.framework.exception;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-07 下午 06:30]
 **/
public class UnLoginException extends RuntimeException {

    public UnLoginException() {
    }

    public UnLoginException(String message) {
        super(message);
    }

    public UnLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
