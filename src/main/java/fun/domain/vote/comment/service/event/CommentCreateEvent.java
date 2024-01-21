package fun.domain.vote.comment.service.event;

public record CommentCreateEvent(
        Long memberId,
        Long votePostId,
        Long commentId
) {
}
