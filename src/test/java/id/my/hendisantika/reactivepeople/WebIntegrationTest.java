package id.my.hendisantika.reactivepeople;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static id.my.hendisantika.reactivepeople.Constants.API;

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

    @BeforeEach
    public void setUp() {
        log.info("Running setUp -> creatíng 100 people");
        ArrayList<Person> people = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            people.add(new Person("Person@" + i));
        }
        Flux<Person> personFlux = personRepository
                .deleteAll()
                .thenMany(personRepository.saveAll(people));
        StepVerifier
                .create(personFlux)
                .expectNextCount(100L)
                .verifyComplete();
    }

    Person fetchFirstPerson() {
        var person = this.personRepository
                .findTopByOrderByIdAsc()
                .block();
        log.info("First Person found: {}", person);
        return person;
    }

    @ParameterizedTest
    @ValueSource(strings = {"N", "Name", "0123456789"})
    void handleUpdateValid(String name) {
        Person first = this.fetchFirstPerson();
        var id = first.getId();
        this.webTestClient
                .put()
                .uri(API + "/" + id)
                .bodyValue(new Person(id, name))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"00123456789"})
    @NullAndEmptySource
    void handleUpdateInvalid(String name) {
        Person first = this.fetchFirstPerson();
        var id = first.getId();
        this.webTestClient
                .put()
                .uri(API + "/" + id)
                .bodyValue(new Person(id, name))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void handleUpdateNotFound() {
        this.webTestClient
                .put()
                .uri(API + "/10000000")
                .bodyValue(new Person(10000000L, "Update"))
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
