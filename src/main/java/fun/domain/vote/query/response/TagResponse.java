package fun.domain.vote.query.response;

import fun.domain.vote.post.domain.VoteTag;

public record TagResponse(
        Long tagId,
        String tagTitle
) {

    public static TagResponse from(final VoteTag voteTag) {
        return new TagResponse(
                voteTag.getId(),
                voteTag.getTag().getTitle()
        );
    }
}
