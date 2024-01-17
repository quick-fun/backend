package fun.domain.auth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {

    private RefreshTokenSigner refreshTokenSigner;

    @BeforeEach
    void setUp() {
        refreshTokenSigner = new RefreshTokenSignerStub();
    }

    @DisplayName("[SUCCESS] 리프래시 토큰을 생성한다.")
    @Test
    void success_publishRefreshToken() {
        // given
        final RefreshToken refreshToken = RefreshToken.publishRefreshToken();

        // expect
        assertThat(refreshToken.getValue())
                .isNotNull()
                .isNotBlank();
    }

    @DisplayName("[SUCCESS] 리프래시 토큰을 서명한다.")
    @Test
    void success_sign() {
        // given
        final String token = UUID.randomUUID().toString();
        final RefreshToken refreshToken = new RefreshToken(token);

        // when
        final String actual = refreshToken.sign(refreshTokenSigner);

        // then
        assertThat(actual)
                .isNotNull()
                .isNotBlank()
                .isNotEqualTo(token);
    }

    @DisplayName("[SUCCESS] 리프래시 토큰이 같지 않은지 확인한다.")
    @Test
    void success_isNotSame() {
        // given
        final RefreshToken one = new RefreshToken(UUID.randomUUID().toString());
        final RefreshToken two = new RefreshToken(UUID.randomUUID().toString());

        // when
        final boolean actual = one.isNotSame(two);

        // then
        assertThat(actual).isTrue();
    }
}
