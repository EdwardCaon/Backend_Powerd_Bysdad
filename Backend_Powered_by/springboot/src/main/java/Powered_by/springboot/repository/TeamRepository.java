package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Team;
import Powered_by.springboot.entity.TeamStatistic;

import Powered_by.springboot.payload.response.TeamClassificaResponse;
import Powered_by.springboot.payload.response.TeamSeasonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    /**
     * Ritorna la classifica West ed East di un certo anno
     * @param season id della stagione che puo essere 7 8 o 9 a partire dal 2021 fino al 2023
     * @param idLeague id lega 1 e 2 per East e West
     * @return
     */
    @Query(value = "SELECT t.id_team , t.logo, t.name_team, t.colour, " +
            "ts.win,  ts.win_percentage* 100  ,ts.lose, ts.plus_minus " +
            "FROM `team_statistic` as ts " +
            "JOIN Team t on ts.id_team = t.id_team  " +
            "WHERE id_season = :season and t.id_league =  :idLeague " +
            "ORDER BY ts.rank;", nativeQuery = true)
    List<Object[]> getClassifica(@Param("season") int season, int idLeague);

    /**
     * Ritorna la formazione della squadra in una determinata stagione
     * @param idTeam id della squadra
     * @param season id della stagione che puo essere 7 8 o 9 a partire dal 2021 fino al 2023
     * @return lista di oggetti Player da mappare sulla response
     */
    @Query("SELECT pd.id.idPlayer , p.firstname, p.lastname," +
            "t.nameTeam, t.logo, p.country.nameCountry, p.dateOfBirth, p.height, pd.pos ,pd.shirtNumber, pd.img, p.country.flag " +
            "FROM PlayerDetails pd " +
            "JOIN pd.player p " +
            "JOIN pd.team  t " +
            " WHERE pd.team.idTeam = :idTeam AND " +
            "pd.season.idSeason = :season  ")
    List<Object[]> getPlayerByTeamAndSeason(int idTeam, int season);

    /**
     * Restituisce una lista di team
     * @param idTeam nome del team
     * @return lista di oggetti team
     */
    List<Team> findTeamByIdTeam(int idTeam);

    /**
     * Query per cercare idTeam tramite il nome del team
     * @param nameTeam il nome del team da cercare
     * @return idTeam
     */
    @Query("SELECT t.idTeam " +
        "FROM Team t " +
        "WHERE t.nameTeam = :nameTeam")
    Integer getIdTeamByNameTeam(String nameTeam);

@Query("SELECT ts " +
        "FROM TeamStatistic ts " +
        "WHERE ts.season.idSeason = :tmpSeason and ts.team.idTeam = :idTeam ")
    List<TeamStatistic> getTeamStatsSeason(int idTeam,Integer tmpSeason);
}
