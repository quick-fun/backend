package fun.domain.auth.service;

import fun.domain.auth.domain.AccessTokenSigner;
import fun.domain.auth.domain.AuthMember;
import fun.domain.auth.domain.AuthMemberRepository;
import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.domain.RefreshTokenSigner;
import fun.domain.auth.domain.SocialAccessToken;
import fun.domain.auth.service.event.MemberCreateEvent;
import fun.domain.auth.service.response.TokenResponse;
import fun.domain.auth.service.token.SocialAccessTokenDto;
import fun.domain.auth.service.token.SocialAccessTokenProviderComposite;
import fun.domain.auth.service.token.SocialProfileDto;
import fun.domain.member.domain.Member;
import fun.domain.member.domain.MemberCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthCommandService {

    private final SocialAccessTokenProviderComposite socialAccessTokenProviderComposite;
    private final AccessTokenSigner accessTokenSigner;
    private final RefreshTokenSigner refreshTokenSigner;
    private final AuthMemberRepository authMemberRepository;
    private final MemberCommandRepository memberCommandRepository;
    private final ApplicationEventPublisher eventPublisher;

    public TokenResponse createTokens(final AuthSocialType authSocialType, final String authCode) {
        final SocialAccessTokenDto socialAccessTokenDto = socialAccessTokenProviderComposite.getSocialAccessToken(authSocialType, authCode);
        final SocialProfileDto socialProfile = socialAccessTokenProviderComposite.getSocialProfile(authSocialType, socialAccessTokenDto);

        final Optional<AuthMember> maybeAuthMember = authMemberRepository.findByAuthId(socialProfile.id());
        if (maybeAuthMember.isPresent()) {
            final AuthMember findAuthMember = maybeAuthMember.get();
            return createTokenResponse(findAuthMember);
        }

        final Member savedMember = createNewMember(socialProfile);
        final AuthMember savedAuthMember = createNewAuthMember(authSocialType, socialProfile, socialAccessTokenDto, savedMember);
        final TokenResponse tokenResponse = createTokenResponse(savedAuthMember);

        eventPublisher.publishEvent(new MemberCreateEvent(savedMember.getId()));

        return tokenResponse;
    }

    private Member createNewMember(final SocialProfileDto socialProfile) {
        return memberCommandRepository.save(socialProfile.toMember());
    }

    private AuthMember createNewAuthMember(
            final AuthSocialType authSocialType,
            final SocialProfileDto socialProfileDto,
            final SocialAccessTokenDto socialAccessTokenDto,
            final Member savedMember
    ) {
        final Long authId = socialProfileDto.id();
        final SocialAccessToken socialAccessToken = socialAccessTokenDto.toSocialAccessToken();
        final RefreshToken refreshToken = RefreshToken.publishRefreshToken();
        final Long memberId = savedMember.getId();

        return authMemberRepository.save(
                new AuthMember(
                        authId,
                        socialAccessToken,
                        refreshToken,
                        authSocialType,
                        memberId
                )
        );
    }

    private TokenResponse createTokenResponse(final AuthMember findAuthMember) {
        return new TokenResponse(
                findAuthMember.signAccessToken(accessTokenSigner),
                findAuthMember.signRefreshToken(refreshTokenSigner)
        );
    }

    public TokenResponse recreateTokens(final RefreshToken requestRefreshToken) {
        final AuthMember findAuthMember = getAuthMemberOrThrowException(requestRefreshToken);
        findAuthMember.publishRefreshToken(requestRefreshToken);

        return createTokenResponse(findAuthMember);
    }

    private AuthMember getAuthMemberOrThrowException(final RefreshToken refreshToken) {
        return authMemberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("리프래시 토큰으로 인증용 사용자를 조회할 수 없습니다."));
    }
}
