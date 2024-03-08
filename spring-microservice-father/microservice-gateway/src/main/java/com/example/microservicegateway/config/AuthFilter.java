package com.example.microservicegateway.config;


import com.example.microservicegateway.dto.UserResponseDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClient;


    public AuthFilter(WebClient.Builder webClient) {
        super(Config.class);
        this.webClient = webClient;
    }


    private List<String> permittedEndpoints = List.of(
            "/api/v1/security/register",
            "/api/v1/security/login"
    );

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            try{
                String jwtToken = extractToken(exchange.getRequest());


                if (checkEndpoint(exchange.getRequest())) {
                    return chain.filter(exchange);
                }


                if (extractToken(exchange.getRequest()) == null) {
                    throw new RuntimeException("Wrong information");
                }


                return webClient.build()
                        .post()
                        .uri("lb://msvc-user/api/v1/security/validate?token=" + jwtToken.substring(7))
                        .retrieve()
                        .bodyToMono(UserResponseDto.class)
                        .map(dto -> {
                            exchange.getRequest()
                                    .mutate()
                                    .header("auth-header-id", String.valueOf(dto.id()))
                                    .header("user-email", dto.email());
                            return exchange;
                        }).flatMap(chain::filter);
            }
            catch (Exception e){
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
            }


        });
    }


    public static class Config {

    }

    private String extractToken(ServerHttpRequest request) {
        return request.getHeaders().getFirst("Authorization");
    }

    private boolean checkEndpoint(ServerHttpRequest request) {
        return permittedEndpoints.stream()
                .anyMatch(request.getPath().toString()::startsWith);
    }
}
