package id.my.hendisantika.reactivepeople;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

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

}
