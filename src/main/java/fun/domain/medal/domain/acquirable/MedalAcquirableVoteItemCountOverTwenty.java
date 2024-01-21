package fun.domain.medal.domain.acquirable;

import fun.domain.vote.item.domain.VoteItemMemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedalAcquirableVoteItemCountOverTwenty implements MedalAcquirable {

    private VoteItemMemberCommandRepository voteItemMemberCommandRepository;

    @Override
    public boolean checkAcquirable(final MedalCheckForm medalCheckForm) {
        if (medalCheckForm.memberId() == null) {
            return false;
        }

        final Long totalVotedCount = voteItemMemberCommandRepository.countByMemberId(medalCheckForm.memberId());

        return totalVotedCount >= 20;
    }
}
