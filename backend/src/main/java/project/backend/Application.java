package project.backend;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableJpaRepositories(basePackages = "project.backend.domain.repository")
@EntityScan(basePackages = "project.backend.domain.model")
@ComponentScan(basePackages = "project.backend")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
