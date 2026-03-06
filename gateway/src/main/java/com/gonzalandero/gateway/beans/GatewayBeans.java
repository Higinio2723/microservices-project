package com.gonzalandero.gateway.beans;

import com.gonzalandero.gateway.filters.AuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;


@Configuration
@AllArgsConstructor
public class GatewayBeans {

    private final AuthFilter authFilter;


    @Bean
    @Profile(value="eureka-on")
    public RouteLocator customRouteLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(router -> router
                        .path("/employees/**")
                        .uri("lb://msvc-employee"))
                .build();
    }

    @Bean
    @Profile(value="oauth2")
    public RouteLocator customRouteLocatorOauth2(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(router -> router
                        .path("/companies-crud/company/**")
                        .filters(filters -> {
                            filters.circuitBreaker(
                                    config -> config
                                            .setName("gateway-cb")
                                            .setStatusCodes(Set.of("500","400"))
                                            .setFallbackUri("forward:/companies-crud-fallback/company/**"));
                        //    filters.filter(this.authFilter);
                            return filters;
                        })
                        .uri("lb://companies-crud"))
                .route(router -> router
                        .path("/report-ms/report/**")
                      //  .filters(filter -> filter.filter(this.authFilter))
                        .uri("lb://report-ms"))
                .route(router -> router
                        .path("/companies-crud-fallback/company/**")
                        .uri("lb://companies-crud-fallback"))

                .build();
    }
}
