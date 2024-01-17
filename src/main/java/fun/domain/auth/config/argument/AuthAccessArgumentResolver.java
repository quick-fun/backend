package fun.domain.auth.config.argument;

import fun.common.auth.AuthAccessToken;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static fun.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN;

public class AuthAccessArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthAccessPrinciple.class)
               && parameter.getParameterType().isAssignableFrom(AuthAccessToken.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        return Optional.ofNullable(webRequest.getAttribute(AUTHORIZATION_ACCESS_TOKEN, 0))
                .filter(value -> value instanceof Long)
                .map(value -> new AuthAccessToken((Long) value))
                .orElseThrow(() -> new IllegalArgumentException("사용자 식별자 값이 존재하지 않습니다."));
    }
}
