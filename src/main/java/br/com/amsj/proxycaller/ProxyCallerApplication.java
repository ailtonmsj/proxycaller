package br.com.amsj.proxycaller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
public class ProxyCallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyCallerApplication.class, args);
	}

}
