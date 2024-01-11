package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatsInsertion {

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            // Get all team IDs from the database
            List<Integer> teamIds = getTeamIdsFromDatabase(6, 10);

            // Get all season years from the database
            List<Integer> seasonYears = getSeasonYearsFromDatabase();

            // Iterate through teams and seasons
            for (Integer teamId : teamIds) {
                for (Integer seasonYear : seasonYears) {
                    // Make API call for player statistics
                    String responseData = apiClient.getData("players/statistics?team=" + teamId + "&season=" + seasonYear);
                    System.out.println(responseData);

                    // Parse JSON and insert player statistics into the database
                    insertPlayerStatistics(teamId, seasonYear, responseData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getTeamIdsFromDatabase(int offset, int batchSize) {
        List<Integer> teamIds = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Use OFFSET and LIMIT to get a batch of team IDs
            String query = "SELECT id_team FROM team LIMIT ? OFFSET ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, batchSize);
                preparedStatement.setInt(2, offset);

                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int teamId = resultSet.getInt("id_team");
                    teamIds.add(teamId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teamIds;
    }

    private static List<Integer> getSeasonYearsFromDatabase() {
        // Implement the logic to get season years from the database
        // ...
        List<Integer> data = Collections.singletonList(2022); // Example, replace with your database access logic
        return data;
    }

    private static void insertPlayerStatistics(int teamId, int seasonYear, String json) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            JSONArray statisticsArray = new JSONObject(json).getJSONArray("response");

            String insertQuery = "INSERT INTO player_stats (id_player, id_team, id_game, points, position, " +
                    "minutes, field_goals_made, field_goals_attempted, field_goal_percentage, free_throws_made, " +
                    "free_throws_attempted, free_throw_percentage, three_pointers_made, three_pointers_attempted, " +
                    "three_point_percentage, offensive_rebounds, defensive_rebounds, total_rebounds, assist, " +
                    "personal_fouls, steals, turnovers, blocks, plus_minus) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (int i = 0; i < statisticsArray.length(); i++) {
                    JSONObject playerStatsObject = statisticsArray.getJSONObject(i);

                    int idPlayer = playerStatsObject.getJSONObject("player").getInt("id");
                    int idGame = playerStatsObject.getJSONObject("game").getInt("id");

                    // Check if the record already exists
                    if (isGameStatsRecordExists(connection, teamId, idPlayer, idGame)) {
                        System.out.println("Skipped duplicate record for team ID: " + teamId + ", season year: " + seasonYear);
                        continue;
                    }

                    preparedStatement.setInt(1, idPlayer);
                    preparedStatement.setInt(2, teamId);
                    preparedStatement.setInt(3, idGame);
                    setNullableIntVariable(preparedStatement, 4, playerStatsObject.optInt("points", -1));
                    setNullableString(preparedStatement, 5, "pos", playerStatsObject);
                    setNullableString(preparedStatement, 6, "min", playerStatsObject);
                    setNullableIntVariable(preparedStatement, 7, playerStatsObject.optInt("fgm", -1));
                    setNullableIntVariable(preparedStatement, 8, playerStatsObject.optInt("fga", -1));
                    setNullableFloatVariable(preparedStatement, 9, playerStatsObject.optDouble("fgp", -1));
                    setNullableIntVariable(preparedStatement, 10, playerStatsObject.optInt("ftm", -1));
                    setNullableIntVariable(preparedStatement, 11, playerStatsObject.optInt("fta", -1));
                    setNullableFloatVariable(preparedStatement, 12, playerStatsObject.optDouble("ftp", -1));
                    setNullableIntVariable(preparedStatement, 13, playerStatsObject.optInt("tpm", -1));
                    setNullableIntVariable(preparedStatement, 14, playerStatsObject.optInt("tpa", -1));
                    setNullableFloatVariable(preparedStatement, 15, playerStatsObject.optDouble("tpp", -1));
                    setNullableIntVariable(preparedStatement, 16, playerStatsObject.optInt("offReb", -1));
                    setNullableIntVariable(preparedStatement, 17, playerStatsObject.optInt("defReb", -1));
                    setNullableIntVariable(preparedStatement, 18, playerStatsObject.optInt("totReb", -1));
                    setNullableIntVariable(preparedStatement, 19, playerStatsObject.optInt("assists", -1));
                    setNullableIntVariable(preparedStatement, 20, playerStatsObject.optInt("pFouls", -1));
                    setNullableIntVariable(preparedStatement, 21, playerStatsObject.optInt("steals", -1));
                    setNullableIntVariable(preparedStatement, 22, playerStatsObject.optInt("turnovers", -1));
                    setNullableIntVariable(preparedStatement, 23, playerStatsObject.optInt("blocks", -1));
                    setNullableString(preparedStatement, 24, "plusMinus", playerStatsObject);

                    preparedStatement.executeUpdate();
                    System.out.println("Inserted game for player: " + idPlayer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getPlayerId(Connection connection, JSONObject playerObject) throws SQLException {
        int playerId = playerObject.getInt("id");

        // Controlla se il giocatore esiste già nella tabella player
        if (!isPlayerExists(connection, playerId)) {
            // Se il giocatore non esiste, inseriscilo nella tabella player
            insertPlayer(connection, playerId, playerObject.getString("firstname"), playerObject.getString("lastname"));
        }

        return playerId;
    }

    private static boolean isPlayerExists(Connection connection, int playerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM player WHERE id_player = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;  // Restituisce true se il giocatore esiste già
            }
        }
        return false;  // Restituisce false in caso di errore o se il giocatore non esiste
    }

    private static void insertPlayer(Connection connection, int playerId, String firstName, String lastName) throws SQLException {
        String insertQuery = "INSERT INTO player (id_player, firstname, lastname) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);

            preparedStatement.executeUpdate();
            System.out.println("Inserted player with ID: " + playerId);
        }
    }

    private static boolean isGameStatsRecordExists(Connection connection, int teamId, int idPlayer, int idGame) {
        String query = "SELECT COUNT(*) FROM player_stats WHERE id_team = ? AND id_player = ? AND id_game = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);
            preparedStatement.setInt(2, idPlayer);
            preparedStatement.setInt(3, idGame);

            // Print the SQL query for debugging
            System.out.println("Generated SQL Query: " + preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;  // Returns true if the record already exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Default to false if there's an error
    }


    private static void setNullableIntVariable(PreparedStatement preparedStatement, int index, int value) throws SQLException {
        if (value >= 0) {
            preparedStatement.setInt(index, value);
        } else {
            preparedStatement.setNull(index, Types.INTEGER);
        }
    }

    private static void setNullableFloatVariable(PreparedStatement preparedStatement, int index, double value) throws SQLException {
        if (value >= 0) {
            preparedStatement.setDouble(index, value);
        } else {
            preparedStatement.setNull(index, Types.FLOAT);
        }
    }

    private static void setNullableString(PreparedStatement preparedStatement, int index, String key, JSONObject jsonObject) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof String) {
                preparedStatement.setString(index, (String) value);
            } else {
                preparedStatement.setNull(index, Types.VARCHAR);
            }
        } else {
            preparedStatement.setNull(index, Types.VARCHAR);
        }
    }
}
