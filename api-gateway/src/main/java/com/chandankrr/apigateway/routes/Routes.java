package com.chandankrr.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user_service")
                .route(RequestPredicates.POST("/auth/signin"),
                        HandlerFunctions.http("http://localhost:8081"))
                .route(RequestPredicates.POST("/auth/signup"),
                        HandlerFunctions.http("http://localhost:8081"))
                .route(RequestPredicates.GET("/api/users/profile"),
                        HandlerFunctions.http("http://localhost:8081"))
                .route(RequestPredicates.path("/api/users"),
                        HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskServiceRoute() {
        return GatewayRouterFunctions.route("task_service")
                .route(RequestPredicates.GET("/api/tasks/{id}"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.POST("/api/tasks"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.GET("/api/tasks/user"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.GET("/api/tasks"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.PUT("/api/tasks/{id}/user/{userid}/assigned"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.PUT("/api/tasks/{id}"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.PUT("/api/tasks/{id}/complete"),
                        HandlerFunctions.http("http://localhost:8082"))
                .route(RequestPredicates.DELETE("/api/tasks/{id}"),
                        HandlerFunctions.http("http://localhost:8082"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> submissionServiceRoute() {
        return GatewayRouterFunctions.route("submission_service")
                .route(RequestPredicates.POST("/api/submissions"),
                        HandlerFunctions.http("http://localhost:8083"))
                .route(RequestPredicates.GET("/api/submissions/{id}"),
                        HandlerFunctions.http("http://localhost:8083"))
                .route(RequestPredicates.GET("/api/submissions"),
                        HandlerFunctions.http("http://localhost:8083"))
                .route(RequestPredicates.GET("/api/submissions/task/{taskId}"),
                        HandlerFunctions.http("http://localhost:8083"))
                .route(RequestPredicates.PUT("/api/submissions/{id}"),
                        HandlerFunctions.http("http://localhost:8083"))
                .build();
    }
}
