package fun.domain.vote.label.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class VoteLabel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    protected VoteLabel() {
    }

    public VoteLabel(final String name) {
        this(null, name);
    }

    protected VoteLabel(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final VoteLabel voteLabel = (VoteLabel) o;
        return Objects.equals(id, voteLabel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
