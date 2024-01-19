package fun.domain.auth.service.token;

import fun.domain.auth.domain.AuthSocialType;

public interface SocialAccessTokenProvider {

    AuthSocialType getAuthSocialType();

    SocialAccessTokenDto getSocialAccessToken(final String authCode);

    SocialProfileDto getSocialProfile(final SocialAccessTokenDto socialAccessToken);
}
