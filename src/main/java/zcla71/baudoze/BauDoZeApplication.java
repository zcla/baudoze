package zcla71.baudoze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(BauDoZeProperties.class)
@ComponentScan(basePackages = { "zcla71" })
public class BauDoZeApplication {
	public static void main(String[] args) {
		SpringApplication.run(BauDoZeApplication.class, args);
	}
}
