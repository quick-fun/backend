package fun.infra.auth.kakao;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.command.token.SocialAccessTokenProvider;
import fun.domain.auth.service.command.token.SocialProfileDto;
import fun.domain.auth.service.command.token.SocialAccessTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RequiredArgsConstructor
@Component
public class KakaoSocialAccessTokenProvider implements SocialAccessTokenProvider {

    private final KakaoOauthConfig kakaoOauthConfig;
    private final KakaoAuthClient kakaoAuthClient;

    @Override
    public AuthSocialType getAuthSocialType() {
        return AuthSocialType.KAKAO;
    }

    @Override
    public SocialAccessTokenDto getSocialAccessToken(final String authCode) {
        final MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", kakaoOauthConfig.grantType());
        requestParams.add("client_id", kakaoOauthConfig.clientId());
        requestParams.add("redirect_uri", kakaoOauthConfig.redirectUri());
        requestParams.add("client_secret", kakaoOauthConfig.clientSecret());
        requestParams.add("code", authCode);

        return kakaoAuthClient.fetchAccessToken(requestParams);
    }

    @Override
    public SocialProfileDto getSocialProfile(final SocialAccessTokenDto socialAccessToken) {
        return kakaoAuthClient.fetchSocial("Bearer " + socialAccessToken.accessToken())
                .toSocialProfileResponse();
    }
}
