package fun.domain.auth.config.filter;

import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Profile("test")
class AccessTokenVerifierStub implements AccessTokenVerifier{

    static final String ONE_ACCESS_TOKEN = UUID.randomUUID().toString();
    static final Long ONE_RETURN_ID = 1L;
    static final Long WRONG_RETURN_ID = -1L;

    AccessTokenVerifierStub() {
    }

    @Override
    public Long verify(final String accessToken) {
        if (accessToken.equals(ONE_ACCESS_TOKEN)) {
            return ONE_RETURN_ID;
        }
        return -WRONG_RETURN_ID;
    }
}
