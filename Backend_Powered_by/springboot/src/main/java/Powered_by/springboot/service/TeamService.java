package Powered_by.springboot.service;

import Powered_by.springboot.entity.Team;
import Powered_by.springboot.entity.TeamStatistic;
import Powered_by.springboot.payload.response.PlayerStatsByIdPlayerResponse;
import Powered_by.springboot.payload.response.TeamClassificaResponse;
import Powered_by.springboot.payload.response.TeamSeasonResponse;
import Powered_by.springboot.repository.LeagueRepository;
import Powered_by.springboot.repository.PlayerStatsRepository;
import Powered_by.springboot.repository.SeasonRepository;
import Powered_by.springboot.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerStatsRepository playerStatsRepository;

    private final SeasonRepository seasonRepository;

    private final LeagueRepository leagueRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerStatsRepository playerStatsRepository,  SeasonRepository seasonRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.playerStatsRepository = playerStatsRepository;
        this.seasonRepository = seasonRepository;
        this.leagueRepository = leagueRepository;
    }

    /**
     * Metodo per ricevere la lista completa di team presente nel db
     * @return lista di Entity Team
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * Metodo per trovare l id della season attraverso il nome Season
     * @param season anno della season 2021, 2022,2023
     * @return id della season
     */
    public Integer getSeasonByName(int season) {
        Integer seasonId = seasonRepository.findByName(season);
        //if (seasonId == null) {
        //  throw new IllegalArgumentException("Season not found for season: " + season + "Season allowed are number 2021 2022 2023");
        //}
        return seasonId;
    }

    /**
     * Metodo per trovare l id della lega attraverso il nome lega
     * @param nameLeague nome della lega
     * @return identificativo della lega ricercata, null se non esiste
     */
    public Integer getLeagueByNameLeague(String nameLeague) {
        Integer leagueId = leagueRepository.findByNameLeague(nameLeague);
        //if (leagueId == null) {
        //  throw new IllegalArgumentException("League not found for idLeague: " + idLeague + "League allowed are string west or east");
        //}
        return leagueId;
    }

    /**
     * Metodo per ricevere la classfica di una lega e una stagione specifica
     * @param season id dealla stagione puo essere 7 8 o 9 ovvero 2021-2023
     * @param idLeague corrisponde ad 1 e 2 ovvero East e West, sono presetni altre leghe ma sono inutilizzate al momento
     * @return ritorna due entita diverse, qual'ora esistesse la classfica desiderata la si riceve,
     *          altrimenti si ricevono le indicazioni necessarie per ricevere le classifiche corrette
     */
    public ResponseEntity<?> getClassifica(int season, String idLeague) {
        Integer tmp_season = getSeasonByName(season);
        Integer tmp_idLeague = getLeagueByNameLeague(idLeague);

        if (tmp_season == null || tmp_idLeague == null) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League = " + idLeague  + "  Season = " + season + "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023) and the idLeague is 'East' or 'West'.", HttpStatus.BAD_REQUEST);
        }
        List<Object[]> resultList = teamRepository.getClassifica(tmp_season, tmp_idLeague);
        List<TeamClassificaResponse> teamClassificaResponses = mapToTeamClassificaResponse(resultList);

        return ResponseEntity.ok(teamClassificaResponses);
    }

    /**
     * metodo per converire il risultato della query sulla response
     * @param resultList lista di oggetti restituiti dalla query
     * @return lista mappata sull entita TeamClassificaResponse la quale contiene la classfica
     */
    private List<TeamClassificaResponse> mapToTeamClassificaResponse(List<Object[]> resultList) {
        // Creare una lista per gli oggetti TeamClassificaResponse
        List<TeamClassificaResponse> teamClassificaResponses = new ArrayList<>();

        // Iterare sulla lista degli array di oggetti restituiti dalla query
        for (Object[] result : resultList) {
            TeamClassificaResponse teamClassificaResponse = new TeamClassificaResponse();

            // Mappare gli elementi dell'array agli attributi dell'oggetto TeamClassificaResponse
            teamClassificaResponse.setTeamId(result[0] != null ? ((Integer) result[0]).intValue() : 0);
            teamClassificaResponse.setTeamLogo((String) result[1]);
            teamClassificaResponse.setTeamName((String) result[2]);
            teamClassificaResponse.setColour((String) result[3]);

            teamClassificaResponse.setVictories(result[4] != null ? ((Number) result[4]).intValue() : 0);
            teamClassificaResponse.setWinPercentage(result[5] != null ? ((Number) result[5]).doubleValue() : 0);
            teamClassificaResponse.setLose(result[6] != null ? ((Number) result[6]).intValue() : 0);
            teamClassificaResponse.setGamePlayed(
                    (result[4] != null ? ((Number) result[4]).intValue() : 0) +
                            (result[6] != null ? Math.abs(((Number) result[6]).intValue()) : 0)
            );

            // diff points
            teamClassificaResponse.setDiffPoints(result[7] != null ? ((Number) result[7]).intValue() : 0);

            // Aggiungere l'oggetto TeamClassificaResponse alla lista
            teamClassificaResponses.add(teamClassificaResponse);
        }

        return teamClassificaResponses;
    }


    /**
     * Metodo per ricevere la formazione di un team in base all id_team e alla stagione
     * @param idTeam identificativo del team
     * @param season id_season identificativo della stagione
     * @return lista di player (formazione del team)
     */
    public ResponseEntity<?> getPlayerByTeamAndSeason(int idTeam, int season) {
        Integer tmp_season = getSeasonByName(season);
        if (tmp_season == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League =    " + season +  "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023).", HttpStatus.BAD_REQUEST);
        }
        List<Object[]> resultList = teamRepository.getPlayerByTeamAndSeason(idTeam ,tmp_season);
        List<TeamSeasonResponse> teamSeasonResponses = mapToTeamPlayerDetails(resultList);
        return ResponseEntity.ok(teamSeasonResponses);
    }

    /**
     * Metodo per mappare la lista di oggetti sulla lista di entita TeamSeasonResponse
     * @param resultList lista di player ricevuti dalla repository
     * @return lista id oggetti TeamSeasonResponse mappati
     */
    private List<TeamSeasonResponse> mapToTeamPlayerDetails(List<Object[]> resultList) {
        List<TeamSeasonResponse> teamSeasonResponses = new ArrayList<>();
        for (Object[] result : resultList) {
            TeamSeasonResponse teamSeasonResponse = new TeamSeasonResponse();

            teamSeasonResponse.setIdPlayer(((Integer) result[0]).intValue());
            teamSeasonResponse.setFirsname(result[1] != null ? (String) result[1] : "");
            teamSeasonResponse.setLastname(result[2] != null ? (String) result[2] : "");
            teamSeasonResponse.setNameTeam(result[3] != null ? (String) result[3] : "");
            teamSeasonResponse.setLogo(result[4] != null ? (String) result[4] : "");
            teamSeasonResponse.setCountry(result[5] != null ? (String) result[5] : "N/D");

            // Calcolo data di nascita
            LocalDate dateOfBirth = (LocalDate) result[6];
            if (dateOfBirth != null) {
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(dateOfBirth, currentDate).getYears();
                teamSeasonResponse.setEta(String.valueOf(age));
            } else {
                teamSeasonResponse.setEta("N/D"); // set nullo se la data di nascita non e calcolabile
            }
            if (result[7] != null) {
                teamSeasonResponse.setHeight(((Number) result[7]).doubleValue());
            } else {
                teamSeasonResponse.setHeight(0.0);
            }

            teamSeasonResponse.setPos(result[8] != null ? (String) result[8] : "");
            teamSeasonResponse.setShirtNumber(result[9] != null ? ((Integer) result[9]).intValue() : 0);
            teamSeasonResponse.setImg(result[10] != null ? (String) result[10] : "https://it.global.nba.com/_next/static/media/headNotFound.1037c381.svg");
            teamSeasonResponse.setLogoCountry(result[11] != null ? (String) result[11] : "https://www.e-ir.info/wp-content/uploads/fly-images/97386/Untitled-11-807x455-c.jpg");
            teamSeasonResponses.add(teamSeasonResponse);
        }
        return teamSeasonResponses;
    }

    /**
     * Metoodo per ricevere le statistiche di un player in una certa stagione
     * @param idPlayer identificativo del player
     * @param season id della stagione
     * @return ritorna un errore se non viene trovata la stagione
     */
    public ResponseEntity<?> getPlayerStatsTeamByIdPlayer(int idPlayer, int season) {
        Integer tmp_season = getSeasonByName(season);
        if (tmp_season == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League = " + season +  "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023).", HttpStatus.BAD_REQUEST);
        }
        List<Object[]> resultList = playerStatsRepository.getPlayerStatsTeamByIdPlayer(idPlayer ,tmp_season);
        List<PlayerStatsByIdPlayerResponse> playerStatsByIdPlayerResponse = mapToPlayerStatsByIdPlayerResponse(resultList);
        return ResponseEntity.ok(playerStatsByIdPlayerResponse);
    }

    /**
     * Metodo per mappare i risultati della repository in una lista di Response
     * @param resultList lista di oggetti
     * @return lista di entita PlayerStatsByIdPlayerResponse
     */
    private List<PlayerStatsByIdPlayerResponse> mapToPlayerStatsByIdPlayerResponse(List<Object[]> resultList) {
        List<PlayerStatsByIdPlayerResponse> playerStatsResponses = new ArrayList<>();

        // Iterare sulla lista degli oggetti PlayerStats restituiti dalla query
        for (Object[] playerStats : resultList) {
            PlayerStatsByIdPlayerResponse playerStatsResponse = new PlayerStatsByIdPlayerResponse();

            // Mappare gli attributi dell'oggetto PlayerStats agli attributi dell'oggetto PlayerStatsByIdPlayerResponse
            playerStatsResponse.setTotalPoints(getLongValue(playerStats[0]));
            playerStatsResponse.setTotalRebounds(getLongValue(playerStats[1]));
            playerStatsResponse.setTotalAssists(getLongValue(playerStats[2]));
            playerStatsResponse.setTotalMinutes(getLongValue(playerStats[3]));
            playerStatsResponse.setTotalBlocks(getLongValue(playerStats[4]));
            playerStatsResponse.setTotalSteals(getLongValue(playerStats[5]));

            // Calcolare la percentuale di tiri liberi e impostarla nel campo
            double freeThrowPercentage = calculatePercentage(
                    Math.toIntExact(getLongValue( playerStats[6] )),
                    Math.toIntExact(getLongValue( playerStats[7] ))
            );

            playerStatsResponse.setFreeThrowPercentage(freeThrowPercentage);

            playerStatsResponse.setThreePointPercentage((Double) playerStats[8]);
            playerStatsResponse.setFieldGoalsMade(getLongValue(playerStats[9]));
            playerStatsResponse.setAvgMinutes((Double) playerStats[10]);
            playerStatsResponse.setGamePlayed((Long) playerStats[11]);

            // Aggiungere l'oggetto PlayerStatsByIdPlayerResponse alla lista
            playerStatsResponses.add(playerStatsResponse);
        }

        return playerStatsResponses;
    }

    /**
     * Metodo per castare un valore anche nullo in Long
     * @param value
     * @return il valore della value castato in Long
     */

    // Metodo per ottenere il valore come Long, gestendo il caso di null
    private Long getLongValue(Object value) {
        return value != null ? ((Number) value).longValue() : 0L;
    }

    /**
     * Metodo per calolcare la percentuale di tiri
     * @param made tiri segnati
     * @param attempted tiri tentati
     * @return percentuale di successo di tiro
     */
    // Metodo per calcolare la percentuale
    private Double calculatePercentage(Integer made, Integer attempted) {
        if (attempted == null || attempted == 0) {
            return 0.0;
        }
        return ((double) made / attempted) * 100;
    }

    /**
     * Metodo per restiuire una lista di Team con i relativi dati es. nameTeam Colour, city, logo
     * @param nameTeam nome del team
     * @return
     */
    public ResponseEntity<?> getTeamById(String nameTeam) {
        Integer idTeam = getIdTeamByNameTeam(nameTeam);
        if (idTeam == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid nameTeam value.  Name Team =   + " + idTeam , HttpStatus.BAD_REQUEST);
        }
        List <Team> teamList = teamRepository.findTeamByIdTeam(idTeam);
        return ResponseEntity.ok(teamList);
    }

    /**
     * Metodo per trovare l id di un team tramite il nome del team
     * @param nameTeam nome del team
     * @return ritorna l'id dell team o null se non viene trovato
     */
    private Integer getIdTeamByNameTeam(String nameTeam) {
        return  teamRepository.getIdTeamByNameTeam(nameTeam);
    }

    /**
     * Metodo per trovare le stats stagionali di un team
     * @param idTeam identificativo del team id_team
     * @param season anno della stagione da converire nell id_season
     * @return le stats del player come entita oppure un messaggio d errore
     */
    public ResponseEntity<?> getTeamStatsSeason(int idTeam, int season) {
        Integer tmp_season = getSeasonByName(season);
        if (tmp_season == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League = " + season +  "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023).", HttpStatus.BAD_REQUEST);
        }
        List<TeamStatistic> resultList = teamRepository.getTeamStatsSeason(idTeam,tmp_season);
        return ResponseEntity.ok(resultList);
    }


}