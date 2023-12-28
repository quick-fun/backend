package fun.domain.vote.post.domain;

import fun.domain.vote.post.domain.VoteAssignHostValidator;

public class VoteAssignHostValidatorFailStub extends VoteAssignHostValidator {

    public VoteAssignHostValidatorFailStub() {
        super(null);
    }

    @Override
    void validate(final Long memberId) {
        throw new IllegalStateException("[Test] 투표 게시글 생성에 필요한 개최자를 사용자 식별자값으로 조회할 수 없습니다.");
    }
}
