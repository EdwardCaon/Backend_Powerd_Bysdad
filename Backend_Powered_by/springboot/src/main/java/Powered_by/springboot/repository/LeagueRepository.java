package Powered_by.springboot.repository;

import Powered_by.springboot.entity.League;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    /**
     * Metodo per trovare l id della lega attraverso il nome della lega
     * @param nameLeague nome della lega
     * @return identificativo della lega
     */
    @Query("SELECT idLeague " +
            "FROM League " +
            "WHERE LOWER(nameLeague) = LOWER(:nameLeague)")
    Integer findByNameLeague(@Param("nameLeague") String nameLeague);


}
