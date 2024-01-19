package fun.testconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.common.auth.AuthAccessToken;
import fun.common.auth.AuthRefreshToken;
import fun.domain.auth.config.filter.AccessTokenVerifier;
import fun.domain.auth.config.filter.AuthAccessFilter;
import fun.domain.auth.config.filter.AuthRefreshFilter;
import fun.domain.auth.config.filter.RefreshTokenVerifier;
import fun.domain.auth.controller.AuthCommandController;
import fun.domain.auth.controller.AuthQueryController;
import fun.domain.auth.domain.RefreshToken;
import fun.domain.auth.service.AuthCommandService;
import fun.domain.auth.service.AuthQueryService;
import fun.domain.vote.comment.controller.CommentCommandController;
import fun.domain.vote.comment.service.CommentCommandService;
import fun.domain.vote.post.controller.command.VotePostCommandController;
import fun.domain.vote.post.service.command.VotePostCommandService;
import fun.domain.vote.query.CommentQueryController;
import fun.domain.vote.query.CommentQueryService;
import fun.domain.vote.query.VotePostQueryController;
import fun.domain.vote.query.VotePostQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.ManualRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@WebMvcTest(controllers = {
        AuthCommandController.class,
        AuthQueryController.class,
        VotePostCommandController.class,
        VotePostQueryController.class,
        CommentCommandController.class,
        CommentQueryController.class
})
@Import({
        RestDocsConfig.class,
        TokenVerifierTestConfig.class
})
public abstract class ControllerTestConfig {

    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected RestDocumentationContextProvider restDocumentationContextProvider;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthCommandService authCommandService;

    @MockBean
    protected AuthQueryService authQueryService;

    @MockBean
    protected VotePostCommandService votePostCommandService;

    @MockBean
    protected VotePostQueryService votePostQueryService;

    @MockBean
    protected CommentCommandService commentCommandService;

    @MockBean
    protected CommentQueryService commentQueryService;

    @Autowired
    private AccessTokenVerifier accessTokenVerifier;

    @Autowired
    private RefreshTokenVerifier refreshTokenVerifier;

    @BeforeEach
    void controllerTestConfigSetUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilter(new AuthAccessFilter(accessTokenVerifier))
                .addFilter(new AuthRefreshFilter(refreshTokenVerifier))
                .alwaysDo(restDocs)
                .build();
    }

    protected void mockingAuthAccessToken(final AuthAccessToken authAccessToken) {
        when(
                accessTokenVerifier.verify(any(String.class))
        ).thenReturn(
                authAccessToken.memberId()
        );
    }

    protected void mockingAuthRefreshToken(final AuthRefreshToken authRefreshToken) {
        when(
                refreshTokenVerifier.verify(any(String.class))
        ).thenReturn(
                new RefreshToken(authRefreshToken.refreshToken())
        );
    }
}

@TestConfiguration
class RestDocsConfig {

    @Bean
    RestDocumentationResultHandler restDocumentationResultHandler() {
        return MockMvcRestDocumentation.document("{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
    }

    @Bean
    RestDocumentationContextProvider restDocumentationContextProvider() {
        return new ManualRestDocumentation();
    }
}

@TestConfiguration
class TokenVerifierTestConfig {

    @Bean
    AccessTokenVerifier accessTokenVerifier() {
        return Mockito.mock(AccessTokenVerifier.class);
    }

    @Bean
    RefreshTokenVerifier refreshTokenVerifier() {
        return Mockito.mock(RefreshTokenVerifier.class);
    }
}
