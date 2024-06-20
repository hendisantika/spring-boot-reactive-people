package id.my.hendisantika.reactivepeople;

import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/20/24
 * Time: 13:50
 * To change this template use File | Settings | File Templates.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PersonTest {
    @Autowired
    private Validator validator;

    @Test
    void should_create_person() {
        Person person = new Person(1L, "Gojo");
        assertThat(person.getId()).isEqualTo(1L);
        assertThat(person.getName()).isEqualTo("Gojo");
    }

    @ParameterizedTest
    @ValueSource(strings = {"N", "Name", "0123456789"})
    void should_create_valid_person(String name) {
        Person person = new Person(name);
        var violations = this.validator.validate(person);
        assertThat(violations).isEmpty();
    }
}
