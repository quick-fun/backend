package fun.config.auth;

import fun.domain.auth.argument.AuthArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthArgumentResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver());
    }

    @Bean
    AuthArgumentResolver authArgumentResolver() {
        return new AuthArgumentResolver();
    }
}
