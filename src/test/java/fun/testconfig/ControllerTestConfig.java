package fun.testconfig;

import fun.domain.auth.config.filter.AuthFilter;
import fun.domain.auth.controller.command.AuthCommandController;
import fun.domain.auth.controller.query.AuthQueryController;
import fun.domain.auth.service.command.AuthCommandService;
import fun.domain.auth.service.query.AuthQueryService;
import jakarta.servlet.ServletException;
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

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@WebMvcTest(controllers = {
        AuthCommandController.class,
        AuthQueryController.class
})
@Import({
        RestDocsConfig.class,
        FilterTestConfig.class
})
public abstract class ControllerTestConfig {

    protected MockMvc mockMvc;

    @Autowired
    protected RestDocumentationResultHandler restDocs;

    @Autowired
    protected RestDocumentationContextProvider restDocumentationContextProvider;


    @MockBean
    protected AuthCommandService authCommandService;

    @MockBean
    protected AuthQueryService authQueryService;

    @BeforeEach
    void controllerTestConfigSetUp(
            final WebApplicationContext webApplicationContext
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .alwaysDo(restDocs)
                .build();
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
class FilterTestConfig {

    @Bean
    AuthFilter authFilter() throws ServletException, IOException {
        final AuthFilter mock = Mockito.mock(AuthFilter.class);
        doNothing().when(mock).doFilter(any(), any(), any());

        return mock;
    }
}
