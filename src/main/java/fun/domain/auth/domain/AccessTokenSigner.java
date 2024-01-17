package fun.domain.auth.domain;

public interface AccessTokenSigner {

    String sign(final Object value);
}
