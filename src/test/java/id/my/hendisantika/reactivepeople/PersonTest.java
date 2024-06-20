package id.my.hendisantika.reactivepeople;

import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
