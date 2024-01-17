package fun.infra.auth.jwt;

import fun.domain.auth.domain.RefreshTokenSigner;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtRefreshTokenSigner implements RefreshTokenSigner {

    private final JwtConfig jwtConfig;

    @Override
    public String sign(final String refreshToken) {
        final Date now = getNow();
        final Date expiredDate = getExpiredDate(now);
        return createJwt(refreshToken, now, expiredDate);
    }

    private Date getNow() {
        return new Date();
    }

    private Date getExpiredDate(final Date now) {
        return new Date(now.getTime() + Duration.ofMinutes(jwtConfig.refreshTokenExpireMinutes()).toMillis());
    }

    private String createJwt(final String refreshToken, final Date now, final Date expiredDate) {
        return Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(jwtConfig.issuer())
                .setClaims(Map.of("refreshToken", refreshToken))
                .setExpiration(expiredDate)
                .signWith(jwtConfig.sharedKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
