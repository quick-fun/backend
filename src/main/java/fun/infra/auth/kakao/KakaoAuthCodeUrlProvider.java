package fun.infra.auth.kakao;

import fun.domain.auth.service.query.authcode.AuthCodeProvider;
import fun.domain.auth.domain.AuthSocialType;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class KakaoAuthCodeUrlProvider implements AuthCodeProvider {

    private final KakaoOauthConfig kakaoOauthConfig;

    public KakaoAuthCodeUrlProvider(final KakaoOauthConfig kakaoOauthConfig) {
        this.kakaoOauthConfig = kakaoOauthConfig;
    }

    @Override
    public AuthSocialType getAuthSocialType() {
        return AuthSocialType.KAKAO;
    }

    @Override
    public String getRequestUrl() {
        return new DefaultUriBuilderFactory()
                .builder()
                .path("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", kakaoOauthConfig.code())
                .queryParam("scope", String.join(",", kakaoOauthConfig.scope()))
                .queryParam("client_id", kakaoOauthConfig.clientId())
                .queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
                .toUriString();
    }
}
