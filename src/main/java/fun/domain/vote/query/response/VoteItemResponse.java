package fun.domain.vote.query.response;

import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.query.support.VoteItemRateSupport;

public record VoteItemResponse(
        Long voteItemId,
        String voteItemTitle,
        Integer voteRate
) {

    public static VoteItemResponse from(final VoteItem voteItem, final VoteItemRateSupport voteItemRateSupport) {
        return new VoteItemResponse(
                voteItem.getId(),
                voteItem.getContent(),
        voteItemRateSupport.getRate(voteItem)
        );
    }
}
