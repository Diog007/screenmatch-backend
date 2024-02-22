package screensync.api.dto;

import screensync.api.model.Categoria;
import screensync.api.model.Serie;

public record SerieDTO(
                        Long id,
                        String titulo,
                        Integer totalTemporadas,
                        Double avaliacao,
                        Categoria genero,
                        String atores,
                        String poster,
                        String sinopse) {

}
