package Powered_by.springboot.service;


import Powered_by.springboot.payload.response.PlayerPanoramicResponse;
import Powered_by.springboot.payload.response.PlayerSearchBarResponse;
import Powered_by.springboot.payload.response.PlayerStatsGameResponse;
import Powered_by.springboot.repository.PlayerDetailsRepository;
import Powered_by.springboot.repository.PlayerRepository;
import Powered_by.springboot.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerService {



    private final PlayerDetailsRepository playerDetailsRepository;
    private final PlayerRepository playerRepository;
    private SeasonRepository seasonRepository;
    @Autowired
    public PlayerService(PlayerDetailsRepository playerDetailsRepository, PlayerRepository playerRepository, SeasonRepository seasonRepository) {
        this.playerDetailsRepository = playerDetailsRepository;
        this.playerRepository = playerRepository;
        this.seasonRepository = seasonRepository;


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
    public List<PlayerSearchBarResponse> getAllPlayer() {
        return  maptoResponse(playerDetailsRepository.getAllPlayer());
    }

    private List<PlayerSearchBarResponse> maptoResponse(List<Object[]> allPlayer) {
        List<PlayerSearchBarResponse> playerList = new ArrayList<>();

        for (Object[] row : allPlayer) {
            PlayerSearchBarResponse player = new PlayerSearchBarResponse();
            player.setIdPlayer((Integer) row[0]);
            player.setFirstname((String) row[1]);
            player.setLastname((String) row[2]);
            player.setImg((String) row[3]); // Assuming the image URL is stored as a String in the database

            playerList.add(player);
        }

        return playerList;
    }

    public ResponseEntity<?> getPanoramicsPlayer(int idPlayer, int season) {
        Integer tmp_season = getSeasonByName(season);
        if (tmp_season == null ) {
            // Restituisci un messaggio di errore
            return new ResponseEntity<>("Invalid season or idLeague value.  League = "  + season + "\n" +
                    " Make sure that the season is an int (2021, 2022, or 2023) .", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok( maptoResponsePanormic(playerRepository.getPanoramicsPlayer(idPlayer, tmp_season)));
    }

    private List<PlayerPanoramicResponse> maptoResponsePanormic(List<Object[]> panoramicsPlayer) {
        List<PlayerPanoramicResponse> playerList = new ArrayList<>();

        for (Object[] result : panoramicsPlayer) {
            PlayerPanoramicResponse playerResponse = new PlayerPanoramicResponse();

            // Calcolo data di nascita
            LocalDate dateOfBirth = result[0] != null ? ((java.sql.Date) result[0]).toLocalDate() : null;
            if (dateOfBirth != null) {
                LocalDate currentDate = LocalDate.now();
                int age = Period.between(dateOfBirth, currentDate).getYears();
                playerResponse.setEta(String.valueOf(age));
            } else {
                playerResponse.setEta("N/D"); // set nullo se è null la data di nascita
            }

            playerResponse.setNameCountry(result[1] != null ? (String) result[1] : "");
            playerResponse.setFlagCountry(result[2] != null ? (String) result[2] : "");
            playerResponse.setHeight(result[3] != null ? ((Number) result[3]).doubleValue() : 0.0);
            playerResponse.setWeight(result[4] != null ? ((Number) result[4]).doubleValue() : 0.0);
            playerResponse.setPos(result[5] != null ? (String) result[5] : "");
            playerResponse.setShirtNumber(result[6] != null ? ((Integer) result[6]).intValue() : 0);
            playerResponse.setNameTeam(result[7] != null ? (String) result[7] :"" );
            playerResponse.setLogoTeam( result[8] != null ? (String) result[8] : "");

            playerList.add(playerResponse);
        }

        return playerList;
    }

}
