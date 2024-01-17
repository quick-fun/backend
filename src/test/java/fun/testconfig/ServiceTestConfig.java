package fun.testconfig;

import fun.domain.auth.domain.AuthMemberRepository;
import fun.domain.member.domain.MemberCommandRepository;
import fun.domain.vote.label.domain.VoteLabelCommandRepository;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import fun.domain.vote.post.domain.VoteTagCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class ServiceTestConfig extends DomainValidatorTestConfig {

    @Autowired
    protected MemberCommandRepository memberCommandRepository;

    @Autowired
    protected VotePostCommandRepository votePostCommandRepository;

    @Autowired
    protected VoteLabelCommandRepository voteLabelCommandRepository;

    @Autowired
    protected VoteTagCommandRepository voteTagCommandRepository;

    @Autowired
    protected AuthMemberRepository authMemberRepository;
}
