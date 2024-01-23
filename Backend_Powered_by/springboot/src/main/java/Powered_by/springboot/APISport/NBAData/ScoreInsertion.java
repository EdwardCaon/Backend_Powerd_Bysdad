package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.List;

public class ScoreInsertion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nba";
    private static final String USERNAME = "backend";
    private static final String PASSWORD = "111";

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            List<Integer> seasonYears = ArenaGameInsertion.getSeasonYearsFromDatabase();

            for (Integer seasonYear : seasonYears) {
                String responseData = apiClient.getData("games/?season=" + seasonYear);
                insertScores(responseData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertScores(String json) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            JSONArray gamesArray = new JSONObject(json).getJSONArray("response");
            String scoreInsertQuery = "INSERT INTO score (id_game, id_team, total_win, total_lose, series_win, series_loss, p1, p2, p3, p4, p5, total_points) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    "ON DUPLICATE KEY UPDATE total_win = VALUES(total_win)," +
                    " total_lose = VALUES(total_lose)," +
                    " series_win = VALUES(series_win)," +
                    " series_loss = VALUES(series_loss)," +
                    " p1 = VALUES(p1)," +
                    " p2 = VALUES(p2)," +
                    " p3 = VALUES(p3)," +
                    " p4 = VALUES(p4)," +
                    " p5 = VALUES(p5)," +
                    " total_points = VALUES(total_points)";


            try (PreparedStatement scoreStatement = connection.prepareStatement(scoreInsertQuery)) {
                for (int i = 0; i < gamesArray.length(); i++) {
                    JSONObject gameObject = gamesArray.getJSONObject(i);
                    int gameId = gameObject.getInt("id");

                    JSONObject teams = gameObject.getJSONObject("teams");
                    insertScoreForTeam(connection, scoreStatement, gameId, gameObject.getJSONObject("scores").getJSONObject("visitors"), teams.getJSONObject("visitors"));
                    insertScoreForTeam(connection, scoreStatement, gameId, gameObject.getJSONObject("scores").getJSONObject("home"), teams.getJSONObject("home"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertScoreForTeam(Connection connection, PreparedStatement scoreStatement, int gameId, JSONObject teamScores, JSONObject teamInfo) throws SQLException {
        int teamId = teamInfo.getInt("id");


        int totalWin = teamScores.has("win") && !teamScores.isNull("win") ? teamScores.getInt("win") : 0;
        int totalLose = teamScores.has("loss") && !teamScores.isNull("loss") ? teamScores.getInt("loss") : 0;

        JSONObject series = teamScores.getJSONObject("series");
        int seriesWin = series.has("win") && !series.isNull("win") ? series.getInt("win") : 0;
        int seriesLoss = series.has("loss") && !series.isNull("loss") ? series.getInt("loss") : 0;

        JSONArray lineScore = teamScores.getJSONArray("linescore");
        int p1 = lineScore.length() > 0 ? parseScore(lineScore.getString(0)) : 0;
        int p2 = lineScore.length() > 1 ? parseScore(lineScore.getString(1)) : 0;
        int p3 = lineScore.length() > 2 ? parseScore(lineScore.getString(2)) : 0;
        int p4 = lineScore.length() > 3 ? parseScore(lineScore.getString(3)) : 0;
        int p5 = lineScore.length() > 4 ? parseScore(lineScore.getString(4)) : 0;

        int totalPoints = teamScores.has("points") && !teamScores.isNull("points") ? teamScores.getInt("points") : 0;

        scoreStatement.setInt(1, gameId);
        scoreStatement.setInt(2, teamId);
        scoreStatement.setInt(3, totalWin);
        scoreStatement.setInt(4, totalLose);
        scoreStatement.setInt(5, seriesWin);
        scoreStatement.setInt(6, seriesLoss);
        scoreStatement.setInt(7, p1);
        scoreStatement.setInt(8, p2);
        scoreStatement.setInt(9, p3);
        scoreStatement.setInt(10, p4);
        scoreStatement.setInt(11, p5);
        scoreStatement.setInt(12, totalPoints);

        scoreStatement.executeUpdate();
    }

    private static int parseScore(String score) {
        return score.isEmpty() ? 0 : Integer.parseInt(score);
    }

    private static boolean isScoreExists(Connection connection, int gameId, int teamId) throws SQLException {
        String query = "SELECT COUNT(*) FROM score WHERE id_game = ? AND id_team = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, gameId);
            statement.setInt(2, teamId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

}
