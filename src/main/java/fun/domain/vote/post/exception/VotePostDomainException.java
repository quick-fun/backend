package fun.domain.vote.post.exception;

import fun.common.exception.DomainException;

public class VotePostDomainException extends DomainException {

    public VotePostDomainException(final String message) {
        super(message);
    }
}
