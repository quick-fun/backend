package fun.common.auth;

public record AuthAccessToken(Long memberId) {

    public static final String AUTHORIZATION_ACCESS_TOKEN = "Authorization";
}
