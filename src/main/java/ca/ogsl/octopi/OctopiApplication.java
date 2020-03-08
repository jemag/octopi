package ca.ogsl.octopi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OctopiApplication {
  public static void main(String[] args) {
    SpringApplication.run(OctopiApplication.class, args);
  }
}
