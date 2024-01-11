package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Game;
import Powered_by.springboot.entity.TeamGameStats;
import Powered_by.springboot.payload.response.GameDayResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT " +
            "g.idGame, g.start, " +
            "t.nameTeam, t.logo, t.colour, s.p1, s.p2, s.p3, s.p4, s.p5, " +
            "t_e.nameTeam, t_e.logo, t_e.colour ,s_e.p1, s_e.p2, s_e.p3, s_e.p4, s_e.p5 " +
            "FROM Game g " +
            "JOIN g.home t " +
            "JOIN g.visitors t_e " +
            "JOIN Score s ON s.team.idTeam = t.idTeam " +
            "JOIN Score s_e ON s_e.team.idTeam = t_e.idTeam " +
            "WHERE g.start BETWEEN :startOfDay AND :endOfDay " +
            "GROUP BY t.nameTeam")
    List<Object[]> getGamesForDayRange(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);
}
