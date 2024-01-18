package fun.testconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import fun.common.auth.AuthAccessToken;
import fun.domain.auth.config.filter.AccessTokenVerifier;
import fun.domain.auth.config.filter.AuthAccessFilter;
import fun.domain.auth.config.filter.RefreshTokenVerifier;
import fun.domain.auth.controller.command.AuthCommandController;
import fun.domain.auth.controller.query.AuthQueryController;
import fun.domain.auth.service.command.AuthCommandService;
import fun.domain.auth.service.query.AuthQueryService;
import fun.domain.vote.post.controller.command.VotePostCommandController;
import fun.domain.vote.post.service.command.VotePostCommandService;
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
        VotePostCommandController.class
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

    @Autowired
    private AccessTokenVerifier accessTokenVerifier;

    @MockBean
    protected AuthCommandService authCommandService;

    @MockBean
    protected AuthQueryService authQueryService;

    @MockBean
    protected VotePostCommandService votePostCommandService;

    @BeforeEach
    void controllerTestConfigSetUp(final WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilter(new AuthAccessFilter(accessTokenVerifier))
                .alwaysDo(restDocs)
                .build();
    }

    protected void mockingAuthAccessTokenReturn(final AuthAccessToken authAccessToken) {
        when(
                accessTokenVerifier.verify(any(String.class))
        ).thenReturn(
                authAccessToken.memberId()
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

//@TestConfiguration
//class ArgumentResolverTestConfig {
//
//    @Bean
//    AuthAccessArgumentResolver authAccessArgumentResolver() {
//        return Mockito.mock(AuthAccessArgumentResolver.class);
//    }
//
//    @Bean
//    AuthRefreshArgumentResolver authRefreshArgumentResolver() {
//        return Mockito.mock(AuthRefreshArgumentResolver.class);
//    }
//}
