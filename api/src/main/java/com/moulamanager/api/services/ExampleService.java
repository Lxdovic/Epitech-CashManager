package com.moulamanager.api.services;

import org.springframework.stereotype.Repository;

@Repository
public class ExampleService {
    public String sayHello(String name) {
        String hello = "Hello " + name + "!";

        System.out.println(hello);

        return hello;
    }
}
