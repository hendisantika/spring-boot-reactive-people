package id.my.hendisantika.reactivepeople;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/3/24
 * Time: 09:58
 * To change this template use File | Settings | File Templates.
 */

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.net.URI;

import static id.my.hendisantika.reactivepeople.Constants.API;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.created;

/**
 * This class is responsible for handling HTTP requests related to the Person entity.
 * It includes methods for CRUD operations and finding a person by name.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PersonHandler {
    private final PersonRepository personRepository;
    private final Validator validator;

    /**
     * Handles a request to get all persons.
     *
     * @param serverRequest The incoming server request.
     * @return A ServerResponse with the list of all persons.
     */
    public Mono<ServerResponse> handleFindAll(ServerRequest serverRequest) {
        return ok()
                .body(this.personRepository.findAll(), Person.class);
    }

    /**
     * Handles a request to get a person by id.
     *
     * @param serverRequest The incoming server request.
     * @return A ServerResponse with the person found or a 404 status if not found.
     */
    public Mono<ServerResponse> handleFindById(ServerRequest serverRequest) {
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        return this.personRepository.findById(id)
                .flatMap(person -> ok()
                        .bodyValue(person))
                .switchIfEmpty(notFound().build());
    }

    /**
     * Handles a request to get the first person found by name.
     *
     * @param serverRequest The incoming server request.
     * @return A ServerResponse with the person found or a 404 status if not found.
     */
    public Mono<ServerResponse> handleFindFirstByName(ServerRequest serverRequest) {
        log.info("Handle request {} {}", serverRequest.method(), serverRequest.path());
        var name = serverRequest.pathVariable("name");
        Mono<Person> firstByName = this.personRepository.findFirstByName(name);
        Mono<ServerResponse> notFound = notFound().build();
        return firstByName.flatMap(person -> ok()
                        .body(fromValue(person)))
                .switchIfEmpty(notFound);
    }

    /**
     * Handles a request to delete a person by id.
     *
     * @param serverRequest The incoming server request.
     * @return A ServerResponse with a success message or a 404 status if not found.
     */
    public Mono<ServerResponse> handleDeleteById(ServerRequest serverRequest) {
        var id = Long.parseLong(serverRequest.pathVariable("id"));
        return this.personRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "person not found")))
                .flatMap(this.personRepository::delete)
                .thenReturn("successfully deleted!")
                .flatMap(msg -> ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(msg));
    }

    /**
     * Handles a request to create a new person.
     *
     * @param serverRequest The incoming server request.
     * @return A ServerResponse with the created person or a 400 status if the request body is invalid.
     */
    public Mono<ServerResponse> handleCreate(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Person.class)
                .switchIfEmpty(Mono.error(new ServerWebInputException("person must not be null")))
                .doOnNext(this::validate)
                .flatMap(this.personRepository::save)
                .flatMap(person ->
                        created(URI.create(API + "/" + person.getId()))
                                .bodyValue(person));
    }
}
