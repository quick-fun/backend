package fun.infra.auth.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@ConfigurationProperties(prefix = "jwt.sign")
public record JwtConfig(
        String issuer,
        String secretKey,
        long accessTokenExpireMinutes,
        long refreshTokenExpireMinutes
) {

    public Key sharedKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
