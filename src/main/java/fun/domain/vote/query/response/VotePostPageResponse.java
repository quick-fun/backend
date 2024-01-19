package fun.domain.vote.query.response;

import java.time.LocalDateTime;
import java.util.List;

public record VotePostPageResponse(
        List<VotePostPageSubResponse> data
) {

    public record VotePostPageSubResponse(
            Long votePostId,
            String title,
            String content,
            List<VoteItemResponse> voteItems,
            TagResponse tag,
            List<VoteLabelResponse> labels,
            LocalDateTime createdAt,
            Long commentTotalCount
    ) {
    }
}
