package fun.domain.vote.label.domain;

import fun.domain.vote.label.domain.VoteLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteLabelCommandRepository extends JpaRepository<VoteLabel, Long> {
}
