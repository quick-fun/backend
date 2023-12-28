package fun.domain.vote.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DueDateTest {

    @DisplayName("[SUCCESS] 마감 기한이 현재보다 미래일 경우 생성에 성공한다.")
    @ValueSource(ints = {1, 10, 20, 30, 60, 1440, 2880})
    @ParameterizedTest
    void success_create(final int dueDateValue) {
        // given
        final LocalDateTime dueDate = LocalDateTime.now().plusMinutes(dueDateValue);

        // expect
        assertThatCode(() -> {
            new DueDate(dueDate);
        }).doesNotThrowAnyException();
    }

    @DisplayName("[EXCEPTION] 마감 기한이 현재보다 과거일 경우 생성에 실패한다.")
    @ValueSource(ints = {1, 10, 20, 30, 60, 1440, 2880})
    @ParameterizedTest
    void exception_create(final int dueDateValue) {
        // given
        final LocalDateTime dueDate = LocalDateTime.now().minusMinutes(dueDateValue);

        // expect
        assertThatThrownBy(() -> {
            new DueDate(dueDate);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
