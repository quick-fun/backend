package fun.domain.vote.item.service.event;

public record VoteItemVoteEvent(
        Long memberId,
        Long votePostId,
        Long voteItemId
) {
}
