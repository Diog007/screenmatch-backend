package screensync.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import screensync.api.model.Serie;

import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
}
