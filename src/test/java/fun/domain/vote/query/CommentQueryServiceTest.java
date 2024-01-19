package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;
import fun.domain.vote.comment.domain.Comment;
import fun.domain.vote.query.response.CommentPageResponse;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentQueryServiceTest extends ServiceTestConfig {

    private CommentQueryService commentQueryService;

    @BeforeEach
    void setUp() {
        commentQueryService = new CommentQueryService(
                commentQueryRepository,
                memberQueryRepository,
                medalQueryRepository
        );
    }

    @DisplayName("[SUCCESS] 댓글 페이징 조회를 한다.")
    @Test
    void success_findCommentPage() {
        // given
        final Member expectedMember = memberCommandRepository.save(
                new Member(
                        "사용자 닉네임",
                        "성별",
                        "사용자 이미지 url",
                        0
                )
        );

        final Medal expectedMedal = medalCommandRepository.save(
                new Medal(
                        "메달명",
                        "메달 내용",
                        "메달 획득 조건"
                )
        );
        expectedMember.addMedal(expectedMedal.getId());

        final long votePostId = 1L;
        for (int count = 0; count < 10; count++) {
            saveComment(votePostId, expectedMember);
        }

        // when
        final CommentPageResponse actual = commentQueryService.pageComments(votePostId, 11L, 10L);

        // then
        assertThat(actual.data()).hasSize(10);
    }

    private void saveComment(final long votePostId, final Member expectedMember) {
        commentCommandRepository.save(
                new Comment(
                        "댓글 내용",
                        votePostId,
                        expectedMember.getId()
                )
        );
    }
}
