package fun.domain.vote.post.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class VotePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Embedded
    private DueDate dueDate;

    @JoinColumn(name = "vote_tag_id")
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private VoteTag voteTag;

    @ElementCollection
    @CollectionTable(name = "vote_post_vote_label", joinColumns = @JoinColumn(name = "vote_post_id"))
    private List<Long> voteLabelIds = new ArrayList<>();

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    protected VotePost() {
    }

    public VotePost(
            final String title,
            final String content,
            final DueDate dueDate,
            final VoteTag voteTag,
            final List<Long> voteLabelIds
    ) {
        this(null, title, content, dueDate, voteTag, voteLabelIds);
    }

    protected VotePost(
            final Long id,
            final String title,
            final String content,
            final DueDate dueDate,
            final VoteTag voteTag,
            final List<Long> voteLabelIds
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.dueDate = dueDate;
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
}
