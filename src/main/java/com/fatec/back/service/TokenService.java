package com.fatec.back.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fatec.back.domain.User.User;

/**
 * Serviço responsável pela geração e validação de tokens JWT (JSON Web Token).
 * Este serviço utiliza a biblioteca Auth0 para criar e verificar tokens de autenticação.
 * 
 * O token gerado contém as informações do usuário e é utilizado para autenticar e autorizar o acesso a recursos da aplicação.
 * O token tem um tempo de expiração de 2 horas.
 * 
 * <p>Métodos principais:</p>
 * <ul>
 *     <li>{@code generateToken(User user)} - Gera um token JWT para um usuário.</li>
 *     <li>{@code validateToken(String token)} - Valida o token JWT e retorna o e-mail do usuário associado.</li>
 * </ul>
 * 
 * @see User
 * @see JWT
 * @see Algorithm
 */
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário fornecido.
     * O token contém informações do usuário, como ID e e-mail, e tem um tempo de expiração de 2 horas.
     * 
     * @param user O usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     * @throws RuntimeException Se ocorrer um erro durante a geração do token.
     */
    public String generateToken(User user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            String token = JWT.create()
                                .withIssuer("auth-api")
                                .withSubject(user.getEmail())
                                .withPayload(claims)
                                .withExpiresAt(generateExpirationDate())
                                .sign(algorithm);
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Error while generatin token", e);
        }
    }

     /**
     * Valida o token JWT fornecido e retorna o e-mail do usuário associado.
     * Se o token for inválido, retorna uma string vazia.
     * 
     * @param token O token JWT a ser validado.
     * @return O e-mail do usuário associado ao token, ou uma string vazia se o token for inválido.
     */
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                        .withIssuer("auth-api")
                        .build()
                        .verify(token)
                        .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    /**
     * Gera a data de expiração do token, que é definida para 2 horas após a geração do token.
     * 
     * @return A data e hora de expiração do token.
     */
    private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
