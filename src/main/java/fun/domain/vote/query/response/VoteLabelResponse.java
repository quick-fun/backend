package fun.domain.vote.query.response;

import fun.domain.vote.label.domain.VoteLabel;

public record VoteLabelResponse(
        Long labelId,
        String labelTitle
) {

    public static VoteLabelResponse from(final VoteLabel voteLabel) {
        return new VoteLabelResponse(
                voteLabel.getId(),
                voteLabel.getName()
        );
    }
}
