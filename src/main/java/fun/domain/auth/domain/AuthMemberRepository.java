package fun.domain.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthMemberRepository extends JpaRepository<AuthMember, Long> {

    Optional<AuthMember> findByAuthId(final Long authId);

    Optional<AuthMember> findByRefreshToken(final RefreshToken refreshToken);
}
