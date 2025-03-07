package mk.ukim.finki.homework5orderservice;

import mk.ukim.finki.homework5orderservice.config.RestTemplateConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
@Import(RestTemplateConfig.class)
public class Homework5OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(Homework5OrderServiceApplication.class, args);
    }

}
