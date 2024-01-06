package fun.testconfig;

import fun.domain.vote.post.domain.VoteAssignHostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import({
        VoteAssignHostValidator.class
})
public abstract class DomainValidatorTestConfig {

    @Autowired
    protected VoteAssignHostValidator voteAssignHostValidator;
}
