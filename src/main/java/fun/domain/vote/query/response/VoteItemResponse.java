package fun.domain.vote.query.response;

import fun.domain.vote.item.domain.VoteItem;
import fun.domain.vote.support.VoteItemRateSupport;

public record VoteItemResponse(
        Long voteItemId,
        String voteItemTitle,
        Integer voteRate,
        boolean visible
) {

    public static VoteItemResponse createVisibleRate(final VoteItem voteItem, final VoteItemRateSupport voteItemRateSupport) {
        return new VoteItemResponse(
                voteItem.getId(),
                voteItem.getContent(),
                voteItemRateSupport.getRate(voteItem),
                true
        );
    }

    public static VoteItemResponse createInvisibleRate(final VoteItem voteItem) {
        return new VoteItemResponse(
                voteItem.getId(),
                voteItem.getContent(),
                0,
                false
        );
    }
}
