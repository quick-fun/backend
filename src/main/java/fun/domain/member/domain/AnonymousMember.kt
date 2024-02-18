package `fun`.domain.member.domain

import `fun`.common.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "anonymous_member")
data class AnonymousMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "random_id")
    val randomId: String,
) : BaseEntity()
