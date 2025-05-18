package com.fatec.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Classe principal da aplicação Spring Boot.
 *
 * <p>
 * Essa classe inicia a aplicação e carrega variáveis de ambiente a partir de um arquivo `.env`
 * usando a biblioteca {@code dotenv-java}. As variáveis carregadas são utilizadas para configurar
 * o acesso ao banco de dados e à chave secreta do JWT.
 * </p>
 *
 * <p>As variáveis carregadas são:</p>
 * <ul>
 *   <li><b>DB_URL</b>: URL de conexão com o banco de dados</li>
 *   <li><b>DB_USERNAME</b>: Nome de usuário do banco</li>
 *   <li><b>DB_PASSWORD</b>: Senha do banco</li>
 *   <li><b>JWT_SECRET</b>: Chave secreta usada para assinar tokens JWT</li>
 * </ul>
 *
 * <p>
 * Após carregar as variáveis, a aplicação é iniciada via {@code SpringApplication.run()}.
 * </p>
 */
@SpringBootApplication
public class PiBackApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
		SpringApplication.run(PiBackApplication.class, args);
	}

}