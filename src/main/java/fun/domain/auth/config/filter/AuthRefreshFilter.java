package fun.domain.auth.config.filter;

import fun.domain.auth.domain.RefreshToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static fun.common.auth.AuthRefreshToken.AUTHORIZATION_REFRESH_TOKEN;

@RequiredArgsConstructor
@Component
public class AuthRefreshFilter extends OncePerRequestFilter {

    private final RefreshTokenVerifier refreshTokenVerifier;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String signedRefreshToken = request.getHeader(AUTHORIZATION_REFRESH_TOKEN);
        if (signedRefreshToken != null) {
            final RefreshToken verifiedRefreshToken = refreshTokenVerifier.verify(signedRefreshToken);

            request.setAttribute(AUTHORIZATION_REFRESH_TOKEN, verifiedRefreshToken.getValue());
        }

        filterChain.doFilter(request, response);
    }
}
