package fun.domain.auth.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static fun.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN;

@RequiredArgsConstructor
@Component
public class AuthAccessFilter extends OncePerRequestFilter {

    private final AccessTokenVerifier accessTokenVerifier;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {
        final String bearer = request.getHeader(AUTHORIZATION_ACCESS_TOKEN);
        if (bearer != null) {
            final String signedAccessToken = bearer.replaceFirst("Bearer ", "");
            final Long memberId = accessTokenVerifier.verify(signedAccessToken);

            request.setAttribute(AUTHORIZATION_ACCESS_TOKEN, memberId);
        }

        filterChain.doFilter(request, response);
    }
}
