package fun.domain.auth.service.command.response;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}
