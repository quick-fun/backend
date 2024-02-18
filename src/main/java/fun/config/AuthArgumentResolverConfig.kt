package `fun`.config

import `fun`.domain.auth.config.argument.AuthAccessArgumentResolver
import `fun`.domain.auth.config.argument.AuthRefreshArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AuthArgumentResolverConfig : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver?>) {
        resolvers.add(AuthAccessArgumentResolver())
        resolvers.add(AuthRefreshArgumentResolver())
    }
}
