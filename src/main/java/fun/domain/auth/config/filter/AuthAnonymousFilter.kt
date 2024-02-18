package `fun`.domain.auth.config.filter

import `fun`.common.auth.AuthAccessToken.ANONYMOUS
import `fun`.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@RequiredArgsConstructor
@Component
class AuthAnonymousFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val maybeAnonymousRandomId = request.getHeader(AUTHORIZATION_ACCESS_TOKEN)
        if (maybeAnonymousRandomId.isNullOrBlank() && maybeAnonymousRandomId.startsWith("NoBearer ")) {
            request.setAttribute(ANONYMOUS, maybeAnonymousRandomId.replaceFirst("NoBearer", ""))
        }

        filterChain.doFilter(request, response)
    }
}
