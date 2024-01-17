package fun.domain.auth.domain;

import fun.domain.auth.config.filter.RefreshTokenVerifier;

import java.util.Base64;

public class RefreshTokenVerifierStub implements RefreshTokenVerifier {

    public RefreshTokenVerifierStub() {
    }

    @Override
    public RefreshToken verify(final String signedRefreshToken) {
        return new RefreshToken(
                new String(getBase64Decode(signedRefreshToken))
        );
    }

    private byte[] getBase64Decode(final String signedRefreshToken) {
        return Base64.getDecoder().decode(signedRefreshToken);
    }
}
