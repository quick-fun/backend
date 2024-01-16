package fun.domain.auth.service.query;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.query.authcode.AuthCodeProviderComposite;
import org.mockito.Mockito;

import java.util.Collections;

public class AuthCodeProviderCompositeStub extends AuthCodeProviderComposite {

    public static final AuthSocialType MOCK_AUTH_SOCIAL_TYPE = Mockito.mock(AuthSocialType.class);

    public AuthCodeProviderCompositeStub() {
        super(Collections.emptySet());
    }

    @Override
    public String getRequestUrl(final AuthSocialType authSocialType) {
        if (authSocialType.equals(MOCK_AUTH_SOCIAL_TYPE)) {
            return "https://";
        }

        return null;
    }
}
