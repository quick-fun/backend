package fun.testconfig;

import fun.config.JpaAuditingConfig;
import fun.domain.auth.domain.AuthMemberRepository;
import fun.domain.medal.domain.MedalCommandRepository;
import fun.domain.member.domain.MemberCommandRepository;
import fun.domain.vote.comment.domain.CommentCommandRepository;
import fun.domain.vote.label.domain.VoteLabelCommandRepository;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import fun.domain.vote.post.domain.VoteTagCommandRepository;
import fun.domain.vote.query.repository.CommentQueryRepository;
import fun.domain.vote.query.repository.MedalQueryRepository;
import fun.domain.vote.query.repository.MemberQueryRepository;
import fun.domain.vote.query.repository.VoteLabelQueryRepository;
import fun.domain.vote.query.repository.VotePostQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(JpaAuditingConfig.class)
@DataJpaTest
public abstract class ServiceTestConfig extends DomainValidatorTestConfig {

    @Autowired
    protected MemberCommandRepository memberCommandRepository;

    @Autowired
    protected MemberQueryRepository memberQueryRepository;

    @Autowired
    protected VotePostCommandRepository votePostCommandRepository;

    @Autowired
    protected VotePostQueryRepository votePostQueryRepository;

    @Autowired
    protected VoteLabelCommandRepository voteLabelCommandRepository;

    @Autowired
    protected VoteLabelQueryRepository voteLabelQueryRepository;

    @Autowired
    protected VoteTagCommandRepository voteTagCommandRepository;

    @Autowired
    protected AuthMemberRepository authMemberRepository;

    @Autowired
    protected MedalCommandRepository medalCommandRepository;

    @Autowired
    protected MedalQueryRepository medalQueryRepository;

    @Autowired
    protected CommentCommandRepository commentCommandRepository;

    @Autowired
    protected CommentQueryRepository commentQueryRepository;
}
