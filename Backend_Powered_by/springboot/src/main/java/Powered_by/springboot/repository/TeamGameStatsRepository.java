package Powered_by.springboot.repository;

import Powered_by.springboot.entity.TeamGameStats;
import Powered_by.springboot.payload.response.GameStatsResponse;
import Powered_by.springboot.payload.response.TeamGameStatsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamGameStatsRepository extends JpaRepository<TeamGameStats, Integer> {
    /*@Query("SELECT NEW Powered_by.springboot.payload.response.GameStatsResponse(" +
            "g.idGame, a.nameArena, a.city, t.nameTeam, t.logo, s.p1, s.p2, s.p3, s.p4, s.p5) " +
            "FROM TeamGameStats tgs " +
            "JOIN tgs.game g " +
            "JOIN g.arena a " +
            "JOIN tgs.team t " +
            "JOIN Score s ON s.id.idGame = g.idGame " +
            "WHERE g.idGame = :idGame " +
            "GROUP BY  t.nameTeam")
    List<GameStatsResponse> findByGameIdPROVA(@Param("idGame") int idGame);*/


    @Query(value = "SELECT " +
            "    g.id_game AS gameId, " +
            "    a.name_arena AS arenaName, " +
            "    a.city, " +
            "    tHomeTeam.name_team AS homeTeamName, " +
            "    tHomeTeam.logo AS homeTeamLogo, " +
            "    tVisitorTeam.name_team AS visitorTeamName, " +
            "    tVisitorTeam.logo AS visitorTeamLogo, " +
            "    tHome.points_in_paint, " +
            "    tHome.biggest_lead, " +
            "    tHome.second_chance_points, " +
            "    tHome.points_off_turnovers, " +
            "    tHome.longest_run, " +
            "    tHome.points, " +
            "    tHome.fgm, " +
            "    tHome.fga, " +
            "    tHome.fgp, " +
            "    tHome.ftm, " +
            "    tHome.fta, " +
            "    tHome.ftp, " +
            "    tHome.tpm, " +
            "    tHome.tpa, " +
            "    tHome.tpp, " +
            "    tHome.off_reb, " +
            "    tHome.tot_reb, " +
            "    tHome.assists, " +
            "    tHome.p_fouls, " +
            "    tHome.steals, " +
            "    tHome.turnovers, " +
            "    tHome.blocks, " +
            "    tHome.plus_minus, " +
            "    tHome.min, " +
            "    tVisitor.points_in_paint AS visitorPointsInPaint, " +
            "    tVisitor.biggest_lead AS visitorBiggestLead, " +
            "    tVisitor.second_chance_points AS visitorSecondChancePoints, " +
            "    tVisitor.points_off_turnovers AS visitorPointsOffTurnovers, " +
            "    tVisitor.longest_run AS visitorLongestRun, " +
            "    tVisitor.points AS visitorPoints, " +
            "    tVisitor.fgm AS visitorFgm, " +
            "    tVisitor.fga AS visitorFga, " +
            "    tVisitor.fgp AS visitorFgp, " +
            "    tVisitor.ftm AS visitorFtm, " +
            "    tVisitor.fta AS visitorFta, " +
            "    tVisitor.ftp AS visitorFtp, " +
            "    tVisitor.tpm AS visitorTpm, " +
            "    tVisitor.tpa AS visitorTpa, " +
            "    tVisitor.tpp AS visitorTpp, " +
            "    tVisitor.off_reb AS visitorOffReb, " +
            "    tVisitor.tot_reb AS visitorTotReb, " +
            "    tVisitor.assists AS visitorAssists, " +
            "    tVisitor.p_fouls AS visitorPFouls, " +
            "    tVisitor.steals AS visitorSteals, " +
            "    tVisitor.turnovers AS visitorTurnovers, " +
            "    tVisitor.blocks AS visitorBlocks, " +
            "    tVisitor.plus_minus AS visitorPlusMinus, " +
            "    tVisitor.min AS visitorMin, " +
            "    DATE_FORMAT(g.start, '%Y-%m-%d %H:%i:%s') AS start, " +
            "    DATE_FORMAT(g.end, '%Y-%m-%d %H:%i:%s') AS end, " +
            " sHome.p1,sHome.p2,sHome.p3,sHome.p4,sHome.p5, "+
            " sVisitors.p1, sVisitors.p2, sVisitors.p3, sVisitors.p4, sVisitors.p5, " +
            "    tHomeTeam.colour AS colourHome, " +
            "    tVisitorTeam.colour AS colourVisitor " +
            "FROM " +
            "    team_game_stats tHome " +
            "JOIN " +
            "    game g ON tHome.id_game = g.id_game " +

            "JOIN " +
            "    arena a ON g.arena = a.id_arena " +
            "JOIN " +
            "    team tHomeTeam ON tHome.id_team = tHomeTeam.id_team " +
            "JOIN score sHome ON sHome.id_team = tHomeTeam.id_team " +
            "JOIN " +
            "    team_game_stats tVisitor ON tVisitor.id_game = g.id_game AND tVisitor.id_team != tHomeTeam.id_team " +

            "JOIN " +
            "    team tVisitorTeam ON tVisitor.id_team = tVisitorTeam.id_team " +
            "JOIN score sVisitors ON sVisitors.id_team = tVisitorTeam.id_team " +
            "WHERE " +
            "    g.id_game = :gameId " +
            "GROUP BY " +
            "   g.id_game ", nativeQuery = true)
    List<Object[]> findByGameId(@Param("gameId") int gameId);

/*
    @Query("SELECT NEW Powered_by.springboot.payload.response.TeamGameStatsResponse(" +
            "g.idGame, t.nameTeam ,tgs.ftm, (tgs.fgm - tgs.tpm) * 2, tgs.tpm * 3, tgs.points, " +
            "tgs.totReb, tgs.offReb, (tgs.totReb - tgs.offReb), tgs.assists, tgs.blocks, " +
            "tgs.steals, tgs.turnovers, tgs.pFouls, tgs.biggestLead, " +
            "FUNCTION('DATE_FORMAT', g.start, '%Y-%m-%d %H:%i:%s'), " +
            "FUNCTION('DATE_FORMAT', g.end, '%Y-%m-%d %H:%i:%s')) " +
            "FROM TeamGameStats tgs " +
            "JOIN tgs.game g " +
            "JOIN g.arena a " +
            "JOIN tgs.team t " +
            "WHERE g.idGame = :gameId")
    List<TeamGameStatsResponse> findStatsByGameId(@Param("gameId") int gameId);*/


}
