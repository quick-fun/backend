package `fun`.domain.auth.config.argument

import `fun`.common.auth.AuthAccessToken
import `fun`.domain.member.domain.AnonymousMemberCommandRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthAccessArgumentResolver(
) : HandlerMethodArgumentResolver {
    private lateinit var anonymousMemberCommandRepository: AnonymousMemberCommandRepository

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return (parameter.hasParameterAnnotation(AuthAccessPrinciple::class.java)
                && parameter.getParameterType().isAssignableFrom(AuthAccessToken::class.java))
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): AuthAccessToken {
        val request = webRequest.nativeRequest as HttpServletRequest
        val authAccessPrinciple = parameter.getParameterAnnotation(AuthAccessPrinciple::class.java)

        if (authAccessPrinciple.isAnonymous) {
            return getAnonymousMember(request)
        }
        return getMember(request)
    }

    private fun getAnonymousMember(request: HttpServletRequest): AuthAccessToken {
        val anonymousRandomId = request.getAttribute(AuthAccessToken.ANONYMOUS) as? String
            ?: throw IllegalArgumentException("익명 랜덤 아이디 값이 존재하지 않습니다.")

        val findAnonymousMemberId = (anonymousMemberCommandRepository.findIdByRandomId(anonymousRandomId)
            ?: throw IllegalArgumentException("익명 랜덤 아이디로 등록된 익명 사용자의 식별자값이 존재하지 않습니다."))

        return AuthAccessToken(null, findAnonymousMemberId)
    }

    private fun getMember(request: HttpServletRequest): AuthAccessToken {
        val authAccessTokenValue = request.getAttribute(AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN)
            ?: throw IllegalArgumentException("사용자 식별자 값이 존재하지 않습니다.")

        if (authAccessTokenValue !is Long) {
            throw IllegalArgumentException("잘못된 사용자 식별자 값입니다.")
        }

        return AuthAccessToken(authAccessTokenValue, null)
    }
}
