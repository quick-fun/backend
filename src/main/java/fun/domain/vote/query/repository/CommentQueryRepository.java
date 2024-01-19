package fun.domain.vote.query.repository;

import fun.domain.vote.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentQueryRepository extends JpaRepository<Comment, Long> {

    @Query("""
                select c
                from Comment c
                where c.votePostId = :votePostId
                and c.id <= :cursor
                order by c.id desc
                limit :limit
            """)
    List<Comment> pageByVotePostId(
            @Param("votePostId") final Long votePostId,
            @Param("cursor") final Long cursor,
            @Param("limit") final Long limit
    );
}
