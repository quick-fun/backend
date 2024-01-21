package fun.domain.auth.service;

import fun.domain.auth.domain.AccessTokenSignerStub;
import fun.domain.auth.domain.AuthMember;
import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.domain.RefreshTokenSignerStub;
import fun.domain.auth.service.response.TokenResponse;
import fun.domain.auth.service.token.SocialAccessTokenProviderCompositeStub;
import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fun.domain.auth.service.token.SocialAccessTokenProviderStub.ONE_SOCIAL_ACCESS_TOKEN;
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
                memberCommandRepository,
                eventPublisher
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

    @DisplayName("[SUCCESS] 리프래시 토큰과 액세스 토큰을 재발급 받는다.")
    @Test
    void success_recreateTokens() {
        // given
        final RefreshToken oldRefreshToken = new RefreshToken("refreshToken");
        authMemberRepository.save(
                new AuthMember(
                        1L,
                        ONE_SOCIAL_ACCESS_TOKEN.toSocialAccessToken(),
                        oldRefreshToken,
                        AuthSocialType.KAKAO,
                        1L
                )
        );

        final TokenResponse oldTokens = authCommandService.createTokens(
                AuthSocialType.KAKAO,
                SocialAccessTokenProviderCompositeStub.ONE_AUTH_CODE
        );

        // when
        final TokenResponse actual = authCommandService.recreateTokens(oldRefreshToken);

        // expect
        assertSoftly(softly -> {
            softly.assertThat(actual.accessToken()).isNotEqualTo("accessToken");
            softly.assertThat(actual.refreshToken()).isNotEqualTo("refreshToken");
        });
    }
}
