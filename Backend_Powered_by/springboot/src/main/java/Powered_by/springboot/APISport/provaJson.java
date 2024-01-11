package Powered_by.springboot.APISport;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class provaJson {

    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query ="SELECT " +
                    "    g.id_game AS Game, " +
                    "    g.start, " +
                    "    t_home.logo AS 'Logo Home', " +
                    "    t_home.name_team AS 'Nome Home', " +
                    "    s_home.total_points AS 'Home Points', " +
                    "    t_visitors.logo AS 'Logo Visitors', " +
                    "    t_visitors.name_team AS 'Nome Visitors', " +
                    "    s_visitors.total_points AS 'Visitors Points' " +
                    "FROM " +
                    "    game AS g " +
                    "JOIN " +
                    "    team AS t_home ON t_home.id_team = g.home " +
                    "JOIN " +
                    "    score AS s_home ON s_home.id_team = g.home " +
                    "JOIN " +
                    "    team AS t_visitors ON t_visitors.id_team = g.visitors " +
                    "JOIN " +
                    "    score AS s_visitors ON s_visitors.id_team = g.visitors " +
                    "WHERE " +
                    "    g.start >= '2021-10-20 00:00:00' AND g.start < '2021-10-21 00:00:00' " +
                    "GROUP BY " +
                    "    g.id_game";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                List<JSONObject> resultList = new ArrayList<>();

                while (resultSet.next()) {
                    JSONObject jsonResult = new JSONObject();
                    jsonResult.put("id_game", resultSet.getString("Game"));
                    jsonResult.put("logo_home", resultSet.getString("Logo Home"));
                    jsonResult.put("name_team_home", resultSet.getString("name_team"));
                    jsonResult.put("total_point_home", resultSet.getString("total_points"));
                    jsonResult.put("logo_visitors", resultSet.getString("Logo Visitors"));
                    jsonResult.put("name_team_visitors", resultSet.getString("name_team"));
                    jsonResult.put("total_points_visitors", resultSet.getString("total_points"));
                    jsonResult.put("start", resultSet.getString("start"));

                    resultList.add(jsonResult);
                }

                // Write the JSON array to a file
                try (FileWriter fileWriter = new FileWriter("output.json")) {
                    JSONArray jsonArray = new JSONArray(resultList);
                    fileWriter.write(jsonArray.toString());
                }

                System.out.println("Query result has been written to output.json.");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
