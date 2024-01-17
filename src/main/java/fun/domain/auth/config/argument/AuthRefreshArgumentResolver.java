package fun.domain.auth.config.argument;

import fun.common.auth.AuthRefreshToken;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static fun.common.auth.AuthRefreshToken.AUTHORIZATION_REFRESH_TOKEN;

public class AuthRefreshArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthRefreshPrinciple.class)
               && parameter.getParameterType().isAssignableFrom(AuthRefreshToken.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        return Optional.ofNullable(webRequest.getAttribute(AUTHORIZATION_REFRESH_TOKEN, 0))
                .filter(value -> value instanceof String)
                .map(value -> new AuthRefreshToken((String) value))
                .orElseThrow(() -> new IllegalArgumentException("리프래시 토큰 값이 존재하지 않습니다."));
    }
}
