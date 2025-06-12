package lv.klix.oas;

import org.springframework.boot.SpringApplication;

// Testcontainers Support for Local Development
class LocalDevApplication {
    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(LocalDevTestcontainersConfig.class)
                .run(args);
    }
}
