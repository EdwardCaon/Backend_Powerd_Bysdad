package Powered_by.springboot.repository;

import Powered_by.springboot.entity.PlayerStats;
import Powered_by.springboot.payload.response.PlayerStatsByIdPlayerResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerStatsRepository  extends JpaRepository<PlayerStats, Integer> {

    @Query("SELECT ps.id.idTeam, ps.id.idPlayer ,ps.points, ps.assist, ps.totalRebounds  " +
            "FROM PlayerStats ps " +
            "WHERE ps.game.idGame = :idGame")
    List<Object[]> getPlayerStatsByIdGame(@Param("idGame") Long idGame);


    @Query("SELECT SUM(ps.points) AS pointsSum, SUM(ps.totalRebounds) AS totalReboundsSum, SUM(ps.assist) AS assistSum, SUM(ps.minutes) AS minutesSum, " +
            "SUM(ps.blocks) AS blocksSum, SUM(ps.steals) AS stealsSum, ps.freeThrowsMade, ps.freeThrowsAttempted, ps.threePointPercentage, ps.fieldGoalsMade " +
            "FROM PlayerStats ps " +
            "JOIN Game g ON g.idGame = ps.game.idGame " +
            "WHERE g.season.idSeason = :idSeason AND ps.player.idPlayer = :idPlayer")
    List<Object[]> getPlayerStatsTeamByIdPlayer(@Param("idPlayer") int idPlayer, @Param("idSeason") int season);


}
