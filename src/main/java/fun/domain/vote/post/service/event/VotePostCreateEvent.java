package fun.domain.vote.post.service.event;

public record VotePostCreateEvent(
        Long memberId,
        Long votePostId
) {
}
