package pl.mkowsky.jirawannabedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@Configuration
public class JiraWannabeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiraWannabeDemoApplication.class, args);
        System.out.println("Hello from Backend");
    }

}
