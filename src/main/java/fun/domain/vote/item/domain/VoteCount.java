package fun.domain.vote.item.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class VoteCount {

    public static final VoteCount ZERO = new VoteCount(0);

    @Column(name = "count", nullable = false)
    private Integer value;

    protected VoteCount() {
    }

    public VoteCount(final Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VoteCount voteCount = (VoteCount) o;
        return Objects.equals(value, voteCount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}