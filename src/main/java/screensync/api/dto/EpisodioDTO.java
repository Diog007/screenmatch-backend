package screensync.api.dto;

import screensync.api.model.Episodio;

public record EpisodioDTO(Integer temporada, Integer numeroEpisodio, String titulo) {
    public EpisodioDTO(Episodio episodio) {
        this(episodio.getTemporada(),
                episodio.getNumeroEpisodio(),
                episodio.getTitulo());
    }
}
