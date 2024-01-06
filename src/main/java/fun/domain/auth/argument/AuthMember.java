package fun.domain.auth.argument;

import lombok.Getter;

import java.util.Objects;

@Getter
public class AuthMember {

    private Long memberId;

    protected AuthMember() {
    }

    public AuthMember(final Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AuthMember that = (AuthMember) o;
        return Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}
