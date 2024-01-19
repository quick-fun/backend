package fun.domain.vote.query.repository;

import fun.domain.vote.label.domain.VoteLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteLabelQueryRepository extends JpaRepository<VoteLabel, Long> {
}
