package fun.common.auth;

public record AuthRefreshToken(String refreshToken) {

    public static final String AUTHORIZATION_REFRESH_TOKEN = "RefreshToken";
}
