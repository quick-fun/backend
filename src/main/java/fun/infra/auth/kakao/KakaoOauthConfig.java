package fun.infra.auth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOauthConfig(
        String grantType,
        String clientId,
        String redirectUri,
        String code,
        String clientSecret,
        String[] scope
) {
}
