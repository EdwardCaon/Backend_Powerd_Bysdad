package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class TeamGameStatsInsertion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nba";
    private static final String USERNAME = "backend";
    private static final String PASSWORD = "111";

    @Scheduled(cron = "0 15 4 * * *") // Esegui alle 4:15 ogni giorno
    public void runFirstBatch() {
        System.out.println("Inizio inserimento team game stats");
        runBatch(0, 10);
    }

    @Scheduled(cron = "0 17 4 * * *") // Esegui alle 4:17 ogni giorno
    public void runSecondBatch() {
        runBatch(10, 10);
        System.out.println("Fine inserimento team game stats");
    }

    private void runBatch(int offset, int batchSize) {
        APIClient apiClient = new APIClient();

        try {
            List<Integer> gameIds = getGameIdsFromDatabase(offset, batchSize);
            for (Integer idGame : gameIds) {
                String responseData = apiClient.getData("games/statistics/?id=" + idGame);
                System.out.println("Team Data: " + responseData.toString());
                insertTeamGameStats(idGame, responseData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<Integer> getGameIdsFromDatabase(int offset, int batchSize) {
        List<Integer> gameIds = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT id_game FROM game WHERE start > ? AND start < ? ORDER BY start ASC LIMIT ? OFFSET ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Setta i parametri per la data di inizio (oggi - 2 giorni)
                LocalDate startDate = LocalDate.now().minusDays(2);
                preparedStatement.setString(1, startDate.format(DateTimeFormatter.ISO_DATE));

                // Setta i parametri per la data di fine (domani)
                LocalDate endDate = LocalDate.now().plusDays(1);
                preparedStatement.setString(2, endDate.format(DateTimeFormatter.ISO_DATE));

                preparedStatement.setInt(3, batchSize);
                preparedStatement.setInt(4, offset);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int idGame = resultSet.getInt("id_game");
                    gameIds.add(idGame);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameIds;
    }

    private static void insertTeamGameStats(Integer idGame, String responseData) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String insertQuery = "INSERT INTO team_game_stats (id_game, id_team, points_in_paint, biggest_lead, " +
                    "second_chance_points, points_off_turnovers, longest_run, points, fgm, fga, fgp, ftm, fta, ftp, tpm, tpa, " +
                    "tpp, off_reb, tot_reb, assists, p_fouls, steals, turnovers, blocks, plus_minus, min) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE " +
            "points_in_paint = VALUES(points_in_paint), " +
                    "biggest_lead = VALUES(biggest_lead), " +
                    "second_chance_points = VALUES(second_chance_points), " +
                    "points_off_turnovers = VALUES(points_off_turnovers), " +
                    "longest_run = VALUES(longest_run), " +
                    "points = VALUES(points), " +
                    "fgm = VALUES(fgm), " +
                    "fga = VALUES(fga), " +
                    "fgp = VALUES(fgp), " +
                    "ftm = VALUES(ftm), " +
                    "fta = VALUES(fta), " +
                    "ftp = VALUES(ftp), " +
                    "tpm = VALUES(tpm), " +
                    "tpa = VALUES(tpa), " +
                    "tpp = VALUES(tpp), " +
                    "off_reb = VALUES(off_reb), " +
                    "tot_reb = VALUES(tot_reb), " +
                    "assists = VALUES(assists), " +
                    "p_fouls = VALUES(p_fouls), " +
                    "steals = VALUES(steals), " +
                    "turnovers = VALUES(turnovers), " +
                    "blocks = VALUES(blocks), " +
                    "plus_minus = VALUES(plus_minus), " +
                    "min = VALUES(min)";


            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Parse e inserisci i dati dalla risposta dell'API
                JSONObject responseDataJson = new JSONObject(responseData);
                JSONArray responseArray = responseDataJson.getJSONArray("response");

                for (int i = 0; i < responseArray.length(); i++) {
                    JSONObject teamData = responseArray.getJSONObject(i);

                    int idTeam = teamData.getJSONObject("team").getInt("id");
                    JSONObject statistics = teamData.getJSONArray("statistics").getJSONObject(0);

                    preparedStatement.setInt(1, idGame);
                    preparedStatement.setInt(2, idTeam);
                    setNullableInt(preparedStatement, 3, "pointsInPaint", statistics);
                    setNullableInt(preparedStatement, 4, "biggestLead", statistics);
                    setNullableInt(preparedStatement, 5, "secondChancePoints", statistics);
                    setNullableInt(preparedStatement, 6, "pointsOffTurnovers", statistics);
                    setNullableInt(preparedStatement, 7, "longestRun", statistics);
                    setNullableInt(preparedStatement, 8, "points", statistics);
                    setNullableInt(preparedStatement, 9, "fgm", statistics);
                    setNullableInt(preparedStatement, 10, "fga", statistics);
                    setNullableString(preparedStatement, 11, "fgp", statistics);
                    setNullableInt(preparedStatement, 12, "ftm", statistics);
                    setNullableInt(preparedStatement, 13, "fta", statistics);
                    setNullableString(preparedStatement, 14, "ftp", statistics);
                    setNullableInt(preparedStatement, 15, "tpm", statistics);
                    setNullableInt(preparedStatement, 16, "tpa", statistics);
                    setNullableString(preparedStatement, 17, "tpp", statistics);
                    setNullableInt(preparedStatement, 18, "offReb", statistics);
                    setNullableInt(preparedStatement, 19, "totReb", statistics);
                    setNullableInt(preparedStatement, 20, "assists", statistics);
                    setNullableInt(preparedStatement, 21, "pFouls", statistics);
                    setNullableFloat(preparedStatement, 22, "steals", statistics);
                    setNullableFloat(preparedStatement, 23, "turnovers", statistics);
                    setNullableFloat(preparedStatement, 24, "blocks", statistics);
                    setNullableString(preparedStatement, 25, "plusMinus", statistics);
                    setNullableString(preparedStatement, 26, "min", statistics);

                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setNullableInt(PreparedStatement preparedStatement, int index, String key, JSONObject jsonObject) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof Number) {
                preparedStatement.setObject(index, ((Number) value).intValue());
            } else {
                preparedStatement.setObject(index, null);
            }
        } else {
            preparedStatement.setObject(index, null);
        }
    }


    private static void setNullableFloat(PreparedStatement preparedStatement, int index, String key, JSONObject jsonObject) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof Number) {
                preparedStatement.setObject(index, ((Number) value).floatValue());
            } else {
                preparedStatement.setObject(index, null);
            }
        } else {
            preparedStatement.setObject(index, null);
        }
    }

    private static void setNullableString(PreparedStatement preparedStatement, int index, String key, JSONObject jsonObject) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof String) {
                preparedStatement.setObject(index, (String) value);
            } else {
                preparedStatement.setObject(index, null);
            }
        } else {
            preparedStatement.setObject(index, null);
        }
    }


}

