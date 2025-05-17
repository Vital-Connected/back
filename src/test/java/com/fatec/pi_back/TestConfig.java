package com.fatec.pi_back;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
class TestConfig {

	@PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        System.setProperty("DB_URL", dotenv.get("DB_URL", "jdbc:h2:mem:testdb"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME", "sa"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD", ""));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET", "test-secret"));
    }

}
