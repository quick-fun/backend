package fun.domain.vote.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VotePostTest {

    @DisplayName("[SUCCESS] 투표 게시글에 작성자 할당을 성공한다.")
    @Test
    void success_assignHost() {
        // given
        final VotePost actual = new VotePost(
                "투표 게시글 제목",
                "투표 게시글 내용",
                new DueDate(LocalDateTime.now().plusHours(24)),
                new VoteTag(Tag.SCIENCE),
                Collections.emptyList()
        );

        // when
        actual.assignHost(1L, new VoteAssignHostValidatorSuccessStub());

        // then
        assertThat(actual.getMemberId()).isEqualTo(1L);
    }

    @DisplayName("[EXCEPTION] 투표 게시글에 작성자 할당을 실패한다.")
    @Test
    void exception_assignHost() {
        // given
        final VotePost actual = new VotePost(
                "투표 게시글 제목",
                "투표 게시글 내용",
                new DueDate(LocalDateTime.now().plusHours(24)),
                new VoteTag(Tag.SCIENCE),
                Collections.emptyList()
        );

        // expect
        assertThatThrownBy(() ->
                actual.assignHost(1L, new VoteAssignHostValidatorFailStub())
        ).isInstanceOf(IllegalStateException.class);
    }
}
