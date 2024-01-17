package fun.domain.auth.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Getter
@Embeddable
public class SocialAccessToken {

    @Column(name = "social_access_token", nullable = false)
    private String value;

    protected SocialAccessToken() {
    }

    public SocialAccessToken(final String value) {
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SocialAccessToken that = (SocialAccessToken) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
