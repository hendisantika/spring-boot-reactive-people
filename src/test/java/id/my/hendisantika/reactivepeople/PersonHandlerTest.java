package id.my.hendisantika.reactivepeople;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/20/24
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
@WebFluxTest
@Import({PersonHandler.class, PersonRouter.class})
@Log4j2
class PersonHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PersonRepository personRepository;
}
