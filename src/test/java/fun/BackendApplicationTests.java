package fun;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootTest
@EnableJpaRepositories("fun.*")
@EntityScan("fun.*")
class BackendApplicationTests {

    @Test
    void contextLoads() {
    }

}
