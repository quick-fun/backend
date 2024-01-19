package fun.domain.auth.service.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class SocialSocialAccessTokenProviderCompositeTest {

    private SocialAccessTokenProviderComposite socialAccessTokenProviderComposite;

    @BeforeEach
    void setUp() {
        socialAccessTokenProviderComposite = new SocialAccessTokenProviderComposite(
                Set.of(new SocialAccessTokenProviderStub())
        );
    }

    @DisplayName("[SUCCESS] 소셜 액세스 토큰을 가져온다.")
    @Test
    void success_getSocialAccessToken() {
        // when
        final SocialAccessTokenDto actual = socialAccessTokenProviderComposite.getSocialAccessToken(
                SocialAccessTokenProviderStub.MOCK_AUTH_SOCIAL_TYPE,
                SocialAccessTokenProviderStub.ONE_AUTH_CODE
        );

        // expect
        assertSoftly(softly -> {
            softly.assertThat(actual.tokenType()).isEqualTo("tokenType");
            softly.assertThat(actual.accessToken()).isEqualTo("accessToken");
            softly.assertThat(actual.idToken()).isEqualTo("idToken");
            softly.assertThat(actual.expiresIn()).isEqualTo(10);
            softly.assertThat(actual.refreshToken()).isEqualTo("refreshToken");
            softly.assertThat(actual.refreshTokenExpiresIn()).isEqualTo(10);
            softly.assertThat(actual.scope()).isEqualTo("scope");
        });
    }

    @DisplayName("[SUCCESS] 소셜 프로필 정보를 가져온다.")
    @Test
    void success_getSocialProfile() {
        // given
        final SocialAccessTokenDto socialAccessToken = socialAccessTokenProviderComposite.getSocialAccessToken(
                SocialAccessTokenProviderStub.MOCK_AUTH_SOCIAL_TYPE,
                SocialAccessTokenProviderStub.ONE_AUTH_CODE
        );

        // when
        final SocialProfileDto actual = socialAccessTokenProviderComposite.getSocialProfile(
                SocialAccessTokenProviderStub.MOCK_AUTH_SOCIAL_TYPE,
                socialAccessToken
        );

        // then
        assertSoftly(softly -> {
            softly.assertThat(actual.id()).isEqualTo(1L);
            softly.assertThat(actual.name()).isEqualTo("name");
            softly.assertThat(actual.gender()).isEqualTo("gender");
            softly.assertThat(actual.ageRange()).isEqualTo("age");
        });
    }
}
