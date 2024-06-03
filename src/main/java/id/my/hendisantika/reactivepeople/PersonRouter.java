package id.my.hendisantika.reactivepeople;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-reactive-people
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/3/24
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static id.my.hendisantika.reactivepeople.Constants.API;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * This class is responsible for routing HTTP requests to the appropriate handler methods.
 * It uses Spring's functional routing style with RouterFunctions and RequestPredicates.
 */
@Configuration
public class PersonRouter {
    RouterFunction<ServerResponse> http(PersonHandler personHandler) {
        return nest(path(API),
                route(GET(""), personHandler::handleFindAll)
                        .andRoute(GET("/{id}"), personHandler::handleFindById)
                        .andRoute(GET("/firstByName/{name}"), personHandler::handleFindFirstByName)
                        .andRoute(DELETE("/{id}"), personHandler::handleDeleteById)
                        .andRoute(POST(""), personHandler::handleCreate)
                        .andRoute(PUT("/{id}"), personHandler::handleUpdate)
        );
    }
}
