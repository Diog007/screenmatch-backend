package screensync.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import screensync.api.principal.Principal;
import screensync.api.repository.SerieRepository;

@SpringBootApplication
public class ScreensyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreensyncApplication.class, args);
	}
}
