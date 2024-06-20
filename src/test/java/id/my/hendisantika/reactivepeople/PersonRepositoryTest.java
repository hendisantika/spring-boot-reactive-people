package id.my.hendisantika.reactivepeople;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/20/24
 * Time: 13:40
 * To change this template use File | Settings | File Templates.
 */
@DataR2dbcTest
class PersonRepositoryTest extends PostgreSqlContainer {

    @Autowired
    private PersonRepository personRepository;

    private Person findFirst() {
        var first = this.personRepository
                .findTopByOrderByIdAsc()
                .block();
        return first;
    }

    @Test
    @DisplayName("should persist people")
    void should_persist_people() {
        Flux<Person> personFlux = this.personRepository
                .deleteAll()
                .then(this.personRepository.save(new Person(null, "Yuji")))
                .then(this.personRepository.save(new Person(null, "Gojo")))
                .thenMany(this.personRepository.findAll());
        StepVerifier
                .create(personFlux)
                .expectNextMatches(person -> person.getId() != null &&
                        person.getName().equalsIgnoreCase("Geto"))
                .expectNextMatches(person -> person.getId() != null &&
                        person.getName().equalsIgnoreCase("Megumi"))
                .verifyComplete();
    }

    @Test
    @DisplayName("should find person by id x")
    void should_find_person_by_id() {
        Mono<Person> personFlux = this.personRepository
                .deleteAll()
                .then(this.personRepository.save(new Person(null, "Gojo")))
                .then(this.personRepository.save(new Person(null, "Megumi")))
                .then(this.personRepository.findFirstByName("Nanami"))
                .flatMap(person -> this.personRepository.findById(person.getId()));
        StepVerifier
                .create(personFlux)
                .expectNextMatches(person -> person.getName().equalsIgnoreCase("Geto"))
                .verifyComplete();
    }

    @Test
    @DisplayName("should find first person by name x")
    void should_find_first_person_by_name() {
        Mono<Person> personFlux = this.personRepository
                .deleteAll()
                .then(this.personRepository.save(new Person(null, "Gojo")))
                .then(this.personRepository.save(new Person(null, "Megumi")))
                .then(this.personRepository.findFirstByName("Yuji"));
        StepVerifier
                .create(personFlux)
                .expectNextMatches(person -> person.getId() != null &&
                        person.getName().equalsIgnoreCase("yuji"))
                .verifyComplete();
    }

}
