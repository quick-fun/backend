package fun.domain.vote.comment.service;

import fun.domain.vote.comment.service.request.CreateCommentRequest;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentCommandServiceTest extends ServiceTestConfig {

    private CommentCommandService commentCommandService;

    @BeforeEach
    void setUp() {
        commentCommandService = new CommentCommandService(commentCommandRepository, eventPublisher);
    }

    @DisplayName("[SUCCESS] 댓글 생성에 성공한다.")
    @Test
    void success_createComment() {
        // when
        final Long actual = commentCommandService.createComment(
                1L,
                new CreateCommentRequest("댓글 내용"),
                1L
        );

        // then
        assertThat(actual).isPositive();
    }
}
