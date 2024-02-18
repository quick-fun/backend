package fun.domain.vote.comment.controller;

import fun.common.auth.AuthAccessToken;
import fun.domain.vote.comment.service.request.CreateCommentRequest;
import fun.testconfig.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static fun.ApiUrl.POST_COMMENT;
import static fun.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentCommandControllerTest extends ControllerTestConfig {

    @DisplayName("[SUCCESS] 댓글을 생성한다.")
    @Test
    void success_createComment() throws Exception {
        // given
        final CreateCommentRequest createCommentRequest = new CreateCommentRequest("댓글 내용");

        // when
        mockingAuthAccessToken(new AuthAccessToken(1L, null));
        when(
                commentCommandService.createComment(
                        anyLong(),
                        any(CreateCommentRequest.class),
                        anyLong()
                )
        ).thenReturn(
                1L
        );

        // then
        mockMvc.perform(
                        post(POST_COMMENT, 1L)
                                .pathInfo("/votePostId")
                                .header(AUTHORIZATION_ACCESS_TOKEN, "Bearer accessToken")
                                .content(objectMapper.writeValueAsString(createCommentRequest))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/v1/posts/**"))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(AUTHORIZATION_ACCESS_TOKEN).description("서명된 액세스 토큰")
                        ),
                        pathParameters(
                                parameterWithName("votePostId").description("투표 게시글 식별자값")
                        ),
                        requestFields(
                                fieldWithPath("content").type(String.class).description("댓글 내용")
                        )
                ));
    }
}
