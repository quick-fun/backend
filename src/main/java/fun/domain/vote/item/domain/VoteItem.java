package fun.domain.vote.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;

@Getter
@Entity
public class VoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Embedded
    private VoteCount voteCount;

    @JoinColumn(name = "vote_post_id", nullable = false)
    private Long votePostId;

    protected VoteItem() {
    }

    public VoteItem(final String content) {
        this(null, content, VoteCount.ZERO, null);
    }

    protected VoteItem(
            final Long id,
            final String content,
            final VoteCount voteCount,
            final Long votePostId
    ) {
        this.id = id;
        this.content = content;
        this.voteCount = voteCount;
        this.votePostId = votePostId;
    }

    public void assignVotePost(final Long requestVotePostId) {
        if (this.votePostId != null) {
            throw new IllegalStateException("현재 투표 항목은 이미 투표 게시글에 속해 있습니다.");
        }

        this.votePostId = requestVotePostId;
    }

    @Override
    public String toString() {
        return "VoteItem{" +
               "id=" + id +
               ", content='" + content + '\'' +
               ", voteCount=" + voteCount +
               ", votePostId=" + votePostId +
               '}';
    }
}
