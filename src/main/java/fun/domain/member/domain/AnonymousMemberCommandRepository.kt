package `fun`.domain.member.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AnonymousMemberCommandRepository : JpaRepository<AnonymousMember, Long> {
    @Query("""
        select am.id
        from AnonymousMember am
        where am.randomId = :randomId
    """)
    fun findIdByRandomId(@Param("randomId") randomId: String): Long?
}
