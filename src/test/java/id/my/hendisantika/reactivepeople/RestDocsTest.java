package id.my.hendisantika.reactivepeople;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/20/24
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestReactivePeoplePostgresqlApplication.class)
public class RestDocsTest extends PostgreSqlContainer {

    @Autowired
    private PersonRepository personRepository;

    private WebTestClient webTestClient;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension();

    private Person fetchFirst() {
        var person = this.personRepository
                .findTopByOrderByIdAsc()
                .block();
        return person;
    }
}
