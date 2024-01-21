package fun.testconfig;

import fun.domain.vote.item.domain.VoteItemVoteValidator;
import fun.domain.vote.post.domain.VoteAssignHostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({
        VoteAssignHostValidator.class,
        VoteItemVoteValidator.class
})
public abstract class DomainValidatorTestConfig {

    @Autowired
    protected VoteAssignHostValidator voteAssignHostValidator;

    @Autowired
    protected VoteItemVoteValidator voteItemVoteValidator;
}
