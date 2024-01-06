package fun.common.exception;

public abstract class DomainException extends BaseException{

    protected DomainException(final String message) {
        super(message);
    }
}
