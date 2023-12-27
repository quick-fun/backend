package fun.domain.vote.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VotePostCommandRepository extends JpaRepository<VotePost, Long> {
}
