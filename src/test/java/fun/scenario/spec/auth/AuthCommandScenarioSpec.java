package fun.scenario.spec.auth;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.command.token.SocialAccessTokenRequest;
import fun.scenario.spec.ApiScenarioSpec;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import java.util.Map;

import static fun.scenario.spec.ApiUrl.POST_JOIN_SOCIAL_MEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SuppressWarnings("NonAsciiCharacters")
public final class AuthCommandScenarioSpec {

    public static AuthCommandScenarioSpec.AuthRequestSpec 클라이언트_요청() {
        return new AuthCommandScenarioSpec.AuthRequestSpec();
    }

    public static class AuthRequestSpec {

        private ExtractableResponse<Response> 응답;

        public AuthCommandScenarioSpec.AuthRequestSpec 액세스_토큰과_프로필_정보를_요청한다(
                final AuthSocialType 인증_소셜_타입,
                final SocialAccessTokenRequest 소셜_액세스_토큰_요청_객체
        ) {
            this.응답 = ApiScenarioSpec.post(
                    POST_JOIN_SOCIAL_MEMBER,
                    Map.of("socialType", 인증_소셜_타입.name()),
                    소셜_액세스_토큰_요청_객체
            );

            return this;
        }

        public AuthCommandScenarioSpec.AuthResponseSpec 서버_응답() {
            return new AuthCommandScenarioSpec.AuthResponseSpec(응답);
        }
    }

    public static class AuthResponseSpec {

        private final ExtractableResponse<Response> 응답;

        public AuthResponseSpec(final ExtractableResponse<Response> 응답) {
            this.응답 = 응답;
        }

        public void 토큰_정보와_프로필_정보_요청_성공을_검증한다() {
            assertThat(응답.statusCode()).isEqualTo(CREATED.value());
        }
    }
}
