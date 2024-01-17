package fun.scenario.spec.auth;

import fun.domain.auth.domain.AuthSocialType;
import fun.scenario.spec.ApiScenarioSpec;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

import static fun.ApiUrl.GET_AUTHENTICATION_SOCIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.LOCATION;

@SuppressWarnings("NonAsciiCharacters")
public final class AuthQueryScenarioSpec {

    public static AuthQueryScenarioSpec.AuthRequestSpec 클라이언트_요청() {
        return new AuthQueryScenarioSpec.AuthRequestSpec();
    }

    public static class AuthRequestSpec {

        private ExtractableResponse<Response> 응답;

        public AuthQueryScenarioSpec.AuthRequestSpec 소셜_인증을_위한_리다이렉트_주소를_요청한다(
                final AuthSocialType 인증_소셜_타입
        ) {
            this.응답 = ApiScenarioSpec.get(
                    GET_AUTHENTICATION_SOCIAL,
                    Map.of("socialType", 인증_소셜_타입.name())
            );

            return this;
        }

        public AuthQueryScenarioSpec.AuthResponseSpec 서버_응답() {
            return new AuthQueryScenarioSpec.AuthResponseSpec(응답);
        }
    }

    public static class AuthResponseSpec {

        private final ExtractableResponse<Response> 응답;

        public AuthResponseSpec(final ExtractableResponse<Response> 응답) {
            this.응답 = 응답;
        }

        public void 소셜_인증을_위한_리다이렉트_주소_요청_성공을_검증한다() {
            assertThat(응답.header(LOCATION)).startsWithIgnoringCase("https");
        }
    }
}
