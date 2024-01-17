package fun.domain.auth.service.command.token;

import fun.domain.member.domain.Member;

public record SocialProfileDto(
        Long id,
        String name,
        String gender,
        String ageRange
) {

    public Member toMember() {
        return new Member(
                name,
                "unknown",
                "unknown",
                0
        );
    }
}
