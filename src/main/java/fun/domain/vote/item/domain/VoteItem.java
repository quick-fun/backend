package fun.domain.vote.item.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @OneToMany(mappedBy = "voteItemId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteItemMember> voteItemMembers = new ArrayList<>();

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
            final List<VoteItemMember> voteItemMembers,
            final Long version
    ) {
        this.id = id;
        this.content = content;
        this.voteCount = voteCount;
        this.votePostId = votePostId;
        this.voteItemMembers = voteItemMembers;
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
        this.voteItemMembers.add(new VoteItemMember(memberId, votePostId));
        this.voteCount = voteCount.increase();
    }

    boolean checkMemberVotedBefore(final Long memberId) {
        return voteItemMembers.contains(memberId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VoteItem voteItem = (VoteItem) o;
        return Objects.equals(id, voteItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VoteItem{" +
               "id=" + id +
               ", content='" + content + '\'' +
               ", voteCount=" + voteCount +
               ", votePostId=" + votePostId +
               ", voteItemMembers=" + voteItemMembers +
               ", version=" + version +
               '}';
    }
}
