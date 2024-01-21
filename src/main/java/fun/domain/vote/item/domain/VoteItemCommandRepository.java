package fun.domain.vote.item.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteItemCommandRepository extends JpaRepository<VoteItem, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("""
        select vi
        from VoteItem vi
        where vi.id = :voteItemId
    """)
    VoteItem findByIdOptimisticLock(final Long voteItemId);

    List<VoteItem> findByVotePostId(final Long votePostId);
}
