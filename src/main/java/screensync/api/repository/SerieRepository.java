package screensync.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import screensync.api.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
