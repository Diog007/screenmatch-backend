package screensync.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import screensync.api.model.DadosSerie;
import screensync.api.service.ConsumoApi;
import screensync.api.service.ConverteDados;

@SpringBootApplication
public class ScreensyncApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreensyncApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dados);
	}
}
