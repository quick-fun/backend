package fun.domain.vote.support;

import fun.domain.vote.item.domain.VoteCount;
import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.post.domain.VotePost;

public class VoteItemRateSupport {

    private final VoteCount totalVoteCount;

    public VoteItemRateSupport(final VotePost votePost) {
        this.totalVoteCount = votePost.getTotalVoteCount();
    }

    public int getRate(final VoteItem requestVoteItem) {
        return requestVoteItem.divideVoteCountRate(totalVoteCount);
    }
}
