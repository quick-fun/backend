package fun.domain.vote.post.service.command.request;

import java.time.LocalDateTime;

public record CreateVotePostRequest(
        String title,
        String content,
        Long tagId,
        LocalDateTime localDateTime
) {
}
