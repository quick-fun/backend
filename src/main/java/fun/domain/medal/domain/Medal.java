package fun.domain.medal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class Medal {

    public static final Medal DEFAULT_MEDAL = new Medal("뉴비", "뉴비", "뉴비");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "acquisition_condition", nullable = false)
    private String acquisitionCondition;

    protected Medal() {
    }

    public Medal(
            final String title,
            final String content,
            final String acquisitionCondition
    ) {
        this.title = title;
        this.content = content;
        this.acquisitionCondition = acquisitionCondition;
    }

    protected Medal(
            final Long id,
            final String title,
            final String content,
            final String acquisitionCondition
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.acquisitionCondition = acquisitionCondition;
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
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", acquisitionCondition='" + acquisitionCondition + '\'' +
               '}';
    }
}
