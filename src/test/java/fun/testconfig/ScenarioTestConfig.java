package fun.testconfig;

import fun.domain.member.domain.MemberCommandRepository;
import fun.domain.vote.post.domain.VoteTagCommandRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class ScenarioTestConfig {

    @Autowired
    protected MemberCommandRepository memberCommandRepository;

    @Autowired
    protected VoteTagCommandRepository voteTagCommandRepository;

    @BeforeEach
    void scenarioTestSetUp(
            @LocalServerPort int port
    ) {
        RestAssured.port = port;
    }
}
