package id.my.hendisantika.reactivepeople;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/20/24
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebIntegrationTest extends PostgreSqlContainer {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    PersonRepository personRepository;
}
