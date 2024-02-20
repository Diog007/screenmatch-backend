package screensync.api.principal;

import org.springframework.beans.factory.annotation.Autowired;
import screensync.api.model.*;
import screensync.api.repository.SerieRepository;
import screensync.api.service.ConsumoApi;
import screensync.api.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    List<DadosSerie> dadosSeries = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();

    private SerieRepository repository;
    public Principal(SerieRepository repository) {
        this.repository = repository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while(opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar Series Buscadas
                    4 - Buscar série por nome
                    5 - Buscar por ator
                    6 - top 5 Séries
                    7 - Buscar séries por categoria
                    8 - Buscar séries pela quatidade de temporada e avaliacao
                    9 - Buscar episodio por trecho
                    0 - Sair                
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5eries();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriePorTemporada();
                    break;
                case 9:
                    buscarEpisodioPorTrecho();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repository.save(serie);
        System.out.println(serie);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var test = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        var json = consumo.obterDados(test);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesBuscadas();
        System.out.println("Escolha uma Serie pelo nome:");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repository.findByTituloContainingIgnoreCase(nomeSerie);

        if(serie.isPresent()) {

            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repository.save(serieEncontrada);
        }else {
            System.out.println("Série não encontrada!");
        }
    }
    private void listarSeriesBuscadas(){
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma Serie pelo nome:");
        var nomeSerie = leitura.nextLine();
        Optional<Serie> serieBuscada = repository.findByTituloContainingIgnoreCase(nomeSerie);
        if(serieBuscada.isPresent()){
            System.out.println("Dados da série: ");
            serieBuscada.stream().forEach(System.out::println);
        }else {
            System.out.println("Série não encontrada!");
        }
    }
    private void buscarSeriesPorAtor(){
        System.out.println("Qual nome do ator para busca: ");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliacoes apartir de qual valor? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repository.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " - avaliacao: " + s.getAvaliacao()));
    }

    private  void buscarTop5eries(){
        List<Serie> serieTop = repository.findTop5ByOrderByAvaliacaoDesc();
        serieTop.forEach(s -> System.out.println(s.getTitulo()+ " - avaliacao: " + s.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar serie de qual categoria/genero");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);
        List<Serie> buscarSerieCategoria = repository.findByGenero(categoria);
        System.out.println("séries da categoria " + nomeGenero);
        buscarSerieCategoria.forEach(System.out::println);
    }

    private void filtrarSeriePorTemporada(){
        System.out.println("Series com até quantas temporadas: ");
        var quantTemporada = leitura.nextInt();
        System.out.println("Apartir de qual avaliacao gostaria as séries? ");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repository.seriesPorTemporadaEAvaliacao(quantTemporada, avaliacao);

        seriesEncontradas.forEach(s ->
                System.out.println("|" + s.getTitulo() + "| Temporadas: " + s.getTotalTemporadas() + "| - avaliacao: " + s.getAvaliacao()));
    }

    private void buscarEpisodioPorTrecho(){
        System.out.println("Qual o nome do episodio para busca? ");
        var trechoEpisodio = leitura.nextLine();
        List<Episodio> episodiosEncontrado = repository.episodiosPorTrecho(trechoEpisodio);
        episodiosEncontrado.forEach(e -> System.out.printf("Série: %s Temporada %s - Episodio %s - %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo()
        ));
    }
}