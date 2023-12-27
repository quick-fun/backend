package fun.domain.vote.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
public class DueDate {

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    protected DueDate() {
    }

    public DueDate(final LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DueDate that = (DueDate) o;
        return Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate);
    }
}
