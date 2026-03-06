package com.gonzalandero.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Agregar headers ANTES de procesar la cadena de filtros
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
            @Override
            public reactor.core.publisher.Mono<Void> writeWith(org.reactivestreams.Publisher<? extends org.springframework.core.io.buffer.DataBuffer> body) {
                // Agregar Referrer-Policy header para React
                getHeaders().add("Referrer-Policy", "strict-origin-when-cross-origin");

                // Headers de seguridad adicionales recomendados
                getHeaders().add("X-Content-Type-Options", "nosniff");
                getHeaders().add("X-Frame-Options", "DENY");
                getHeaders().add("X-XSS-Protection", "1; mode=block");

                return super.writeWith(body);
            }
        };

        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        // Ejecutar con alta prioridad
        return -3;
    }
}
