package fun.domain.auth.service.command.token;

import fun.domain.auth.domain.AuthSocialType;
import org.mockito.Mockito;

public class SocialAccessTokenProviderStub implements SocialAccessTokenProvider {

    public static final AuthSocialType MOCK_AUTH_SOCIAL_TYPE = Mockito.mock(AuthSocialType.class);
    public static final String ONE_AUTH_CODE = "one_auth_code";
    public static final SocialAccessTokenDto ONE_SOCIAL_ACCESS_TOKEN = new SocialAccessTokenDto(
            "tokenType",
            "accessToken",
            "idToken",
            10,
            "refreshToken",
            10,
            "scope"
    );
    public static final SocialProfileDto ONE_SOCIAL_PROFILE = new SocialProfileDto(
            1L,
            "name",
            "gender",
            "age"
    );

    public SocialAccessTokenProviderStub() {
    }

    @Override
    public AuthSocialType getAuthSocialType() {
        return MOCK_AUTH_SOCIAL_TYPE;
    }

    @Override
    public SocialAccessTokenDto getSocialAccessToken(final String authCode) {
        if (authCode.equals(ONE_AUTH_CODE)) {
            return ONE_SOCIAL_ACCESS_TOKEN;
        }

        return null;
    }

    @Override
    public SocialProfileDto getSocialProfile(final SocialAccessTokenDto socialAccessToken) {
        if (socialAccessToken.equals(ONE_SOCIAL_ACCESS_TOKEN)) {
            return ONE_SOCIAL_PROFILE;
        }

        return null;
    }
}
