package fun.domain.vote.comment.domain;

import fun.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "vote_post_id", nullable = false)
    private Long votePostId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    protected Comment() {
    }

    public Comment(
            final String content,
            final Long votePostId,
            final Long memberId
    ) {
        this(null, content, votePostId, memberId);
    }

    protected Comment(
            final Long id,
            final String content,
            final Long votePostId,
            final Long memberId
    ) {
        this.id = id;
        this.content = content;
        this.votePostId = votePostId;
        this.memberId = memberId;
    }
}
