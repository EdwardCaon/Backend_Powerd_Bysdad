package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;

import org.json.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TeamInsertion {

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            String responseData = apiClient.getData("teams");
            insertTeamsIntoDatabase(responseData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertTeamsIntoDatabase(String json) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            JSONArray teamsArray = new JSONObject(json).getJSONArray("response");
            String insertQuery = "INSERT INTO team (id_team, id_league, name_team, nickname, code_team, city, logo, allStar, nbaFranchise) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (int i = 0; i < teamsArray.length(); i++) {
                    JSONObject teamObject = teamsArray.getJSONObject(i);

                    int idTeam = teamObject.getInt("id");
                    preparedStatement.setInt(1, idTeam);

                    int idLeague = getLeagueId(teamObject);
                    preparedStatement.setInt(2, idLeague);
                    preparedStatement.setString(3, teamObject.getString("name"));
                    preparedStatement.setString(4, teamObject.getString("nickname"));
                    preparedStatement.setString(5, teamObject.getString("code"));

                    // Verifica se la chiave "city" è presente e imposta il valore di conseguenza
                    if (teamObject.isNull("city")) {
                        preparedStatement.setNull(6, java.sql.Types.VARCHAR);
                    } else {
                        preparedStatement.setString(6, teamObject.getString("city"));
                    }

                    // Verifica se la chiave "logo" è null prima di impostarla
                    if (teamObject.isNull("logo")) {
                        preparedStatement.setNull(7, java.sql.Types.VARCHAR);
                    } else {
                        preparedStatement.setString(7, teamObject.getString("logo"));
                    }

                    preparedStatement.setInt(8, teamObject.getBoolean("allStar") ? 1 : 0);
                    preparedStatement.setInt(9, teamObject.getBoolean("nbaFranchise") ? 1 : 0);

                    preparedStatement.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getLeagueId(JSONObject teamObject) {
        int idLeague = 4; // Default value for "Others"
        if (teamObject.has("leagues") && teamObject.getJSONObject("leagues").has("standard")) {
            JSONObject standardLeague = teamObject.getJSONObject("leagues").getJSONObject("standard");

            if (standardLeague.has("conference") && standardLeague.get("conference") instanceof String) {
                String conference = standardLeague.getString("conference");
                if ("East".equals(conference)) {
                    idLeague = 1;
                } else if ("West".equals(conference)) {
                    idLeague = 2;
                } else if ("Intl".equals(conference) || "Internatio".equals(conference)) {
                    idLeague = 3;
                }
            }
        }
        return idLeague;
    }

}
