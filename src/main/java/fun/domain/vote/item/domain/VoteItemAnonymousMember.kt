package `fun`.domain.vote.item.domain

import jakarta.persistence.*

@Entity
class VoteItemAnonymousMember(
    _id: Long? = null,
    _anonymousMemberId: Long,
    _voteItemId: Long,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = _id

    @Column(name = "anonymous_member_id")
    val anonymousMemberId: Long = _anonymousMemberId

    @Column(name = "vote_item_id")
    val voteItemId: Long = _voteItemId

    fun isSameAnonymousMember(requestAnonymousMemberId: Long): Boolean {
        return this.anonymousMemberId == requestAnonymousMemberId;
    }
}
