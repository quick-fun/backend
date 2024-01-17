package fun.domain.auth.service.query;

import fun.testconfig.ServiceTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthQueryServiceTest extends ServiceTestConfig {

    private AuthQueryService authQueryService;

    @BeforeEach
    void setUp() {
        authQueryService = new AuthQueryService(
                new AuthCodeProviderCompositeStub()
        );
    }

    @DisplayName("[SUCCESS] 인증 코드를 요청할 url을 가져온다.")
    @Test
    void success_findAuthCodeRequestUrl() {
        // when
        final String actual = authQueryService.findAuthCodeRequestUrl(AuthCodeProviderCompositeStub.MOCK_AUTH_SOCIAL_TYPE);

        // expect
        assertThat(actual).startsWith("https://");
    }
}
