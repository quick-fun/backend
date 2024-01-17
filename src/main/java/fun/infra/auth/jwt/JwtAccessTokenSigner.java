package fun.infra.auth.jwt;

import fun.domain.auth.domain.AccessTokenSigner;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtAccessTokenSigner implements AccessTokenSigner {

    private final JwtConfig jwtConfig;

    @Override
    public String sign(final Object memberId) {
        final Date now = getNow();
        final Date expiredDate = getExpiredDate(now);
        return createJwt(memberId, now, expiredDate);
    }

    private Date getNow() {
        return new Date();
    }

    private Date getExpiredDate(final Date now) {
        return new Date(now.getTime() + Duration.ofMinutes(jwtConfig.accessTokenExpireMinutes()).toMillis());
    }

    private String createJwt(final Object memberId, final Date now, final Date expiredDate) {
        return Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(jwtConfig.issuer())
                .setClaims(Map.of("memberId", memberId))
                .setExpiration(expiredDate)
                .signWith(jwtConfig.sharedKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
