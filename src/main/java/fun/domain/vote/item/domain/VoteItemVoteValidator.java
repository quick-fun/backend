package fun.domain.vote.item.domain;

import fun.domain.vote.post.domain.VotePostCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class VoteItemVoteValidator {

    private final VotePostCommandRepository votePostCommandRepository;
    private final VoteItemCommandRepository voteItemCommandRepository;

    void validate(final Long memberId, final Long votePostId) {
        validateVotePostExists(votePostId);
        validateMemberVotedBefore(votePostId, memberId);
    }

    private void validateVotePostExists(final Long votePostId) {
        if (!votePostCommandRepository.existsById(votePostId)) {
            throw new IllegalStateException("원하는 투표 항목이 속하고 있는 투표 게시글이 아닙니다.");
        }
    }

    private void validateMemberVotedBefore(final Long votePostId, final Long memberId) {
        if (isMemberVotedBefore(votePostId, memberId)) {
            throw new IllegalStateException("이미 사용자는 투표 게시글의 투표 항목 중 하나에 투표한 결과가 있습니다.");
        }
    }

    private boolean isMemberVotedBefore(final Long votePostId, final Long memberId) {
        return voteItemCommandRepository.findByVotePostId(votePostId)
                .stream()
                .anyMatch(voteItem -> voteItem.checkMemberVotedBefore(memberId));
    }

    public void validateAnonymous(final Long anonymousMemberId, final Long votePostId) {
        validateVotePostExists(votePostId);
        validateMemberVotedBefore(votePostId, anonymousMemberId);
    }

    private void validateAnonymousVotedBefore(final Long votePostId, final Long memberId) {
        if (isAnonymousVotedBefore(votePostId, memberId)) {
            throw new IllegalStateException("이미 익명 사용자는 투표 게시글의 투표 항목 중 하나에 투표한 결과가 있습니다.");
        }
    }

    private boolean isAnonymousVotedBefore(final Long votePostId, final Long memberId) {
        return voteItemCommandRepository.findByVotePostId(votePostId)
                .stream()
                .anyMatch(voteItem -> voteItem.checkMemberVotedBefore(memberId));
    }
}
