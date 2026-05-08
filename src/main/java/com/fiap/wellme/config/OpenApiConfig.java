package com.fiap.wellme.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String BEARER_SCHEME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WellMe – CarePlus API")
                        .description("""
                                API REST para o aplicativo mobile gamificado de saúde WellMe (CarePlus).
                                Gerencia usuários, trilhas de aprendizagem, quizzes, progresso, hidratação e badges.
                                
                                GETs são públicos. Operações de escrita exigem JWT.
                                Login: POST /auth/login  |  usuário: admin  |  senha: 123456
                                Ou cadastre um novo usuário em POST /api/v1/users/register (público).
                                
                                FIAP 3ESPR – Sprint 3 SOA & WebServices 2026
                                """)
                        .version("1.0"))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Token JWT obtido em POST /auth/login")));
    }
}
