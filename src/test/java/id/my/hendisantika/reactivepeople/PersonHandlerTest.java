package id.my.hendisantika.reactivepeople;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static id.my.hendisantika.reactivepeople.Constants.API;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("should handle request find all")
    void should_handle_find_all() {
        when(this.personRepository.findAll())
                .thenReturn(Flux.just(
                        new Person(1L, "Yuji"),
                        new Person(2L, "Megumi"),
                        new Person(3L, "Gojo")
                ));
        this.webTestClient
                .get()
                .uri(API)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Person.class)
                .hasSize(3)
                .contains(
                        new Person(1L, "Yuji"),
                        new Person(2L, "Megumi"),
                        new Person(3L, "Gojo")
                );
    }
}
