package fun.domain.vote.post.controller.command;

import fun.domain.vote.post.service.command.request.CreateVotePostRequest;
import fun.testconfig.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static fun.ApiUrl.POST_VOTE_POST_CREATE;
import static fun.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotePostCommandControllerTest extends ControllerTestConfig {

    @DisplayName("[SUCCESS] 투표 게시글을 생성한다.")
    @Test
    void success_createVotePost() throws Exception {
        // given
        final CreateVotePostRequest createVotePostRequest = new CreateVotePostRequest(
                "투표 게시글 제목",
                "투표 게시글 내용",
                1L,
                List.of("투표 항목 내용1", "투표 항목 내용2"),
                LocalDateTime.now().plusHours(24),
                true
        );

        // when
        when(
                votePostCommandService.createVotePost(
                        any(Long.class),
                        any(CreateVotePostRequest.class)
                )
        ).thenReturn(
                1L
        );

        // then
        mockMvc.perform(
                        post(POST_VOTE_POST_CREATE)
                                .header(AUTHORIZATION_ACCESS_TOKEN, "Bearer accessToken")
                                .content(objectMapper.writeValueAsString(createVotePostRequest))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("/api/v1/posts/**"))
                .andDo(restDocs.document(
                        requestHeaders(
                                headerWithName(AUTHORIZATION_ACCESS_TOKEN).description("서명된 액세스 토큰")
                        ),
                        requestFields(
                                fieldWithPath("title").type(String.class).description("투표 게시글 제목"),
                                fieldWithPath("content").type(String.class).description("투표 게시글 내용"),
                                fieldWithPath("tagId").type(Long.class).description("투표 게시글 태그"),
                                fieldWithPath("voteItemTitles").type(Arrays.class).description("투표 항목 내용 목록"),
                                fieldWithPath("localDateTime").type(LocalDateTime.class).description("투표 게시글 마감 기한"),
                                fieldWithPath("isEnableComments").type(Boolean.class).description("투표 게시글 실시간 댓글 true/false")
                        )
                ));
    }
}
