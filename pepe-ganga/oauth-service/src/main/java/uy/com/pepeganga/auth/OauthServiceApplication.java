package uy.com.pepeganga.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OauthServiceApplication implements CommandLineRunner {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(OauthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String pass = "12345";

		for (int i = 0; i < 4; i++) {
			System.out.println(bCryptPasswordEncoder.encode(pass));
		}
	}
}
