package id.my.hendisantika.reactivepeople;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static id.my.hendisantika.reactivepeople.Constants.API;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    @DisplayName("should handle request find by id x")
    void should_handle_find_by_id() {
        when(this.personRepository.findById(1L))
                .thenReturn(Mono.just(new Person(1L, "Gojo")));
        this.webTestClient
                .get()
                .uri(API + "/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Person.class)
                .consumeWith(response -> {
                    assertThat(response.getResponseBody()).isNotNull();
                    assertThat(response.getResponseBody().getName()).isEqualTo("Gojo");
                    assertThat(response.getResponseBody().getId()).isEqualTo(1L);
                });
    }

    @Test
    @DisplayName("should handle request find by unknown id x")
    void should_handle_find_by_unknown_id() {
        when(this.personRepository.findById(1000L))
                .thenReturn(Mono.empty());
        this.webTestClient
                .get()
                .uri(API + "/1000")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    @DisplayName("should handle request delete by id x")
    void should_handle_delete_by_id() {
        Person person = new Person(1L, "Gojo");
        when(this.personRepository.findById(any(Long.class)))
                .thenReturn(Mono.just(person));
        when(this.personRepository.delete(any(Person.class)))
                .thenReturn(Mono.empty());
        this.webTestClient
                .delete()
                .uri(API + "/1")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(String.class)
                .isEqualTo("successfully deleted!");
    }
}
