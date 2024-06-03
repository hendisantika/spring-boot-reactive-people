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
import org.springframework.stereotype.Component;

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

}
