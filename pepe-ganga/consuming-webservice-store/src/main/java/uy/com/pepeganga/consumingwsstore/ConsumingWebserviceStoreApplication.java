package uy.com.pepeganga.consumingwsstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EntityScan({"uy.com.pepeganga.business.common.entities", "uy.com.pepeganga.consumingwsstore.entities"})
public class ConsumingWebserviceStoreApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebserviceStoreApplication.class, args);
	}

}
