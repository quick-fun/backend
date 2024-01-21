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
    private int value;

    protected VoteCount() {
    }

    public VoteCount(final int value) {
        this.value = value;
    }

    public VoteCount plus(final VoteCount requestVoteCount) {
        return new VoteCount(this.value + requestVoteCount.value);
    }

    public int divide(VoteCount target) {
        if (value == 0) {
            return 0;
        }

        return target.value / this.value;
    }

    public VoteCount increase() {
        return new VoteCount(this.value + 1);
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

    @Override
    public String toString() {
        return "VoteCount{" +
               "value=" + value +
               '}';
    }
}
