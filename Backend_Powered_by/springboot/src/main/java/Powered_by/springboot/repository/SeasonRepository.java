package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Season;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SeasonRepository extends  JpaRepository<Season, Long> {

    @Query("SELECT idSeason " +
            "FROM Season " +
            "WHERE  season = :season")
    Integer findByName(@Param("season") int season);
}
