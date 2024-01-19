package fun.domain.auth.service.token;

import fun.domain.auth.domain.AuthSocialType;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SocialAccessTokenProviderComposite {

    private final Map<AuthSocialType, SocialAccessTokenProvider> socialAccessTokenProviders;

    public SocialAccessTokenProviderComposite(final Set<SocialAccessTokenProvider> socialAccessTokenProviders) {
        this.socialAccessTokenProviders = socialAccessTokenProviders.stream()
                .collect(Collectors.toMap(
                        SocialAccessTokenProvider::getAuthSocialType,
                        Function.identity()
                ));
    }

    public SocialAccessTokenDto getSocialAccessToken(final AuthSocialType authSocialType, final String authCode) {
        return Optional.ofNullable(socialAccessTokenProviders.get(authSocialType))
                .map(socialAccessTokenProvider -> socialAccessTokenProvider.getSocialAccessToken(authCode))
                .orElseThrow(() -> new RuntimeException());
    }

    public SocialProfileDto getSocialProfile(
            final AuthSocialType authSocialType,
            final SocialAccessTokenDto socialAccessTokenDto
    ) {
        return Optional.ofNullable(socialAccessTokenProviders.get(authSocialType))
                .map(socialAccessTokenProvider -> socialAccessTokenProvider.getSocialProfile(socialAccessTokenDto))
                .orElseThrow(() -> new RuntimeException());
    }
}
