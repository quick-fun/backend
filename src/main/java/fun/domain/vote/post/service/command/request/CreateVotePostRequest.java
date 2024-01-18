package fun.domain.vote.post.service.command.request;

import java.time.LocalDateTime;
import java.util.List;

public record CreateVotePostRequest(
        String title,
        String content,
        Long tagId,
        List<String> voteItemTitles,
        LocalDateTime localDateTime,
        boolean isEnableComments
) {
}
