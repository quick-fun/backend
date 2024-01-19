package fun.domain.vote.query.response;

import fun.domain.medal.domain.Medal;
import fun.domain.member.domain.Member;

public record MemberProfileResponse(
        Long memberId,
        String nickname,
        String memberMedalTitle,
        String imageUrl
) {

    public static MemberProfileResponse from(final Member member, final Medal medal) {
        return new MemberProfileResponse(
                member.getId(),
                member.getNickname(),
                medal.getMedalType().getTitle(),
                member.getProfileImageUrl()
        );
    }
}
