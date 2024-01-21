package Powered_by.springboot.service;

import Powered_by.springboot.payload.response.GameStatsResponse;
import Powered_by.springboot.repository.GameRepository;
import Powered_by.springboot.repository.TeamGameStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TeamGameService {

    @Autowired
    private TeamGameStatsRepository teamGameStatsRepository;
    @Autowired
    private GameRepository gameRepository;
    /**
     * Metodo per ricevere la lista di statistiche per game dei team
     * @param idGame identificativo del game
     * @return lista di statistiche dei team per game
     */
    public List<GameStatsResponse> getTeamGameStats(int idGame) {
        List<Object[]> gameStatsResponse = teamGameStatsRepository.findByGameId(idGame);
        return mapToTeamGameStatsResponseList(gameStatsResponse , idGame);
    }

    /**
     * Metodo per convertire la lista di oggetti in lista di oggetti di tipo GameStatsResponse
     * @param gameStatsObjects lista di oggetti
     * @return lista di entita GameStatsResponse
     */

    private List<GameStatsResponse> mapToTeamGameStatsResponseList(List<Object[]> gameStatsObjects, int idGame) {
        List<GameStatsResponse> gameStatsResponse = new ArrayList<>();

        if (gameStatsObjects != null && !gameStatsObjects.isEmpty()) {
            for (int i = 0; i < gameStatsObjects.size(); i++) {
                Object[] gameStatsObject = gameStatsObjects.get(i);

                GameStatsResponse response = new GameStatsResponse();

                response.setGameId(getInteger(gameStatsObject[0]));
                response.setArenaName(getString(gameStatsObject[1]));
                response.setCity(getString(gameStatsObject[2]));
                response.setHomeTeamName(getString(gameStatsObject[3]));
                response.setHomeTeamLogo(getString(gameStatsObject[4]));
                response.setVisitorTeamName(getString(gameStatsObject[5]));
                response.setVisitorTeamLogo(getString(gameStatsObject[6]));
                response.setPointsInPaint(getInteger(gameStatsObject[7]));
                response.setBiggestLead(getInteger(gameStatsObject[8]));
                response.setSecondChancePoints(getInteger(gameStatsObject[9]));
                response.setPointsOffTurnovers(getInteger(gameStatsObject[10]));
                response.setLongestRun(getInteger(gameStatsObject[11]));
                response.setPoints(getInteger(gameStatsObject[12]));
                response.setFgm(getInteger(gameStatsObject[13]));
                response.setFga(getInteger(gameStatsObject[14]));
                response.setFgp(getString(gameStatsObject[15]));
                response.setFtm(getInteger(gameStatsObject[16]));
                response.setFta(getInteger(gameStatsObject[17]));
                response.setFtp(getString(gameStatsObject[18]));
                response.setTpm(getInteger(gameStatsObject[19]));
                response.setTpa(getInteger(gameStatsObject[20]));
                response.setTpp(getString(gameStatsObject[21]));
                response.setOffReb(getInteger(gameStatsObject[22]));
                response.setTotReb(getInteger(gameStatsObject[23]));
                response.setAssists(getInteger(gameStatsObject[24]));
                response.setPFouls(getInteger(gameStatsObject[25]));
                response.setSteals(getInteger(gameStatsObject[26]));
                response.setTurnovers(getFloat(gameStatsObject[27]));
                response.setBlocks(getFloat(gameStatsObject[28]));
                response.setPlusMinus(getString(gameStatsObject[29]));
                response.setMin(getString(gameStatsObject[30]));
                response.setVisitorPointsInPaint(getInteger(gameStatsObject[31]));
                response.setVisitorBiggestLead(getInteger(gameStatsObject[32]));
                response.setVisitorSecondChancePoints(getInteger(gameStatsObject[33]));
                response.setVisitorPointsOffTurnovers(getInteger(gameStatsObject[34]));
                response.setVisitorLongestRun(getInteger(gameStatsObject[35]));
                response.setVisitorPoints(getInteger(gameStatsObject[36]));
                response.setVisitorFgm(getInteger(gameStatsObject[37]));
                response.setVisitorFga(getInteger(gameStatsObject[38]));
                response.setVisitorFgp(getString(gameStatsObject[39]));
                response.setVisitorFtm(getInteger(gameStatsObject[40]));
                response.setVisitorFta(getInteger(gameStatsObject[41]));
                response.setVisitorFtp(getString(gameStatsObject[42]));
                response.setVisitorTpm(getInteger(gameStatsObject[43]));
                response.setVisitorTpa(getInteger(gameStatsObject[44]));
                response.setVisitorTpp(getString(gameStatsObject[45]));
                response.setVisitorOffReb(getInteger(gameStatsObject[46]));
                response.setVisitorTotReb(getInteger(gameStatsObject[47]));
                response.setVisitorAssists(getInteger(gameStatsObject[48]));
                response.setVisitorPFouls(getInteger(gameStatsObject[49]));
                response.setVisitorSteals(getFloat(gameStatsObject[50]));
                response.setVisitorTurnovers(getFloat(gameStatsObject[51]));
                response.setVisitorBlocks(getFloat(gameStatsObject[52]));
                response.setVisitorPlusMinus(getString(gameStatsObject[53]));
                response.setVisitorMin(getString(gameStatsObject[54]));
                Object start = gameStatsObject[55];
                Object end = gameStatsObject[56];
                response.setP1Home(getInteger(gameStatsObject[57]));
                response.setP2Home(getInteger(gameStatsObject[58]));
                response.setP3Home(getInteger(gameStatsObject[59]));
                response.setP4Home(getInteger(gameStatsObject[60]));
                response.setP5Home(getInteger(gameStatsObject[61]));
                response.setP1Visitors(getInteger(gameStatsObject[62]));
                response.setP2Visitors(getInteger(gameStatsObject[63]));
                response.setP3Visitors(getInteger(gameStatsObject[64]));
                response.setP4Visitors(getInteger(gameStatsObject[65]));
                response.setP5Visitors(getInteger(gameStatsObject[66]));
                response.setColourHome(getString(gameStatsObject[67]));
                response.setColourVisitor(getString(gameStatsObject[68]));


                response.setMinutes(calculateDuration(start, end));

                gameStatsResponse.add(response);
            }
        } else {
            List<Object[]> gameDetailsStats = gameRepository.findStatsGame(idGame);
            Object[] stats = gameDetailsStats.get(0);
            GameStatsResponse defaultResponse = new GameStatsResponse();

            defaultResponse.setGameId(getInteger(stats[0]));  // Set null for Integer fields
            defaultResponse.setArenaName(getString(stats[17]));
            defaultResponse.setCity(getString(stats[18]));
            defaultResponse.setHomeTeamName(getString(stats[1]));
            defaultResponse.setHomeTeamLogo(getString(stats[2]));
            defaultResponse.setVisitorTeamName(getString(stats[9]));
            defaultResponse.setVisitorTeamLogo(getString(stats[10]));
            defaultResponse.setPointsInPaint(null);  // Set null for Integer fields
            defaultResponse.setBiggestLead(null);  // Set null for Integer fields
            defaultResponse.setSecondChancePoints(null);  // Set null for Integer fields
            defaultResponse.setPointsOffTurnovers(null);  // Set null for Integer fields
            defaultResponse.setLongestRun(null);  // Set null for Integer fields
            defaultResponse.setPoints(null);  // Set null for Integer fields
            defaultResponse.setFgm(null);  // Set null for Integer fields
            defaultResponse.setFga(null);  // Set null for Integer fields
            defaultResponse.setFgp("N/D");
            defaultResponse.setFtm(null);  // Set null for Integer fields
            defaultResponse.setFta(null);  // Set null for Integer fields
            defaultResponse.setFtp("N/D");
            defaultResponse.setTpm(null);  // Set null for Integer fields
            defaultResponse.setTpa(null);  // Set null for Integer fields
            defaultResponse.setTpp("N/D");
            defaultResponse.setOffReb(null);  // Set null for Integer fields
            defaultResponse.setTotReb(null);  // Set null for Integer fields
            defaultResponse.setAssists(null);  // Set null for Integer fields
            defaultResponse.setPFouls(null);  // Set null for Integer fields
            defaultResponse.setSteals(null);  // Set null for Integer fields
            defaultResponse.setTurnovers(null);  // Set null for Float fields
            defaultResponse.setBlocks(null);  // Set null for Float fields
            defaultResponse.setPlusMinus("N/D");
            defaultResponse.setMin("N/D");
            defaultResponse.setVisitorPointsInPaint(null);  // Set null for Integer fields
            defaultResponse.setVisitorBiggestLead(null);  // Set null for Integer fields
            defaultResponse.setVisitorSecondChancePoints(null);  // Set null for Integer fields
            defaultResponse.setVisitorPointsOffTurnovers(null);  // Set null for Integer fields
            defaultResponse.setVisitorLongestRun(null);  // Set null for Integer fields
            defaultResponse.setVisitorPoints(null);  // Set null for Integer fields
            defaultResponse.setVisitorFgm(null);  // Set null for Integer fields
            defaultResponse.setVisitorFga(null);  // Set null for Integer fields
            defaultResponse.setVisitorFgp("N/D");
            defaultResponse.setVisitorFtm(null);  // Set null for Integer fields
            defaultResponse.setVisitorFta(null);  // Set null for Integer fields
            defaultResponse.setVisitorFtp("N/D");
            defaultResponse.setVisitorTpm(null);  // Set null for Integer fields
            defaultResponse.setVisitorTpa(null);  // Set null for Integer fields
            defaultResponse.setVisitorTpp("N/D");
            defaultResponse.setVisitorOffReb(null);  // Set null for Integer fields
            defaultResponse.setVisitorTotReb(null);  // Set null for Integer fields
            defaultResponse.setVisitorAssists(null);  // Set null for Integer fields
            defaultResponse.setVisitorPFouls(null);  // Set null for Integer fields
            defaultResponse.setVisitorSteals(null);  // Set null for Float fields
            defaultResponse.setVisitorTurnovers(null);  // Set null for Float fields
            defaultResponse.setVisitorBlocks(null);  // Set null for Float fields
            defaultResponse.setVisitorPlusMinus("N/D");
            defaultResponse.setVisitorMin("N/D");
            defaultResponse.setMinutes(null);  // Set null for String field
            defaultResponse.setP1Home(getInteger(stats[4]));  // Set null for Integer fields
            defaultResponse.setP2Home(getInteger(stats[5]));  // Set null for Integer fields
            defaultResponse.setP3Home(getInteger(stats[6]));  // Set null for Integer fields
            defaultResponse.setP4Home(getInteger(stats[7]));  // Set null for Integer fields
            defaultResponse.setP5Home(getInteger(stats[8]));  // Set null for Integer fields
            defaultResponse.setP1Visitors(getInteger(stats[12]));  // Set null for Integer fields
            defaultResponse.setP2Visitors(getInteger(stats[13]));  // Set null for Integer fields
            defaultResponse.setP3Visitors(getInteger(stats[14]));  // Set null for Integer fields
            defaultResponse.setP4Visitors(getInteger(stats[15]));  // Set null for Integer fields
            defaultResponse.setP5Visitors(getInteger(stats[16]));  // Set null for Integer fields
            defaultResponse.setColourHome(getString(stats[3]));
            defaultResponse.setColourVisitor(getString(stats[11]));
            gameStatsResponse.add(defaultResponse);

        }

        return gameStatsResponse;
    }


    /**
     * Metodo per settare le value degli oggetti in numeri Integer e se nulli a 0
     * @param value valore dell oggetto
     * @return valore dell oggetto castato in Integer
     */
    private Integer getInteger(Object value) {
        return (value instanceof Integer) ? (Integer) value : 0;
    }

    /**
     * Metodo per settare le value degli oggetti in numeri Float e se nulli a 0
     * @param value valore dell oggetto
     * @return valore dell oggetto castato in Float
     */
    private Float getFloat(Object value) {
        return (value instanceof Float) ? (Float) value : 0.0f;
    }

    /**
     * Metodo per settare le value degli oggetti in stringhe
     * @param value valore della stringa
     * @return il valore dell object castato in stringa
     */

    private String getString(Object value) {
        return (value instanceof String) ? (String) value : "N/D";
    }

    /**
     * Metodo per calolare quanto un team ha giocato
     * @param start inizio partita
     * @param end fine partita
     * @return minuti giocati in campo, N/D  se non e possibile calcolare
     */
    private String calculateDuration(Object start, Object end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (start != null && end != null && start instanceof String && end instanceof String) {
            String startString = (String) start;
            String endString = (String) end;

            LocalDateTime startTime = LocalDateTime.parse(startString, formatter);
            LocalDateTime endTime = LocalDateTime.parse(endString, formatter);

            Duration duration = Duration.between(startTime, endTime);
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();

            return String.format("%02d:%02d", hours, minutes);
        } else {
            return "N/D";
        }
    }

}
