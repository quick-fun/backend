package fun.domain.auth.service.command.token;

import fun.domain.auth.domain.AuthSocialType;

import java.util.Collections;

public class SocialAccessTokenProviderCompositeStub extends SocialAccessTokenProviderComposite {
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

    public SocialAccessTokenProviderCompositeStub() {
        super(Collections.emptySet());
    }

    @Override
    public SocialAccessTokenDto getSocialAccessToken(final AuthSocialType authSocialType, final String authCode) {
        if (authSocialType.equals(AuthSocialType.KAKAO)) {
            if (authCode.equals(ONE_AUTH_CODE)) {
                return ONE_SOCIAL_ACCESS_TOKEN;
            }
        }

        return null;
    }

    @Override
    public SocialProfileDto getSocialProfile(final AuthSocialType authSocialType, final SocialAccessTokenDto socialAccessTokenDto) {
        if (authSocialType.equals(AuthSocialType.KAKAO)) {
            if (socialAccessTokenDto.equals(ONE_SOCIAL_ACCESS_TOKEN)) {
                return ONE_SOCIAL_PROFILE;
            }
        }

        return null;
    }
}
