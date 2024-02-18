//package fun.domain.vote.item.controller;
//
//import fun.common.auth.AuthAccessToken;
//import fun.testconfig.ControllerTestConfig;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.context.annotation.Configuration;
//
//import static fun.ApiUrl.POST_VOTE_VOTE_ITEM;
//import static fun.common.auth.AuthAccessToken.AUTHORIZATION_ACCESS_TOKEN;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
//import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
//import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Configuration
//class VoteItemCommandControllerTest extends ControllerTestConfig {
//
//    @DisplayName("[SUCCESS] 투표 항목에 투표한다.")
//    @Test
//    void success_voteVoteItem() throws Exception {
//        // when
//        mockingAuthAccessToken(new AuthAccessToken(1L, null));
//        when(
//                voteItemCommandService.voteVoteItem(
//                        anyLong(),
//                        anyLong(),
//                        anyLong()
//                )
//        ).thenReturn(
//                1L
//        );
//
//        // then
//        mockMvc.perform(
//                        post(POST_VOTE_VOTE_ITEM, 1L, 1L)
//                                .header(AUTHORIZATION_ACCESS_TOKEN, "Bearer accessToken")
//                                .pathInfo("/votePostId")
//                                .pathInfo("/voteItemId")
//                )
//                .andExpect(status().isCreated())
//                .andExpect(redirectedUrlPattern("/api/v1/posts/**"))
//                .andDo(restDocs.document(
//                        requestHeaders(
//                                headerWithName(AUTHORIZATION_ACCESS_TOKEN).description("서명된 액세스 토큰")
//                        ),
//                        pathParameters(
//                                parameterWithName("votePostId").description("투표 게시글 식별자값"),
//                                parameterWithName("voteItemId").description("투표 항목 식별자값")
//                        )
//                ));
//    }
//}
