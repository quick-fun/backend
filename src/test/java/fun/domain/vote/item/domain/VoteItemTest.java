package fun.domain.vote.item.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VoteItemTest {

    @DisplayName("[SUCCESS] 투표 항목이 속할 투표 게시글을 지정한다.")
    @Test
    void success_assignVotePost() {
        // given
        final VoteItem voteItem = new VoteItem("투표 항목 내용");

        // when
        voteItem.assignVotePost(1L);

        // then
        assertThat(voteItem.getVotePostId()).isPositive();
    }

    @DisplayName("[EXCEPTION] 투표 항목이 이미 투표 게시글에 속해 있을 경우 새로운 투표 게시글에 지정될 수 없다.")
    @Test
    void exception_assignVotePost() {
        // given
        final VoteItem voteItem = new VoteItem("투표 항목 내용");

        // when
        voteItem.assignVotePost(1L);

        // then
        assertThatThrownBy(() ->
                voteItem.assignVotePost(1L)
        ).isInstanceOf(IllegalStateException.class);
    }
}
