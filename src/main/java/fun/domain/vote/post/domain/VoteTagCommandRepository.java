package fun.domain.vote.post.domain;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteTagCommandRepository extends JpaRepository<VoteTag, Long> {

    default VoteTag getVoteTagById(final Long voteTagId) {
        return findById(voteTagId).orElseThrow(() -> new EmptyResultDataAccessException("투표 태그 식별자값으로 해당하는 데이터를 조회할 수 없습니다.", 1));
    }
}
