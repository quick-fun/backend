package fun.domain.auth.config.filter;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

class AuthFilterTest {

    private AuthFilter authFilter;

    @BeforeEach
    void setUp() {
        authFilter = new AuthFilter(new AccessTokenVerifierStub());
    }

    @DisplayName("[SUCCESS] 액세스 토큰 서명을 검증하고 내부 값을 HttpServletRequest에 Authorization 이라는 이름으로 설정해둔다.")
    @Test
    void success_doFilter() throws ServletException, IOException {
        // given
        final MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, AccessTokenVerifierStub.ONE_ACCESS_TOKEN);
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final MockFilterChain filterChain = new MockFilterChain();

        // when
        authFilter.doFilter(request, response, filterChain);

        // then
        assertSoftly(softly -> {
            softly.assertThat(request.getAttribute(AUTHORIZATION)).isExactlyInstanceOf(Long.class);

            final Long actual = (Long) request.getAttribute(AUTHORIZATION);
            softly.assertThat(actual).isEqualTo(AccessTokenVerifierStub.ONE_RETURN_ID);
        });
    }
}
