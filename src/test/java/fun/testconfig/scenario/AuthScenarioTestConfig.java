package fun.testconfig.scenario;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.command.token.SocialAccessTokenProviderComposite;
import fun.domain.auth.service.query.authcode.AuthCodeProviderComposite;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestConfiguration
public abstract class AuthScenarioTestConfig {

    protected static String MEMBER_1_AUTH_CODE = "member_1_auth_code";
    protected static String MEMBER_2_AUTH_CODE = "member_2_auth_code";
    protected static String MEMBER_1_ACCESS_TOKEN = "member_1_access_token";
    protected static String MEMBER_2_ACCESS_TOKEN = "member_2_access_token";

    @Bean
    AuthCodeProviderComposite authCodeProviderComposite() {
        final AuthCodeProviderComposite mock = Mockito.mock(AuthCodeProviderComposite.class);
        when(
                mock.getRequestUrl(any(AuthSocialType.class))
        ).thenReturn(
                "https://test-redirect.com"
        );

        return mock;
    }

    @Bean
    SocialAccessTokenProviderComposite accessTokenProviderComposite() {
        final SocialAccessTokenProviderComposite mock = Mockito.mock(SocialAccessTokenProviderComposite.class);

        when(
                mock.getSocialAccessToken(any(AuthSocialType.class), eq(MEMBER_1_AUTH_CODE))
        ).thenReturn(
            null
        );

        when(
                mock.getSocialAccessToken(any(AuthSocialType.class), eq(MEMBER_2_AUTH_CODE))
        ).thenReturn(
                null
        );

        return mock;
    }
}
