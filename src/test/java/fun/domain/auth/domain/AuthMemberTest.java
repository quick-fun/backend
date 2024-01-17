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

    @DisplayName("[SUCCESS] 액세스 토큰을 발행한다.")
    @Test
    void success_publishAccessToken() {
        // given
        final AuthMember authMember = new AuthMember(
                1L,
                new SocialAccessToken(UUID.randomUUID().toString()),
                RefreshToken.publishRefreshToken(),
                Mockito.mock(AuthSocialType.class),
                new MemberId(1L)
        );

        // when
        final String actual = authMember.publishAccessToken(accessTokenSigner);

        // then
        assertThat(actual)
                .isNotNull()
                .isNotBlank();
    }

    @DisplayName("[SUCCESS] 리프레시 토큰을 발행한다.")
    @Test
    void success_publishRefreshToken() {
        // given
        final AuthMember authMember = new AuthMember(
                1L,
                new SocialAccessToken(UUID.randomUUID().toString()),
                RefreshToken.publishRefreshToken(),
                Mockito.mock(AuthSocialType.class),
                new MemberId(1L)
        );

        // when
        final String actual = authMember.publishRefreshToken(refreshTokenSigner);

        // then
        assertThat(actual)
                .isNotNull()
                .isNotBlank();
    }
}
