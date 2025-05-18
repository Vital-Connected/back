package com.fatec.back.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.back.domain.Login.LoginDTO;
import com.fatec.back.domain.Login.LoginResponseDTO;
import com.fatec.back.domain.Role.Role;
import com.fatec.back.domain.User.User;
import com.fatec.back.domain.User.UserRequestDTO;
import com.fatec.back.repository.RoleRepository;
import com.fatec.back.repository.UserRepository;
import com.fatec.back.service.TokenService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador responsável pela autenticação de usuários no sistema.
 * Este controlador fornece endpoints para login e registro de usuários.
 */
    @RestController
    @RequestMapping("auth")
    public class AuthenticatonController {
        @Autowired
        private UserRepository repository;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private TokenService tokenService;

        @Autowired
        private AuthenticationManager authenticationManager;

        /**
         * Endpoint para login do usuário.
         * 
         * @param data Dados de login enviados pelo usuário, contendo email e senha.
         * @return Retorna um objeto LoginResponseDTO contendo o token de autenticação.
         * @see LoginDTO
         */
        @PostMapping("/login")
        public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO data) {
            var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());            
            var auth = this.authenticationManager.authenticate(userPassword);
            System.out.println(auth);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));

        }

        /**
         * Endpoint para registro de um novo usuário no sistema.
         * 
         * @param data Dados de cadastro do usuário, contendo informações como
         *             email, nome, senha e role.
         * @return Retorna uma mensagem de sucesso ou erro, dependendo da operação.
         * @throws RuntimeException Se a role não for encontrada no banco de dados.
         */
        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody @Valid UserRequestDTO data) {
            if (this.repository.findByEmail(data.email()) != null) {
                return ResponseEntity.badRequest().body("Email já cadastrado.");
            }

            // Criptografar a senha
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

            // Buscar a role pelo ID
            Role role = roleRepository.findById(data.role())
                .orElseThrow(() -> new RuntimeException("Role not found"));

            // Criar novo usuário
            User newUser = new User();
            newUser.setEmail(data.email());
            newUser.setPassword(encryptedPassword);
            newUser.setName(data.name());
            newUser.setRole(role);

            // Buscar o usuário que criou este, se fornecido
            if (data.userId() != null) {
                repository.findById(data.userId()).ifPresent(user -> {
                    newUser.setCreatedBy(user);
                    newUser.setUpdatedBy(user);
                });
            }

            // Salvar no banco
            repository.save(newUser);
            return ResponseEntity.ok("Usuário registrado com sucesso.");
        }


    }
