package fun.domain.medal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

import static fun.domain.medal.domain.MedalType.*;

@Getter
@Entity
public class Medal {

    public static final Medal DEFAULT_MEDAL = new Medal(NEW_MEMBER);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @Enumerated(value = EnumType.STRING)
    private MedalType medalType;

    protected Medal() {
    }

    public Medal(final MedalType medalType) {
        this(null, medalType);
    }

    protected Medal(final Long id, final MedalType medalType) {
        this.id = id;
        this.medalType = medalType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Medal medal = (Medal) o;
        return Objects.equals(id, medal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Medal{" +
               "id=" + id +
               ", medalType=" + medalType +
               "}";
    }
}
