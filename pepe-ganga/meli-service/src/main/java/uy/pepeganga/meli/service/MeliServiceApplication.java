package uy.pepeganga.meli.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"uy.com.pepeganga.business.common.entities"})
public class MeliServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeliServiceApplication.class, args);
    }

}
