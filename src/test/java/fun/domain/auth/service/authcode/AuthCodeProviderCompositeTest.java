package fun.domain.auth.service.authcode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AuthCodeProviderCompositeTest {

    private AuthCodeProviderComposite authCodeProviderComposite;

    @BeforeEach
    void setUp() {
        this.authCodeProviderComposite = new AuthCodeProviderComposite(
                Set.of(new AuthCodeProviderStub())
        );
    }

    @DisplayName("[SUCCESS] 인증 코드를 얻기 위한 요청 url을 가져온다.")
    @Test
    void success_getRequestUrl() {
        // when
        final String actual = authCodeProviderComposite.getRequestUrl(AuthCodeProviderStub.MOCK_AUTH_SOCIAL_TYPE);

        // then
        assertThat(actual)
                .isNotNull()
                .isNotBlank()
                .startsWith("https://");
    }
}
