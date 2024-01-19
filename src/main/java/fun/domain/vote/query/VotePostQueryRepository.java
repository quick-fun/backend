package fun.domain.vote.query;

import fun.domain.vote.post.domain.VotePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotePostQueryRepository extends JpaRepository<VotePost, Long> {
}
