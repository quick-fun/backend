package fun.domain.auth.config.filter;

import fun.domain.auth.domain.RefreshToken;

public interface RefreshTokenVerifier {

    RefreshToken verify(String signedRefreshToken);
}
