package fun.domain.vote.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotePostCommandRepository extends JpaRepository<VotePost, Long> {

    @Query("""
        select count(0)
        from VotePost vp
        where vp.memberId = :memberId
    """)
    Long countByMemberId(final Long memberId);
}
