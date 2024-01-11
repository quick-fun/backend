package fun.domain.auth;

import java.util.Arrays;

public enum AuthSocialType {
    KAKAO;

    public static AuthSocialType from(final String value) {
        return Arrays.stream(values())
                .filter(authSocialType -> value.equals(authSocialType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 소셜 타입입니다."));
    }
}
