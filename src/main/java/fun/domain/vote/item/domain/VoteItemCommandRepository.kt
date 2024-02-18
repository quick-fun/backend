package `fun`.domain.vote.item.domain

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query

interface VoteItemCommandRepository : JpaRepository<VoteItem, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query(
        """
        select vi
        from VoteItem vi
        where vi.id = :voteItemId
    """
    )
    fun findByIdOptimisticLock(voteItemId: Long): VoteItem?

    fun findByVotePostId(votePostId: Long): List<VoteItem>
}
