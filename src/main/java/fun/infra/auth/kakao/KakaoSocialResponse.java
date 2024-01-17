package fun.infra.auth.kakao;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import fun.domain.auth.service.command.token.SocialProfileDto;

import static com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

@JsonNaming(SnakeCaseStrategy.class)
public record KakaoSocialResponse(
        Long id,
        boolean hasSignedUp,
        KakaoAccount kakaoAccount
) {

    @JsonNaming(SnakeCaseStrategy.class)
    public record KakaoAccount(
            String name,
            String gender,
            String ageRange,
            Profile profile
    ) {
    }

    @JsonNaming(SnakeCaseStrategy.class)
    public record Profile(
            String nickname
    ) {
    }

    public SocialProfileDto toSocialProfileResponse() {
        return new SocialProfileDto(
                this.id,
                this.kakaoAccount.profile.nickname,
                this.kakaoAccount.gender,
                this.kakaoAccount.ageRange
        );
    }
}
