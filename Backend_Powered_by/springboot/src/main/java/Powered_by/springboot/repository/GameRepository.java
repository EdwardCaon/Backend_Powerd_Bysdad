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
    /**
     * Metodo usato nella home, restiuisce una lista di partite inerenti al giorno inserito
     * @param startOfDay inizio della giornata
     * @param endOfDay fine della giornata
     * @return lista di parite
     */

    @Query("SELECT " +
            "g.idGame, g.start, " +
            "t.nameTeam, t.logo, t.colour, s.p1, s.p2, s.p3, s.p4, s.p5, " +
            "t_e.nameTeam, t_e.logo, t_e.colour ,s_e.p1, s_e.p2, s_e.p3, s_e.p4, s_e.p5 " +
            "FROM Game g " +
            "JOIN g.home t " +
            "JOIN g.visitors t_e " +
            "JOIN Score s ON s.team.idTeam = t.idTeam " +
            "JOIN Score s_e ON s_e.team.idTeam = t_e.idTeam " +
            "WHERE g.start BETWEEN :startOfDay AND :endOfDay and s.game.idGame = g.idGame and s_e.game.idGame = g.idGame " +
            "group by  t.nameTeam")
    List<Object[]> getGamesForDayRange(
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    /**
     * Metodo per ricercare le partite di un team specifico  degli ultimi tre mesi
     * @param idTeam identificativo del team
     * @param startOfDay giorno corrente
     * @param endOfDay giorno del passato
     * @return lista di game passati
     */
    @Query("SELECT " +
            "g.idGame, g.start, " +
            "t.nameTeam, t.logo, t.colour, s.p1, s.p2, s.p3, s.p4, s.p5, " +
            "t_e.nameTeam, t_e.logo, t_e.colour ,s_e.p1, s_e.p2, s_e.p3, s_e.p4, s_e.p5 " +
            "FROM Game g " +
            "JOIN g.home t " +
            "JOIN g.visitors t_e " +
            "JOIN Score s ON s.team.idTeam = t.idTeam " +
            "JOIN Score s_e ON s_e.team.idTeam = t_e.idTeam " +
            "    WHERE g.start BETWEEN :startOfDay AND :endOfDay AND g.home.idTeam = :idTeam  " +
            "GROUP BY g.start  ")
    List<Object[]> getGameForTeam(
            @Param("idTeam") int idTeam,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    /**
     * Metodo per ricercare le prossime sei partite programmate di un team specifico
     * @param idTeam identificativo del team
     * @param startOfDay giorno di oggi
     * @return lista delle prossime sei partite a partire dal giorno corrente
     */
    @Query("SELECT " +
            "g.idGame, g.start, " +
            "t.nameTeam AS homeTeamName, t.logo AS homeTeamLogo, t.colour AS homeTeamColour,  " +
            "t_e.nameTeam AS visitorTeamName, t_e.logo AS visitorTeamLogo, t_e.colour AS visitorTeamColour " +
            "FROM Game g " +
            "JOIN g.home t " +
            "JOIN g.visitors t_e " +
            "WHERE g.start = :startOfDay AND g.home.idTeam = :idTeam  " +
            "GROUP BY g.start " +
            "ORDER BY g.start ASC " +  // Ordina in modo ascendente in base alla data di inizio
            "LIMIT 6")  // Limita i risultati a 6
    List<Object[]> getProgrammate(
            @Param("idTeam") int idTeam,
            @Param("startOfDay") LocalDateTime startOfDay);



}
