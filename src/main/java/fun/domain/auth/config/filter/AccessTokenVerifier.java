package fun.domain.auth.config.filter;

public interface AccessTokenVerifier {

    Long verify(final String accessToken);
}
