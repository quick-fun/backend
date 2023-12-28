package fun.domain.vote.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Embeddable
public class DueDate {

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    protected DueDate() {
    }

    public DueDate(final LocalDateTime dueDate) {
        validateIsFuture(dueDate);
        this.dueDate = dueDate;
    }

    private void validateIsFuture(final LocalDateTime dueDate) {
        if (dueDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("마감 기한은 현재보다 과거일 수 없습니다.");
        }
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
