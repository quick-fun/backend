package fun.domain.vote.query;

import fun.domain.vote.query.response.CommentPageResponse;
import fun.testconfig.ControllerTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static fun.ApiUrl.GET_COMMENT_PAGE;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentQueryControllerTest extends ControllerTestConfig {

    @DisplayName("[SUCCESS] 투표 게시글 페이징 목록을 조회한다.")
    @Test
    void success_readVotePostPage() throws Exception {
        // when
        when(
                commentQueryService.pageComments(anyLong(), anyLong(), anyLong())
        ).thenReturn(
                new CommentPageResponse(
                        List.of(
                                new CommentPageResponse.CommentPageSubResponse(
                                        1L,
                                        1L,
                                        "사용자 닉네임",
                                        "사용자 메달명",
                                        "사용자 이미지 url",
                                        "댓글 내용",
                                        LocalDateTime.now()
                                )
                        )
                )
        );

        // then
        mockMvc.perform(
                        get(GET_COMMENT_PAGE, 1L)
                                .pathInfo("/votePostId")
                                .queryParam("cursor", "10")
                                .queryParam("limit", "10")
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(restDocs.document(
                        pathParameters(
                          parameterWithName("votePostId").description("투표 게시글 식별자값")
                        ),
                        queryParameters(
                                parameterWithName("cursor").description("이전 데이터의 마지막 식별자값 + 1"),
                                parameterWithName("limit").description("원하는 데이터의 개수")
                        ),
                        responseFields(
                                fieldWithPath("data.[].commentId").type(String.class).description("댓글 식별자값"),
                                fieldWithPath("data.[].memberId").type(String.class).description("작성자(사용자) 식별자값"),
                                fieldWithPath("data.[].nickname").type(String.class).description("작성자(사용자) 닉네임"),
                                fieldWithPath("data.[].memberMedalTitle").type(Number.class).description("작성자(사용자) 메달명"),
                                fieldWithPath("data.[].imageUrl").type(String.class).description("작성자(사용자) 이미지 url"),
                                fieldWithPath("data.[].content").type(Number.class).description("댓글 내용"),
                                fieldWithPath("data.[].createdAt").type(Number.class).description("댓글 작성일자")
                        )
                ));
    }
}
