package screensync.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import screensync.api.principal.Principal;

@SpringBootApplication
public class ScreensyncApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreensyncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
