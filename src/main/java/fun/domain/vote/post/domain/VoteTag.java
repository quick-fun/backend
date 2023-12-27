package fun.domain.vote.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class VoteTag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "title")
    private Tag tag;

    protected VoteTag() {
    }

    public VoteTag(final Tag tag) {
        this(null, tag);
    }

    protected VoteTag(final Long id, final Tag tag) {
        this.id = id;
        this.tag = tag;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VoteTag voteTag = (VoteTag) o;
        return Objects.equals(id, voteTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
