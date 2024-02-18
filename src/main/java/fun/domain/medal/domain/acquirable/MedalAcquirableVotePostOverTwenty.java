package fun.domain.medal.domain.acquirable;

import fun.domain.vote.post.domain.VotePostCommandRepository;

public class MedalAcquirableVotePostOverTwenty implements MedalAcquirable {

    private final VotePostCommandRepository votePostCommandRepository;

    protected MedalAcquirableVotePostOverTwenty(final VotePostCommandRepository votePostCommandRepository) {
        this.votePostCommandRepository = votePostCommandRepository;
    }

    @Override
    public boolean checkAcquirable(final MedalCheckForm medalCheckForm) {
        if (medalCheckForm.memberId() == null) {
            return false;
        }

        return votePostCommandRepository.countByMemberId(medalCheckForm.memberId()) >= 20;
    }
}
