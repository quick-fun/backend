package fun.domain.vote.post.domain;

import fun.common.BaseEntity;
import fun.domain.vote.item.domain.VoteCount;
import fun.domain.vote.item.domain.VoteItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class VotePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Embedded
    private DueDate dueDate;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "votePostId", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<VoteItem> voteItems = new ArrayList<>();

    @JoinColumn(
            name = "vote_tag_id",
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private VoteTag voteTag;

    @ElementCollection
    @CollectionTable(
            name = "vote_post_vote_label",
            joinColumns = @JoinColumn(name = "vote_post_id", nullable = false),
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "vote_label_id", nullable = false)
    private List<Long> voteLabelIds = new ArrayList<>();

    @Column(name = "member_id")
    private Long memberId;

    protected VotePost() {
    }

    public VotePost(
            final String title,
            final String content,
            final DueDate dueDate,
            final VoteTag voteTag
    ) {
        this(null, title, content, dueDate, new ArrayList<>(), voteTag, new ArrayList<>());
    }

    protected VotePost(
            final Long id,
            final String title,
            final String content,
            final DueDate dueDate,
            final List<VoteItem> voteItems,
            final VoteTag voteTag,
            final List<Long> voteLabelIds
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
        this.voteItems = voteItems;
        this.voteTag = voteTag;
        this.voteLabelIds = voteLabelIds;
    }

    public void assignHost(
            final Long requestMemberId,
            final VoteAssignHostValidator voteAssignHostValidator
    ) {
        voteAssignHostValidator.validate(requestMemberId);
        this.memberId = requestMemberId;
    }

    public void addVoteItems(final List<VoteItem> requestVoteItems) {
        for (final VoteItem requestVoteItem : requestVoteItems) {
            requestVoteItem.assignVotePost(this.id);
        }
        this.voteItems.addAll(requestVoteItems);
    }

    public VoteCount getTotalVoteCount() {
        return voteItems.stream()
                .map(VoteItem::getVoteCount)
                .reduce(VoteCount::plus)
                .orElse(VoteCount.ZERO);
    }

    public void addVoteLabel(final Long requestVoteLabelId) {
        this.voteLabelIds.add(requestVoteLabelId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VotePost votePost = (VotePost) o;
        return Objects.equals(id, votePost.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VotePost{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", dueDate=" + dueDate +
               ", voteItems=" + voteItems +
               ", voteTag=" + voteTag +
               ", voteLabelIds=" + voteLabelIds +
               ", memberId=" + memberId +
               '}';
    }
}
