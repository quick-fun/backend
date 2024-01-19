package fun.domain.medal.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedalCommandRepository extends JpaRepository<Medal, Long> {
}
