package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    private final String welcome;

    public WelcomeController(@Value("${welcome.message}")String welcome) {
        this.welcome = welcome;
    }

    @GetMapping("/")
    public String sayHello() {
        return this.welcome;
    }
}
