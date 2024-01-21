package fun.domain.medal.domain.acquirable;

import fun.domain.medal.domain.MedalType;
import fun.domain.vote.post.domain.VotePostCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedalAcquirableVotePostOverTwenty implements MedalAcquirable {

    private final VotePostCommandRepository votePostCommandRepository;

    @Override
    public boolean checkAcquirable(final MedalCheckForm medalCheckForm) {
        if (medalCheckForm.memberId() == null) {
            return false;
        }

        return votePostCommandRepository.countByMemberId(medalCheckForm.memberId()) >= 20;
    }

    @Override
    public MedalType getMedalType() {
        return MedalType.VOTE_POST_OVER_TWENTY;
    }
}
