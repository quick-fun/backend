package fun.domain.vote.query.response;

import java.time.LocalDateTime;
import java.util.List;

public record CommentPageResponse(
        List<CommentPageSubResponse> data
) {

    public record CommentPageSubResponse(
            Long commentId,
            Long memberId,
            String nickname,
            String memberMedalTitle,
            String imageUrl,
            String content,
            LocalDateTime createdAt
    ) {
    }
}
