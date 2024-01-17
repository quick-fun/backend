package fun.infra.auth.jwt;

import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.config.filter.RefreshTokenVerifier;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtRefreshTokenVerifier implements RefreshTokenVerifier {

    private final JwtConfig jwtConfig;

    @Override
    public RefreshToken verify(final String signedRefreshToken) {
        return new RefreshToken(
                getJwtParser().parseClaimsJwt(signedRefreshToken)
                .getBody()
                .get("refreshToken", String.class)
        );
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.sharedKey())
                .requireIssuer(jwtConfig.issuer())
                .build();
    }
}
