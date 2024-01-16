package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Team;
import Powered_by.springboot.payload.response.TeamClassificaResponse;
import Powered_by.springboot.payload.response.TeamSeasonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {


    @Query(value = "SELECT " +
            "    s.id_team AS teamId," +
            "    t.logo AS teamLogo, " +
            "    t.name_team AS teamName," +
            "    t.colour as colour, " +
            "    COUNT(*) AS victories," +
            "    COUNT(*) / MAX(ts.games_played) * 100 AS winPercentage," +
            "    MAX(ts.games_played) - COUNT(*) AS lose, " +
            "    plus_minus   " +
            "FROM " +
            "    `score` AS s " +
            "JOIN " +
            "    game AS g ON s.id_game = g.id_game " +
            "LEFT JOIN (" +
            "    SELECT id_team, MAX(games_played) as games_played, plus_minus   " +
            "    FROM team_statistic" +
            "    GROUP BY id_team " +
            ") ts ON ts.id_team = s.id_team " +
            "JOIN " +
            "    team AS t ON t.id_team = ts.id_team " +
            "WHERE " +
            "    g.season = :season and t.id_league = :idLeague" +
            "    AND s.total_points = ( " +
            "        SELECT " +
            "            MAX(total_points) " +
            "        FROM " +
            "            `score` AS s_inner " +
            "        WHERE " +
            "            s_inner.id_game = s.id_game" +
            "    ) " +
            "GROUP BY " +
            "    s.id_team, t.logo, t.name_team " +
            "ORDER BY " +
            "    victories DESC ;", nativeQuery = true)
    List<Object[]> getClassifica(@Param("season") int season, int idLeague);

    @Query("SELECT pd.id.idPlayer , p.firstname, p.lastname," +
            "t.nameTeam, t.logo, p.country , p.dateOfBirth, p.height, pd.pos ,pd.shirtNumber " +
            "FROM PlayerDetails pd " +
            "JOIN pd.player p " +
            "JOIN pd.team  t " +
            " WHERE pd.team.idTeam = :idTeam AND " +
            "pd.season.idSeason = :season  ")
    List<Object[]> getPlayerByTeamAndSeason(int idTeam, int season);

    @Query("SELECT t.idTeam " +
            "FROM Team t " +
            "where t.nameTeam = :nameTeam")
    int getIdTeam(String nameTeam);

    List<Team> findTeamByIdTeam(int idTeam);
}


/*
    @Query("SELECT NEW Powered_by.springboot.payload.response.TeamClassificaResponse(" +
            "CAST(s.team.idTeam as int ), " +
            "CAST(s.team.nameTeam as String) , " +
            "CAST(s.team.logo as String ), " +
            "CAST(COUNT(s) as Long ), " +
            "CAST(MAX(ts.gamesPlayed) as Long )) " +
            "FROM Score s " +
            "JOIN s.game g " +
            "LEFT JOIN TeamStatistic ts ON ts.team.idTeam = s.team.idTeam " +
            "WHERE " +
            "g.season.idSeason = :seasonId " +
            " AND s.totalPoints = (" +
            "SELECT MAX(sInner.totalPoints) " +
            "FROM Score sInner " +
            "WHERE sInner.game = s.game" +
            ") " +
            "GROUP BY " +
            "s.team.idTeam, s.team.nameTeam, s.team.logo " +
            "ORDER BY " +
            "COUNT(s) DESC")
    List<TeamClassificaResponse> countWinsByTeamAndSeason(@Param("seasonId") int seasonId);

*/
