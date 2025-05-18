package com.fatec.back.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fatec.back.repository.UserRepository;
import com.fatec.back.service.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Filtro de segurança que intercepta cada requisição HTTP para verificar e autenticar o token JWT.
 * 
 * <p>
 * Esta classe estende {@link OncePerRequestFilter}, garantindo que o filtro seja executado uma única vez por requisição.
 * Ela valida o token JWT presente no cabeçalho `Authorization`, autentica o usuário e atualiza o {@link SecurityContextHolder}.
 * </p>
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    /**
     * Intercepta a requisição e verifica se há um token JWT válido.
     * Se o token for válido, autentica o usuário e atualiza o contexto de segurança.
     *
     * @param request requisição HTTP
     * @param response resposta HTTP
     * @param filterChain cadeia de filtros
     * @throws ServletException se ocorrer um erro de servlet
     * @throws IOException se ocorrer um erro de I/O
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException {
        String path = request.getRequestURI();
        if (path.equals("/auth/login") || path.equals("/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            UserDetails user = repository.findByEmail(login);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho `Authorization` da requisição.
     * 
     * @param request a requisição HTTP
     * @return o token sem o prefixo "Bearer ", ou {@code null} se não existir
     */
    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");

    }
}
