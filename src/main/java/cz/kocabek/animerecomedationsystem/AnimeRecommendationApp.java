package cz.kocabek.animerecomedationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class AnimeRecommendationApp {

    @RequestMapping("/")
    String home() {
        return "Hello World! Docker";
    }

    public static void main(String[] args) {
        SpringApplication.run(AnimeRecommendationApp.class, args);
    }

}
