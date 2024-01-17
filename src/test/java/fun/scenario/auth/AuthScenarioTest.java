package fun.scenario.auth;

import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.service.command.token.SocialAccessTokenRequest;
import fun.scenario.spec.auth.AuthCommandScenarioSpec;
import fun.testconfig.ScenarioTestConfig;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class AuthScenarioTest extends ScenarioTestConfig {

    @Test
    void authCode로_토큰_정보와_프로필_정보를_요청한다() {
        final String authCode = "";

//        AuthQueryScenarioSpec
//                .클라이언트_요청()
//                .소셜_인증을_위한_리다이렉트_주소를_요청한다(AuthSocialType.KAKAO)
//                .서버_응답()
//                .소셜_인증을_위한_리다이렉트_주소_요청_성공을_검증한다();

        AuthCommandScenarioSpec
                .클라이언트_요청()
                .액세스_토큰과_프로필_정보를_요청한다(
                        AuthSocialType.KAKAO,
                        new SocialAccessTokenRequest(
                                "authorization_code",
                                "client_id",
                                "redirect_uri",
                                "code",
                                "client_secret"
                        )
                )
                .서버_응답()
                .토큰_정보와_프로필_정보_요청_성공을_검증한다();
    }
}
