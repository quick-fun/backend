package fun.infra.config;

import fun.infra.auth.kakao.KakaoAuthClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @Bean
    KakaoAuthClient kakaoAuthClient() {
        final RestClientAdapter adapter = RestClientAdapter.create(RestClient.create());
        final HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(KakaoAuthClient.class);
    }
}
