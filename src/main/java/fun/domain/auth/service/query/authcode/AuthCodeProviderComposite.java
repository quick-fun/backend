package fun.domain.auth.service.query.authcode;

import fun.domain.auth.domain.AuthSocialType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AuthCodeProviderComposite {

    private final Map<AuthSocialType, AuthCodeProvider> authCodeProviders;

    public AuthCodeProviderComposite(final Set<AuthCodeProvider> authCodeProviders) {
        this.authCodeProviders = authCodeProviders.stream()
                .collect(Collectors.toMap(
                        AuthCodeProvider::getAuthSocialType,
                        Function.identity()
                ));
    }

    public String getRequestUrl(final AuthSocialType authSocialType) {
        return Optional.ofNullable(authCodeProviders.get(authSocialType))
                .map(AuthCodeProvider::getRequestUrl)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 소셜 로그인 타입입니다."));
    }
}
