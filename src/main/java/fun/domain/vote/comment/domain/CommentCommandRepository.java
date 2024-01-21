package fun.domain.vote.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentCommandRepository extends JpaRepository<Comment, Long> {

    @Query("""
                select count(0)
                from Comment c
                where c.memberId = :memberId
            """)
    Long countByMemberId(final Long memberId);
}
