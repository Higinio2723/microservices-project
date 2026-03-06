package com.gonzalandero.gateway.filters;

import com.gonzalandero.gateway.dtos.TokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GatewayFilter {

    private final WebClient webClient;

    private final String authServiceUrl;
    private final String accessTokenHeaderName;

    private static final String DEFAULT_AUTH_SERVICE_URL = "http://localhost:3030/auth-server/auth/jwt";
    private static final String DEFAULT_ACCESS_TOKEN_HEADER_NAME = "accessToken";


    public AuthFilter() {
        this.webClient = WebClient.builder().build();
        this.authServiceUrl = DEFAULT_AUTH_SERVICE_URL;
        this.accessTokenHeaderName = DEFAULT_ACCESS_TOKEN_HEADER_NAME;
    }

    // Constructor adicional para facilitar testing e inyección manual
    public AuthFilter(WebClient webClient, String authServiceUrl, String accessTokenHeaderName) {
        this.webClient = webClient;
        this.authServiceUrl = authServiceUrl;
        this.accessTokenHeaderName = accessTokenHeaderName;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
           return this.onError(exchange);
        }

        final var tokenHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (tokenHeader == null || tokenHeader.isBlank()) {
            return this.onError(exchange);
        }

        final var chunked = tokenHeader.split("\\s+");

        // Validamos prefijo Bearer y que tenga dos partes
        if (chunked.length != 2 || !chunked[0].equalsIgnoreCase("Bearer")) {
            return this.onError(exchange);
        }
        final var token = chunked[1];

        return this.webClient
                .post()
                .uri(this.authServiceUrl)
                .header(this.accessTokenHeaderName, token)
                .retrieve()
                .bodyToMono(TokenDto.class)
                // Si la validación remota es exitosa continuamos con la cadena
                .flatMap(response -> chain.filter(exchange))
                // En caso de error al validar el token devolvemos BAD_REQUEST
                .onErrorResume(err -> this.onError(exchange));
    }

    private Mono<Void> onError(ServerWebExchange exchange){
        final var response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        return response.setComplete();
    }
}
