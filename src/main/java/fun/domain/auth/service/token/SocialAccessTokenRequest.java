package fun.domain.auth.service.token;

public record SocialAccessTokenRequest(
        String grantType,
        String clientId,
        String redirectUri,
        String code,
        String clientSecret
) {
}
