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
    /**
     * Metodo per trovare le statistiche dei player in un game specifico
     * @param idGame id del game per cercare le statistiche
     * @return lista di player con le relative stats
     */
    @Query("SELECT ps.id.idTeam, ps.id.idPlayer ,ps.points, ps.assist, ps.totalRebounds  " +
            "FROM PlayerStats ps " +
            "WHERE ps.game.idGame = :idGame")
    List<Object[]> getPlayerStatsByIdGame(@Param("idGame") Long idGame);

    /**
     * Metodo per trovare le statistiche di un player specfifico all interno di un team in una certa stagione
     * @param idPlayer id del player interessato
     * @param season id della stagione per la quale interessano le statistiche
     * @return lista di statistiche del player ricercato
     */
    @Query("SELECT SUM(ps.points) AS pointsSum, SUM(ps.totalRebounds) AS totalReboundsSum, SUM(ps.assist) AS assistSum, SUM(CAST(ps.minutes AS double)) AS minutesSum, " +
            "SUM(ps.blocks) AS blocksSum, SUM(ps.steals) AS stealsSum, ps.freeThrowsMade, ps.freeThrowsAttempted, ps.threePointPercentage, ps.fieldGoalsMade, AVG(CAST(ps.minutes AS double)), COUNT(ps.player.idPlayer)" +
            "FROM PlayerStats ps " +
            "JOIN Game g ON g.idGame = ps.game.idGame " +
            "WHERE g.season.idSeason = :idSeason AND ps.player.idPlayer = :idPlayer")
    List<Object[]> getPlayerStatsTeamByIdPlayer(@Param("idPlayer") int idPlayer, @Param("idSeason") int season);


}
