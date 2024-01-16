package Powered_by.springboot.service;

import Powered_by.springboot.entity.Team;
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
import java.math.BigDecimal;
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


    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Integer getSeasonByName(int season) {
        Integer seasonId = seasonRepository.findByName(season);
        //if (seasonId == null) {
        //  throw new IllegalArgumentException("Season not found for season: " + season + "Season allowed are number 2021 2022 2023");
        //}
        return seasonId;
    }

    public Integer getLeagueByNameLeague(String idLeague) {
        Integer leagueId = leagueRepository.findByNameLeague(idLeague);
        //if (leagueId == null) {
        //  throw new IllegalArgumentException("League not found for idLeague: " + idLeague + "League allowed are string west or east");
        //}
        return leagueId;
    }

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
            teamClassificaResponse.setVictories(result[4] != null ? ((Long) result[4]).longValue() : 0);
            teamClassificaResponse.setWinPercentage(result[5] != null ? ((BigDecimal) result[5]).doubleValue() : 0);
            teamClassificaResponse.setLose(result[6] != null ? ((Long) result[6]).longValue() : 0);
            teamClassificaResponse.setGamePlayed(
                    (result[4] != null ? ((Long) result[4]).longValue() : 0) +
                            (result[6] != null ? Math.abs(((Long) result[6]).longValue()) : 0)
            );
            //diff points
            teamClassificaResponse.setDiffPoints((result[7] != null ? (Integer) result[7] : 0));

            // Aggiungere l'oggetto TeamClassificaResponse alla lista
            teamClassificaResponses.add(teamClassificaResponse);
        }

        return teamClassificaResponses;
    }


    public ResponseEntity<?> getPlayerByTeamAndSeason(int idTeam, int season) {
        Integer tmp_season = getSeasonByName(season);


        if (tmp_season == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League =   + " + season +  "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023).", HttpStatus.BAD_REQUEST);
        }
        List<Object[]> resultList = teamRepository.getPlayerByTeamAndSeason(idTeam ,tmp_season);
        List<TeamSeasonResponse> teamSeasonResponses = mapToTeamPlayerDetails(resultList);
        return ResponseEntity.ok(teamSeasonResponses);
    }

    private List<TeamSeasonResponse> mapToTeamPlayerDetails(List<Object[]> resultList) {
        List<TeamSeasonResponse> teamSeasonResponses = new ArrayList<>();
        for (Object[] result : resultList) {
            TeamSeasonResponse teamSeasonResponse = new TeamSeasonResponse();

            teamSeasonResponse.setIdPlayer(((Integer) result[0]).intValue());
            teamSeasonResponse.setFirsname(result[1] != null ? (String) result[1] : "");
            teamSeasonResponse.setLastname(result[2] != null ? (String) result[2] : "");
            teamSeasonResponse.setNameTeam(result[3] != null ? (String) result[3] : "");
            teamSeasonResponse.setLogo(result[4] != null ? (String) result[4] : "");
            teamSeasonResponse.setCountry(result[5] != null ? (String) result[5] : "");


            // Calcolo data di nascita
            LocalDate dateOfBirth = (LocalDate) result[6];
            if (dateOfBirth != null) {
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(dateOfBirth, currentDate).getYears();
                teamSeasonResponse.setEta(String.valueOf(age));
            } else {
                teamSeasonResponse.setEta("N/D"); // set nullo se Ã¨ null la data di nascita
            }
            if (result[7] != null) {
                teamSeasonResponse.setHeight(((Number) result[7]).doubleValue());
            } else {
                teamSeasonResponse.setHeight(0.0);
            }

            teamSeasonResponse.setPos(result[8] != null ? (String) result[8] : "");
            teamSeasonResponse.setShirtNumber(result[9] != null ? ((Integer) result[9]).intValue() : 0);

            teamSeasonResponses.add(teamSeasonResponse);
        }
        return teamSeasonResponses;
    }


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

            // Aggiungere l'oggetto PlayerStatsByIdPlayerResponse alla lista
            playerStatsResponses.add(playerStatsResponse);
        }

        return playerStatsResponses;
    }



    // Metodo per ottenere il valore come Long, gestendo il caso di null
    private Long getLongValue(Object value) {
        return value != null ? ((Number) value).longValue() : 0L;
    }

    // Metodo per calcolare la percentuale
    private Double calculatePercentage(Integer made, Integer attempted) {
        if (attempted == null || attempted == 0) {
            return 0.0;
        }
        return ((double) made / attempted) * 100;
    }


    public List<Team> getTeamById(int idTeam) {
        List <Team> teamList = teamRepository.findTeamByIdTeam(idTeam);
        return teamList;
    }
}