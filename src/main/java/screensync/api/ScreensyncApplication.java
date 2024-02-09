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

//		List<DadosTemporada> temporadas = new ArrayList<>();
//
//		for (int i =1; i<=dados.totalTemporadas(); i++){
//			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=" + i + "&apikey=6585022c");
//			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
//			temporadas.add(dadosTemporada);
//		}
//		temporadas.forEach(System.out::println);
	}
}
