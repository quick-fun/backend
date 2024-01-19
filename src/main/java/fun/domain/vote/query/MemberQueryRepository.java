package fun.domain.vote.query;

import fun.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberQueryRepository extends JpaRepository<Member, Long> {
}
