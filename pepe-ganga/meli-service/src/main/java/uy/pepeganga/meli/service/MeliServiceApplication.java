package uy.pepeganga.meli.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MeliServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeliServiceApplication.class, args);
    }

}
