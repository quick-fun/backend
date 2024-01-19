package fun.domain.auth.controller;

import fun.domain.auth.domain.AuthSocialType;
import fun.testconfig.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fun.ApiUrl.GET_AUTHENTICATION_SOCIAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthQueryControllerTest extends ControllerTestConfig {

    @DisplayName("[SUCCESS] 소셜 서버에 인증 코드를 요청한다.")
    @Test
    void success_findAuthCodeRequestUrl() throws Exception {
        // when
        when(
                authQueryService.findAuthCodeRequestUrl(
                        any(AuthSocialType.class)
                )
        ).thenReturn(
                "authCodeRequestUrl"
        );

        // expect
        mockMvc.perform(
                        get(GET_AUTHENTICATION_SOCIAL, AuthSocialType.KAKAO)
                                .pathInfo("/socialType")
                )
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("authCodeRequestUrl"))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("socialType").description("소셜 플랫폼 타입")
                        )
                ));
    }
}
