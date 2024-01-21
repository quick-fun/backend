package fun.domain.vote.item.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

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

    @BatchSize(size = 100)
    @ElementCollection
    @CollectionTable(
            name = "vote_item_member",
            joinColumns = @JoinColumn(name = "vote_item_id", nullable = false),
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "member_id", nullable = false)
    private List<Long> votedMemberIds = new ArrayList<>();

    @Version
    private Long version;

    protected VoteItem() {
    }

    public VoteItem(final String content) {
        this(null, content, VoteCount.ZERO, null, new ArrayList<>(), null);
    }

    protected VoteItem(
            final Long id,
            final String content,
            final VoteCount voteCount,
            final Long votePostId,
            final List<Long> votedMemberIds,
            final Long version
    ) {
        this.id = id;
        this.content = content;
        this.voteCount = voteCount;
        this.votePostId = votePostId;
        this.votedMemberIds = votedMemberIds;
        this.version = version;
    }

    public void assignVotePost(final Long requestVotePostId) {
        if (this.votePostId != null) {
            throw new IllegalStateException("현재 투표 항목은 이미 투표 게시글에 속해 있습니다.");
        }

        this.votePostId = requestVotePostId;
    }

    public int divideVoteCountRate(final VoteCount requestVoteCount) {
        return this.voteCount.divide(requestVoteCount);
    }

    public void vote(
            final Long memberId,
            final Long votePostId,
            final VoteItemVoteValidator voteItemVoteValidator
    ) {
        voteItemVoteValidator.validate(memberId, votePostId);
        this.votedMemberIds.add(memberId);
        this.voteCount = voteCount.increase();
    }

    boolean checkMemberVotedBefore(final Long memberId) {
        return votedMemberIds.contains(memberId);
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
