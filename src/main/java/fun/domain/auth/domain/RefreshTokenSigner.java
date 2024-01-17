package fun.domain.auth.domain;

public interface RefreshTokenSigner {

    String sign(final String refreshToken);
}
