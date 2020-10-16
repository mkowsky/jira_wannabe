package pl.mkowsky.jirawannabedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JiraWannabeDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiraWannabeDemoApplication.class, args);
        System.out.println("Hello from Backend");
    }

}
