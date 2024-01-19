package fun.domain.auth.service.authcode;

import fun.domain.auth.domain.AuthSocialType;
import org.mockito.Mockito;

import static org.mockito.BDDMockito.when;

public class AuthCodeProviderStub implements AuthCodeProvider {

    public static final AuthSocialType MOCK_AUTH_SOCIAL_TYPE = Mockito.mock(AuthSocialType.class);

    public AuthCodeProviderStub() {
        when(MOCK_AUTH_SOCIAL_TYPE.name()).thenReturn("mock_auth_social_type");
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
