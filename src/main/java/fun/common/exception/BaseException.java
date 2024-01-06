package fun.common.exception;

public abstract class BaseException extends RuntimeException {

    protected BaseException(final String message) {
        super(message);
    }
}
