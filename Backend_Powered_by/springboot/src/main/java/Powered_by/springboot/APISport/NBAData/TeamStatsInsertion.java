package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeamStatsInsertion {

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            // Ottenere tutti gli id delle squadre dal database
            // offset parto da 0 , ... 10 ,  ... 20 ,...  30 , ... 40, ... 50 , ... 60
            // batchsize: 10 chiamate ogni 10 minuti
            List<Integer> teamIds = getTeamIdsFromDatabase(60, 10);

            // Ottenere tutti gli anni delle stagioni dal database
            List<Integer> seasonYears = getSeasonYearsFromDatabase();

            // Iterare su tutte le squadre e stagioni
            for (Integer teamId : teamIds) {
                for (Integer seasonYear : seasonYears) {
                    // Ottenere le statistiche per la squadra e la stagione correnti
                    String responseData = apiClient.getData("teams/statistics?id=" + teamId + "&season=" + seasonYear);

                    // Pulire il JSON e inserire le statistiche nel database
                    insertTeamStatistics(teamId, seasonYear, responseData);
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
            // Utilizza OFFSET e LIMIT per ottenere un batch di ID del team
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
        // Implementa la logica per ottenere gli anni delle stagioni dal database
        // ...
        List<Integer> data = Collections.singletonList(2022); // Esempio, sostituisci con la tua logica di accesso al database
        return data;
    }

    private static void insertTeamStatistics(int teamId, int seasonYear, String json) {
        // Implementa la logica per l'inserimento delle statistiche nel database
        // ...

        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            JSONArray statisticsArray = new JSONObject(json).getJSONArray("response");

            String insertQuery = "INSERT INTO team_statistic (id_team, id_season, games_played, fast_break_points, " +
                    "points_in_paint, biggest_lead, second_chance_points, points_off_turnovers, longest_run, points, " +
                    "field_goals_made, field_goals_attempted, field_goal_percentage, free_throws_made, free_throws_attempted, " +
                    "free_throw_percentage, three_pointers_made, three_pointers_attempted, three_point_percentage, " +
                    "offensive_rebounds, defensive_rebounds, total_rebounds, assist, personal_fouls, steals, turnovers, " +
                    "blocks, plus_minus) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (int i = 0; i < statisticsArray.length(); i++) {
                    JSONObject statisticsObject = statisticsArray.getJSONObject(i);

                    preparedStatement.setInt(1, teamId);
                    preparedStatement.setInt(2, 8);
                    preparedStatement.setInt(3, statisticsObject.getInt("games"));
                    preparedStatement.setInt(4, statisticsObject.getInt("fastBreakPoints"));
                    preparedStatement.setInt(5, statisticsObject.getInt("pointsInPaint"));
                    preparedStatement.setInt(6, statisticsObject.getInt("biggestLead"));
                    preparedStatement.setInt(7, statisticsObject.getInt("secondChancePoints"));
                    preparedStatement.setInt(8, statisticsObject.getInt("pointsOffTurnovers"));
                    preparedStatement.setInt(9, statisticsObject.getInt("longestRun"));
                    preparedStatement.setInt(10, statisticsObject.getInt("points"));
                    preparedStatement.setInt(11, statisticsObject.getInt("fgm"));
                    preparedStatement.setInt(12, statisticsObject.getInt("fga"));
                    preparedStatement.setDouble(13, statisticsObject.getDouble("fgp"));
                    preparedStatement.setInt(14, statisticsObject.getInt("ftm"));
                    preparedStatement.setInt(15, statisticsObject.getInt("fta"));
                    preparedStatement.setDouble(16, statisticsObject.getDouble("ftp"));
                    preparedStatement.setInt(17, statisticsObject.getInt("tpm"));
                    preparedStatement.setInt(18, statisticsObject.getInt("tpa"));
                    preparedStatement.setDouble(19, statisticsObject.getDouble("tpp"));
                    preparedStatement.setInt(20, statisticsObject.getInt("offReb"));
                    preparedStatement.setInt(21, statisticsObject.getInt("defReb"));
                    preparedStatement.setInt(22, statisticsObject.getInt("totReb"));
                    preparedStatement.setInt(23, statisticsObject.getInt("assists"));
                    preparedStatement.setInt(24, statisticsObject.getInt("pFouls"));
                    preparedStatement.setInt(25, statisticsObject.getInt("steals"));
                    preparedStatement.setInt(26, statisticsObject.getInt("turnovers"));
                    preparedStatement.setInt(27, statisticsObject.getInt("blocks"));
                    preparedStatement.setInt(28, statisticsObject.getInt("plusMinus"));

                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
