package fun.domain.vote.item.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoteItemMemberCommandRepository extends JpaRepository<VoteItemMember, Long> {

    @Query("""
        select count(0)
        from VoteItemMember vim
        where vim.memberId = :memberId
    """)
    Long countByMemberId(@Param("memberId") final Long memberId);
}
