package fun.domain.auth.controller.command;

import fun.common.auth.AuthAccessToken;
import fun.common.auth.AuthRefreshToken;
import fun.domain.auth.domain.AuthSocialType;
import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.service.command.response.TokenResponse;
import fun.testconfig.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fun.ApiUrl.POST_JOIN_SOCIAL_MEMBER;
import static fun.ApiUrl.PUT_NEW_TOKENS;
import static fun.common.auth.AuthRefreshToken.AUTHORIZATION_REFRESH_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthCommandControllerTest extends ControllerTestConfig {

    @DisplayName("[SUCCESS] 소셜 인증 코드를 이용하여 회원가입[신규] 후 토큰(액세스, 리프래시)을 반환한다.")
    @Test
    void success_createMemberAndReturnToken() throws Exception {
        // when
        mockingAuthAccessToken(new AuthAccessToken(1L));
        when(
                authCommandService.createTokens(
                        any(AuthSocialType.class),
                        anyString()
                )
        ).thenReturn(
                new TokenResponse("newSignedAccessToken", "newSignedRefreshToken")
        );

        // expect
        mockMvc.perform(
                        post(POST_JOIN_SOCIAL_MEMBER, AuthSocialType.KAKAO)
                                .pathInfo("/socialType")
                                .queryParam("authCode", "AuthCode")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(restDocs.document(
                        pathParameters(
                                parameterWithName("socialType").description("소셜 플랫폼 타입")
                        ),
                        queryParameters(
                                parameterWithName("authCode").description("소셜 인증 코드")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(String.class).description("서명된 액세스 토큰"),
                                fieldWithPath("refreshToken").type(String.class).description("서명된 리프래시 토큰")
                        )
                ));
    }

    @DisplayName("[SUCCESS] 리프래시 토큰을 이용하여 새로운 토큰(액세스, 리프래시)을 반환한다.")
    @Test
    void success_recreateTokens() throws Exception {
        // when
        mockingAuthRefreshToken(new AuthRefreshToken("newSignedRefreshToken"));
        when(
                authCommandService.recreateTokens(any(RefreshToken.class))
        ).thenReturn(
                new TokenResponse("newSignedAccessToken", "newSignedRefreshToken")
        );

        // expect
        mockMvc.perform(
                        put(PUT_NEW_TOKENS)
                                .header(AUTHORIZATION_REFRESH_TOKEN, "signedRefreshToken")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName("RefreshToken").description("서명된 리프래시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(String.class).description("서명된 액세스 토큰"),
                                fieldWithPath("refreshToken").type(String.class).description("서명된 리프래시 토큰")
                        )
                ));
    }
}
