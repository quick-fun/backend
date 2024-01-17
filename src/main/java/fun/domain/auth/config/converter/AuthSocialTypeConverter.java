package fun.domain.auth.config.converter;

import fun.domain.auth.domain.AuthSocialType;
import org.springframework.core.convert.converter.Converter;

public class AuthSocialTypeConverter implements Converter<String, AuthSocialType> {

    @Override
    public AuthSocialType convert(final String source) {
        return AuthSocialType.from(source.toUpperCase());
    }
}
