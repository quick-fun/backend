package fun.domain.vote.comment.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentCommandRepository extends JpaRepository<Comment, Long> {
}
