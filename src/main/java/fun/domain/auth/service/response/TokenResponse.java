package fun.domain.auth.service.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
