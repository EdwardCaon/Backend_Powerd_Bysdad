package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Season;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SeasonRepository extends  JpaRepository<Season, Long> {
    /**
     * Metodo per trovare identificativo di una stagione attraverso il nome/anno
     * @param season anno/nome della stagione
     * @return identificativo della stagione
     */
    @Query("SELECT idSeason " +
            "FROM Season " +
            "WHERE  season = :season")
    Integer findByName(@Param("season") int season);
}
