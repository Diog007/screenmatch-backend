package screensync.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import screensync.api.dto.EpisodioDTO;
import screensync.api.dto.SerieDTO;
import screensync.api.model.Categoria;
import screensync.api.model.Serie;
import screensync.api.repository.SerieRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class SerieService {

    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obterTodasAsSeries(){
        return converteDados(repository.findAll());
    }
    public List<SerieDTO> obtertop5Series() {
        return converteDados(repository.findTop5ByOrderByAvaliacaoDesc());
    }
    public List<SerieDTO> obterLancamento() {
        return converteDados(repository.lancamentosMaisRecentes());
    }
    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(),
                    s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse());
        }
        return null;
    }
    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterTemporadasPorNumero(Long id, Long numero) {
        var episodios = repository.obterEpisodiosPorTemporada(id, numero);
        return episodios.stream()
                .map(EpisodioDTO::new)
                .collect(Collectors.toList());
    }

    private List<SerieDTO> converteDados(List<Serie> series){
        return series.stream()
                .map(s -> new SerieDTO(s.getId(), s.getTitulo(), s.getTotalTemporadas(), s.getAvaliacao(),
                        s.getGenero(), s.getAtores(), s.getPoster(), s.getSinopse())).collect(Collectors.toList());
    }
}