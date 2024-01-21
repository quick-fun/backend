package fun.domain.medal.domain.acquirable;

import fun.domain.vote.comment.domain.CommentCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MedalAcquirableCommentOverThirty implements MedalAcquirable {

    private final CommentCommandRepository commentCommandRepository;

    @Override
    public boolean checkAcquirable(final MedalCheckForm medalCheckForm) {
        if (medalCheckForm.memberId() == null) {
            return false;
        }

        return commentCommandRepository.countByMemberId(medalCheckForm.memberId()) >= 30;
    }
}
