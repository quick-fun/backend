package fun.domain.vote.post.domain;

import fun.domain.vote.item.domain.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

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
                new VoteTag(Tag.SCIENCE)
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
                new VoteTag(Tag.SCIENCE)
        );

        // expect
        assertThatThrownBy(() ->
                actual.assignHost(1L, new VoteAssignHostValidatorFailStub())
        ).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("[SUCCESS] 투표 게시글에 투표 항목 할당을 성공한다.")
    @Test
    void success_addVoteItem() {
        // given
        final VotePost actual = new VotePost(
                "투표 게시글 제목",
                "투표 게시글 내용",
                new DueDate(LocalDateTime.now().plusHours(24)),
                new VoteTag(Tag.SCIENCE)
        );

        // when
        final VoteItem expected1 = new VoteItem("투표 항목 내용1");
        final VoteItem expected2 = new VoteItem("투표 항목 내용2");
        actual.addVoteItems(List.of(expected1, expected2));

        // then
        assertThat(actual.getVoteItems()).containsExactly(expected1, expected2);
    }
}
