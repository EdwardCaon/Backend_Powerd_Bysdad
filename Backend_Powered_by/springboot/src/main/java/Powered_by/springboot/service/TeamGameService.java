package Powered_by.springboot.service;

import Powered_by.springboot.payload.response.GameStatsResponse;
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

    public List<GameStatsResponse> getTeamGameStats(int idGame) {
        List<Object[]> gameStatsResponse = teamGameStatsRepository.findByGameId(idGame);
        return mapToTeamGameStatsResponseList(gameStatsResponse);
    }

    private List<GameStatsResponse> mapToTeamGameStatsResponseList(List<Object[]> gameStatsObjects) {
        List<GameStatsResponse> gameStatsResponse = new ArrayList<>();
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

        return gameStatsResponse;
    }

    private Integer getInteger(Object value) {
        return (value instanceof Integer) ? (Integer) value : 0;
    }

    private Float getFloat(Object value) {
        return (value instanceof Float) ? (Float) value : 0.0f;
    }

    private String getString(Object value) {
        return (value instanceof String) ? (String) value : "N/D";
    }
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
