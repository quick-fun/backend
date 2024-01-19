package fun.domain.vote.query;

import fun.domain.medal.domain.Medal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedalQueryRepository extends JpaRepository<Medal, Long> {
}
