package fun.domain.vote.query.repository;

import fun.domain.vote.post.domain.VotePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotePostQueryRepository extends JpaRepository<VotePost, Long> {

    @Query("""
        select vp
        from VotePost vp
        where vp.id <= :cursor
        order by vp.id desc
        limit :limit
    """)
    List<VotePost> findVotePostPage(@Param("cursor") final Long cursor, @Param("limit") final Long limit);
}
