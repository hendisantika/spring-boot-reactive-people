package id.my.hendisantika.reactivepeople;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

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

}
