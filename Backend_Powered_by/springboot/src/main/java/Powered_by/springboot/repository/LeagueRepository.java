package Powered_by.springboot.repository;

import Powered_by.springboot.entity.League;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    @Query("SELECT idLeague " +
            "FROM League " +
            "WHERE LOWER(nameLeague) = LOWER(:idLeague)")
    Integer findByNameLeague(@Param("idLeague") String idLeague);


}
