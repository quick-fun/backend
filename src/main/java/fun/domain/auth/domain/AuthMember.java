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

    @Embedded
    private MemberId memberId;

    protected AuthMember() {
    }

    public AuthMember(
            final Long authId,
            final SocialAccessToken socialAccessToken,
            final RefreshToken refreshToken,
            final AuthSocialType authSocialType,
            final MemberId memberId
    ) {
        this(null, authId, socialAccessToken, refreshToken, authSocialType, memberId);
    }

    protected AuthMember(
            final Long id,
            final Long authId,
            final SocialAccessToken socialAccessToken,
            final RefreshToken refreshToken,
            final AuthSocialType authSocialType,
            final MemberId memberId
    ) {
        this.id = id;
        this.authId = authId;
        this.socialAccessToken = socialAccessToken;
        this.refreshToken = refreshToken;
        this.authSocialType = authSocialType;
        this.memberId = memberId;
    }

    public String publishAccessToken(final AccessTokenSigner accessTokenSigner) {
        return accessTokenSigner.sign(this.memberId);
    }

    public String publishRefreshToken(final RefreshTokenSigner refreshTokenSigner) {
        return this.refreshToken.sign(refreshTokenSigner);
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
