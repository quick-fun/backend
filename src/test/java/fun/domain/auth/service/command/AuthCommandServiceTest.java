package fun.domain.auth.service.command;

import fun.domain.auth.domain.AccessTokenSignerStub;
import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.domain.RefreshTokenSignerStub;
import fun.domain.auth.service.command.response.TokenResponse;
import fun.domain.auth.service.command.token.SocialAccessTokenProviderCompositeStub;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class AuthCommandServiceTest extends ServiceTestConfig {

    private AuthCommandService authCommandService;

    @BeforeEach
    void setUp() {
        this.authCommandService = new AuthCommandService(
                new SocialAccessTokenProviderCompositeStub(),
                new AccessTokenSignerStub(),
                new RefreshTokenSignerStub(),
                authMemberRepository,
                memberCommandRepository
        );
    }

    @DisplayName("[SUCCESS] 토큰(액세스, 리프레시)을 생성한다.")
    @Test
    void success_createTokens() {
        // when
        final TokenResponse actual = authCommandService.createTokens(
                AuthSocialType.KAKAO,
                SocialAccessTokenProviderCompositeStub.ONE_AUTH_CODE
        );

        // expect
        assertSoftly(softly -> {
            softly.assertThat(actual.accessToken()).isNotEqualTo("accessToken");
            softly.assertThat(actual.refreshToken()).isNotEqualTo("refreshToken");
        });
    }
}