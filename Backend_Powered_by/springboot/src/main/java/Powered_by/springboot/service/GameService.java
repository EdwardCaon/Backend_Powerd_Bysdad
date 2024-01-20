package Powered_by.springboot.service;
import Powered_by.springboot.payload.response.GameDayResponse;
import Powered_by.springboot.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * Metodo per ricevere una lista di partite inerenti ad una giornata
     * @param startOfDay inizio giornata
     * @param endOfDay fine giornata
     * @return lista di game di una giornata
     */
    public List<GameDayResponse> getGamesForDayRange(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        List<Object[]> resultList = gameRepository.getGamesForDayRange(startOfDay, endOfDay);
        return mapToGameforDayResponse(resultList);
    }

    /**
     * Metodo per mappare i risultati delle query inerenti ai game
     * @param resultList lista di oggetti
     * @return lista mappata sulla relativa response (entita)
     */
    private List<GameDayResponse> mapToGameforDayResponse(List<Object[]> resultList) {
        // Create a list for gameDayResponse objects
        List<GameDayResponse> gameDayResponses = new ArrayList<>();

        // Iterate over the list of arrays returned by the query
        for (Object[] result : resultList) {

            GameDayResponse gameDayResponse = new GameDayResponse();

            // Map array elements to gameDayResponse attributes
            gameDayResponse.setGameId(result[0] != null ? ((Integer) result[0]).intValue() : 0);
            gameDayResponse.setStart((LocalDateTime) result[1]);
            gameDayResponse.setHomeTeamName((String) result[2]);
            gameDayResponse.setHomeTeamLogo((String) result[3]);
            gameDayResponse.setHomeColour((String) result[4]);
            gameDayResponse.setP1Home(result[5] != null ? ((Integer) result[5]).intValue() : 0);
            gameDayResponse.setP2Home(result[6] != null ? ((Integer) result[6]).intValue() : 0);
            gameDayResponse.setP3Home(result[7] != null ? ((Integer) result[7]).intValue() : 0);
            gameDayResponse.setP4Home(result[8] != null ? ((Integer) result[8]).intValue() : 0);
            gameDayResponse.setP5Home(result[9] != null ? ((Integer) result[9]).intValue() : 0);
            gameDayResponse.setVisitorTeamName((String) result[10]);
            gameDayResponse.setVisitorTeamLogo((String) result[11]);
            gameDayResponse.setVisitorsColour((String) result[12]);
            gameDayResponse.setP1Visitor(result[13] != null ? ((Integer) result[13]).intValue() : 0);
            gameDayResponse.setP2Visitor(result[14] != null ? ((Integer) result[14]).intValue() : 0);
            gameDayResponse.setP3Visitor(result[15] != null ? ((Integer) result[15]).intValue() : 0);
            gameDayResponse.setP4Visitor(result[16] != null ? ((Integer) result[16]).intValue() : 0);
            gameDayResponse.setP5Visitor(result[17] != null ? ((Integer) result[17]).intValue() : 0);

            // Add the gameDayResponse object to the list
            gameDayResponses.add(gameDayResponse);
        }

        return gameDayResponses;
    }

    /**
     * Metodo per ricercare tutte le partite di un team specifico
     * @param idTeam identificativo del team
     * @param startOfDay giorno di oggi
     * @param endOfDay giorno del passato (3 mesi prima di oggi)
     * @return  lista di partite passate di un team specifico
     */
    public List<GameDayResponse> getGameForTeam(int idTeam, LocalDateTime startOfDay, LocalDateTime endOfDay){
        List<Object[]> resultList =gameRepository.getGameForTeam(idTeam, startOfDay, endOfDay);
        return mapToGameforDayResponse(resultList);
    }

    /**
     * Metodo per ricercare le prossime sei partite di un team specifico
     * @param idTeam  identificativo del team
     * @param startOfDay giorno di oggi
     * @return lista di sei partite di un team specifico
     */
    public List<GameDayResponse> getProgrammate(int idTeam, LocalDateTime startOfDay){
        List<Object[]> resultList =gameRepository.getProgrammate(idTeam, startOfDay);
        return mapToGameforDayResponse(resultList);
    }

}
