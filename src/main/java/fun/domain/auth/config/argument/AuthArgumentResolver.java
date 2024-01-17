package fun.domain.auth.config.argument;

import fun.domain.auth.domain.MemberId;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthPrinciple.class)
               && parameter.getParameterType().isAssignableFrom(MemberId.class);
    }

    @Override
    public Object resolveArgument(
            final MethodParameter parameter,
            final ModelAndViewContainer mavContainer,
            final NativeWebRequest webRequest,
            final WebDataBinderFactory binderFactory
    ) throws Exception {
        return Optional.ofNullable(webRequest.getAttribute(AUTHORIZATION, 0))
                .filter(value -> value instanceof Long)
                .map(value -> new MemberId((Long) value))
                .orElseThrow(() -> new IllegalArgumentException("사용자 식별자 값이 존재하지 않습니다."));
    }
}
