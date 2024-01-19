package fun.domain.auth.service;

import fun.domain.auth.service.authcode.AuthCodeProviderComposite;
import fun.domain.auth.domain.AuthSocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthQueryService {

    private final AuthCodeProviderComposite authCodeProviderComposite;

    public String findAuthCodeRequestUrl(final AuthSocialType authSocialType) {
        return authCodeProviderComposite.getRequestUrl(authSocialType);
    }
}
