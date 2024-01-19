package fun.domain.auth.service.authcode;

import fun.domain.auth.domain.AuthSocialType;

public interface AuthCodeProvider {

    AuthSocialType getAuthSocialType();

    String getRequestUrl();
}
