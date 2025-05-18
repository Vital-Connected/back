package com.fatec.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fatec.back.repository.UserRepository;

/**
 * Serviço responsável pela autenticação de usuários.
 * Esta classe implementa o {@link UserDetailsService} para carregar as informações do usuário com base no e-mail.
 * 
 * A principal função deste serviço é buscar um usuário no banco de dados usando o e-mail e retornar os detalhes do usuário
 * para o processo de autenticação no Spring Security.
 * 
 * <p>Método principal:</p>
 * <ul>
 *     <li>{@code loadUserByUsername(String email)} - Carrega um usuário com base no e-mail fornecido.</li>
 * </ul>
 * 
 * @see UserRepository
 * @see UserDetails
 */
@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    /**
     * Carrega os detalhes do usuário com base no e-mail fornecido.
     * Este método é utilizado pelo Spring Security para autenticar e autorizar o usuário.
     * 
     * @param email O e-mail do usuário que está tentando se autenticar.
     * @return Os detalhes do usuário, encapsulados em um objeto {@link UserDetails}.
     * @throws UsernameNotFoundException Se o usuário com o e-mail fornecido não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }
}