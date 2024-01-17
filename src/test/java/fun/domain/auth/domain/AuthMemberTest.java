package fun.domain.auth.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthMemberTest {

    private AccessTokenSigner accessTokenSigner;
    private RefreshTokenSigner refreshTokenSigner;

    @BeforeEach
    void setUp() {
        accessTokenSigner = new AccessTokenSignerStub();
        refreshTokenSigner = new RefreshTokenSignerStub();
    }

    @DisplayName("[SUCCESS] 액세스 토큰을 서명한다.")
    @Test
    void success_signAccessToken() {
        // given
        final AuthMember authMember = new AuthMember(
                1L,
                new SocialAccessToken(UUID.randomUUID().toString()),
                RefreshToken.publishRefreshToken(),
                Mockito.mock(AuthSocialType.class),
                1L
        );

        // when
        final String actual = authMember.signAccessToken(accessTokenSigner);

        // then
        assertThat(actual)
                .isNotNull()
                .isNotBlank();
    }

    @DisplayName("[SUCCESS] 리프레시 토큰을 서명한다.")
    @Test
    void success_signRefreshToken() {
        // given
        final RefreshToken expected = RefreshToken.publishRefreshToken();
        final AuthMember authMember = new AuthMember(
                1L,
                new SocialAccessToken(UUID.randomUUID().toString()),
                expected,
                Mockito.mock(AuthSocialType.class),
                1L
        );

        // when
        final String actual = authMember.signRefreshToken(refreshTokenSigner);

        // then
        assertThat(actual).isNotEqualTo(expected.getValue());
    }

    @DisplayName("[SUCCESS] 리프레시 토큰을 발행한다.")
    @Test
    void success_publishRefreshToken() {
        // given
        final RefreshToken expected = RefreshToken.publishRefreshToken();
        final AuthMember actual = new AuthMember(
                1L,
                new SocialAccessToken(UUID.randomUUID().toString()),
                expected,
                Mockito.mock(AuthSocialType.class),
                1L
        );

        // when
        actual.publishRefreshToken(expected);

        // then
        assertThat(actual.getRefreshToken()).isNotEqualTo(expected);
    }
}
