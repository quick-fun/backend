package `fun`.domain.vote.item.service.response

data class VoteVoteItemResponse(
    val voteItems: List<VoteItemResponse>
)

data class VoteItemResponse(
    val voteItemId: Long,
    val voteItemTitle: String,
    val voteRate: Int
)
