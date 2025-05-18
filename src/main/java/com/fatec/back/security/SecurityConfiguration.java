package com.fatec.back.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configuração de segurança da aplicação.
 * Esta classe configura os filtros de segurança, controle de sessão, CORS, criptografia de senha e o gerenciamento de autenticação.
 * 
 * <p>
 * A classe {@link SecurityConfiguration} é responsável por configurar as regras de segurança da aplicação, como autenticação,
 * autorização, CORS (Cross-Origin Resource Sharing), controle de sessão e a criptografia das senhas.
 * </p>
 * 
 * <p> Métodos principais:</p>
 * <ul>
 *     <li>{@code corsConfigurationSource()} - Configura as permissões de CORS para a aplicação.</li>
 *     <li>{@code securityFilterChain(HttpSecurity httpSecurity)} - Configura os filtros de segurança, desabilita o CSRF e define a política de criação de sessão.</li>
 *     <li>{@code authenticationManager(AuthenticationConfiguration authenticationConfiguration)} - Cria e retorna o {@link AuthenticationManager} necessário para autenticação.</li>
 *     <li>{@code passwordEncoder()} - Retorna o {@link PasswordEncoder} utilizado para criptografar as senhas, usando o algoritmo {@link BCryptPasswordEncoder}.</li>
 * </ul>
 * 
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    SecurityFilter securityFilter;

    /**
     * Configura as permissões de CORS para a aplicação.
     * 
     * @return A configuração de CORS.
     */
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT","PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Configura os filtros de segurança, desabilita o CSRF e define a política de criação de sessão.
     * 
     * @param httpSecurity A instância de {@link HttpSecurity} usada para configurar a segurança.
     * @return O {@link SecurityFilterChain} configurado.
     * @throws Exception Se ocorrer um erro ao configurar a segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .anyRequest().permitAll()
                    // .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    // .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                    // .requestMatchers(HttpMethod.POST, "/user/**").hasRole("ADMIN")
                    // .anyRequest().authenticated()
                    )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    /**
     * Cria e retorna o {@link AuthenticationManager} necessário para autenticação.
     * 
     * @param authenticationConfiguration A configuração de autenticação fornecida pelo Spring Security.
     * @return O {@link AuthenticationManager} configurado.
     * @throws Exception Se ocorrer um erro ao criar o {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Retorna o {@link PasswordEncoder} utilizado para criptografar as senhas.
     * 
     * @return O {@link PasswordEncoder} configurado, utilizando o algoritmo {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
