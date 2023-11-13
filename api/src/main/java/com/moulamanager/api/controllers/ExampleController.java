package com.moulamanager.api.controllers;

import com.moulamanager.api.models.Hello;
import com.moulamanager.api.services.ExampleService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

@Configuration
@EnableAutoConfiguration
@RestController()
@RequestMapping("hello")
public class ExampleController {
    private final ExampleService exampleService;

    public ExampleController(ExampleService exampleService) {
        this.exampleService = exampleService;
    }

    @GetMapping("/{name}")
    public String getHello(@PathVariable String name) {
        return exampleService.sayHello(name);
    }

    @PostMapping("/")
    public String postHello(@RequestBody Hello hello) {
        return exampleService.sayHello(hello.name);
    }
}
