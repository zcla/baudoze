package zcla71.baudoze;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static String format(LocalDate localDate) {
		return localDate.format(DATE_FORMATTER);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
