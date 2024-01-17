package fun.infra.auth.jwt;

import fun.domain.auth.config.filter.AccessTokenVerifier;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtAccessTokenVerifier implements AccessTokenVerifier {

    private final JwtConfig jwtConfig;

    @Override
    public Long verify(final String accessToken) {
        return getJwtParser().parseClaimsJwt(accessToken)
                .getBody()
                .get("memberId", Long.class);
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.sharedKey())
                .requireIssuer(jwtConfig.issuer())
                .build();
    }
}
