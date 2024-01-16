package fun.domain.auth.service.query.authcode;

import fun.domain.auth.domain.AuthSocialType;
import org.mockito.Mockito;

public class AuthCodeProviderStub implements AuthCodeProvider {

    public static final AuthSocialType MOCK_AUTH_SOCIAL_TYPE = Mockito.mock(AuthSocialType.class);

    public AuthCodeProviderStub() {
    }

    @Override
    public AuthSocialType getAuthSocialType() {
        return this.MOCK_AUTH_SOCIAL_TYPE;
    }

    @Override
    public String getRequestUrl() {
        return "https://requestUrl.com";
    }
}
