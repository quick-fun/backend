package fun.domain.auth.service.token;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fun.domain.auth.domain.SocialAccessToken;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

/**
 * 외부 플랫폼에서 발급 받은 SocialAccessToken
 *
 * @param accessToken
 */
@JsonNaming(SnakeCaseStrategy.class)
public record SocialAccessTokenDto(
        String tokenType,
        String accessToken,
        String idToken,
        Integer expiresIn,
        String refreshToken,
        Integer refreshTokenExpiresIn,
        String scope
) {

    public SocialAccessToken toSocialAccessToken() {
        return new SocialAccessToken(accessToken);
    }
}
