package fun.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
public class AuthMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "auth_id", nullable = false)
    private Long authId;

    @Embedded
    private SocialAccessToken socialAccessToken;

    @Embedded
    private RefreshToken refreshToken;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "auth_social_type", nullable = false)
    private AuthSocialType authSocialType;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    protected AuthMember() {
    }

    public AuthMember(
            final Long authId,
            final SocialAccessToken socialAccessToken,
            final RefreshToken refreshToken,
            final AuthSocialType authSocialType,
            final Long memberId
    ) {
        this(null, authId, socialAccessToken, refreshToken, authSocialType, memberId);
    }

    protected AuthMember(
            final Long id,
            final Long authId,
            final SocialAccessToken socialAccessToken,
            final RefreshToken refreshToken,
            final AuthSocialType authSocialType,
            final Long memberId
    ) {
        this.id = id;
        this.authId = authId;
        this.socialAccessToken = socialAccessToken;
        this.refreshToken = refreshToken;
        this.authSocialType = authSocialType;
        this.memberId = memberId;
    }

    public String signAccessToken(final AccessTokenSigner accessTokenSigner) {
        return accessTokenSigner.sign(this.memberId);
    }

    public String signRefreshToken(final RefreshTokenSigner refreshTokenSigner) {
        return this.refreshToken.sign(refreshTokenSigner);
    }

    public void publishRefreshToken(final RefreshToken verifiedRefreshToken) {
        if (this.refreshToken.isNotSame(verifiedRefreshToken)) {
            throw new IllegalArgumentException("인증용 사용자의 리프래시 토큰을 새로 발급하기 위해서는 이전에 발급된 리프래시 토큰값이 필요합니다.");
        }

        this.refreshToken = RefreshToken.publishRefreshToken();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthMember that = (AuthMember) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
