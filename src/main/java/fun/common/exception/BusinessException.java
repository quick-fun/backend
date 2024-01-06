package fun.common.exception;

public abstract class BusinessException extends BaseException {

    protected BusinessException(final String message) {
        super(message);
    }
}
