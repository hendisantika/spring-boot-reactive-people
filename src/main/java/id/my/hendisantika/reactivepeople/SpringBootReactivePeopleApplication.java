package id.my.hendisantika.reactivepeople;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories
@OpenAPIDefinition(info = @Info(
        title = "Reactive example Webflux, R2DBC, Testcontainers",
        version = "1.0.0",
        description = "Spring WebFlux CRUD Example Sample documents"
))
public class SpringBootReactivePeopleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactivePeopleApplication.class, args);
    }

}
