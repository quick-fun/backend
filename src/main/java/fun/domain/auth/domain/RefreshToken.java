package fun.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Embeddable
public class RefreshToken {

    public static RefreshToken publishRefreshToken() {
        return new RefreshToken(UUID.randomUUID().toString());
    }

    @Column(name = "refresh_token", nullable = false)
    private String value;

    protected RefreshToken() {
    }

    public RefreshToken(final String value) {
        this.value = value;
    }

    public String sign(final RefreshTokenSigner refreshTokenSigner) {
        return refreshTokenSigner.sign(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final RefreshToken that = (RefreshToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
