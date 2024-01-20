package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerInsertion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nba";
    private static final String USERNAME = "backend";
    private static final String PASSWORD = "111";

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            // Ottenere tutti gli id delle squadre dal database
            // offset parto da 0 , ... 10 ,  ... 20 ,...  30 , ... 40, ... 50 , ... 60
            // batchsize: 10 chiamate ogni 10 minuti
            List<Integer> teamIds = getTeamIdsFromDatabase(30, 10);

            List<Integer> seasonIds = getSeasonIdsFromDatabase(); // Implement this method to get season IDs from your database

            for (Integer seasonId : seasonIds) {
                for (Integer teamId : teamIds) {
                    String responseData = apiClient.getData("players/?season=" + seasonId + "&team=" + teamId);
                    insertPlayers(responseData, teamId, seasonId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertPlayers(String json, int teamId, int seasonId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            JSONArray playersArray = new JSONObject(json).getJSONArray("response");

            String playerInsertQuery = "INSERT INTO player (id_player, firstname, lastname, date_of_birthday, country, started_year, pro, height, weight, college, affiliation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE" +
                    "  firstname = VALUES(firstname)," +
                    "  lastname = VALUES(lastname)," +
                    "  date_of_birthday = VALUES(date_of_birthday)," +
                    "  country = VALUES(country)," +
                    "  started_year = VALUES(started_year)," +
                    "  pro = VALUES(pro)," +
                    "  height = VALUES(height)," +
                    "  weight = VALUES(weight)," +
                    "  college = VALUES(college)," +
                    "  affiliation = VALUES(affiliation);";

            try (PreparedStatement playerStatement = connection.prepareStatement(playerInsertQuery)) {
                for (int i = 0; i < playersArray.length(); i++) {
                    JSONObject playerObject = playersArray.getJSONObject(i);
                    int playerId = playerObject.getInt("id");

                    /* Verifica preliminare se il giocatore esiste già
                    if (isPlayerExists(connection, playerId)) {
                        System.out.println("Player already exists for playerId: " + playerId + ". Skipping insert.");
                        continue;
                    }*/

                    // Esegui l'inserimento del giocatore
                    insertPlayer(connection, playerStatement, playerObject, teamId, seasonId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Integer getIdCountry(String country) {
        Integer idCountry = null;

        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Utilize a prepared statement to avoid SQL injection
            String query = "SELECT id_country FROM Country WHERE name_country = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, country);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    idCountry = resultSet.getInt("id_country");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Restituisci 0 se il paese non è trovato
        return (idCountry != null) ? idCountry : 0;
    }

    private static void insertPlayer(Connection connection, PreparedStatement playerStatement, JSONObject playerObject, int teamId, int seasonId) throws SQLException {
        playerStatement.setInt(1, playerObject.getInt("id"));
        playerStatement.setString(2, playerObject.getString("firstname"));
        playerStatement.setString(3, playerObject.getString("lastname"));

        JSONObject birthObject = playerObject.getJSONObject("birth");
        String dateOfBirth = birthObject.optString("date", null);
        playerStatement.setDate(4, (dateOfBirth != null) ? Date.valueOf(dateOfBirth) : null);

        // Aggiunta di country
        String countryName = birthObject.optString("country", null);
        // Get the id_country using the getIdCountry method
        Integer idCountry = getIdCountry(countryName);
        // Set the id_country in the playerStatement
        setNullableIntNON(playerStatement, 5, idCountry);
        setNullableInt(playerStatement, 6, playerObject.getJSONObject("nba"), "start");
        setNullableInt(playerStatement, 7, playerObject.getJSONObject("nba"), "pro");
        setNullableDouble(playerStatement, 8, playerObject.getJSONObject("height"), "meters");
        setNullableDouble(playerStatement, 9, playerObject.getJSONObject("weight"), "kilograms");
        setNullableString(playerStatement, 10, playerObject, "college");
        setNullableString(playerStatement, 11, playerObject, "affiliation");

        playerStatement.executeUpdate();

        // Esegui l'inserimento dei dettagli del giocatore nella tabella 'player_details'
        insertPlayerDetails(connection, playerObject.getInt("id"), teamId, playerObject);
    }
    private static void setNullableIntNON(PreparedStatement statement, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            statement.setInt(parameterIndex, value);
        } else {
            // Set the default value (in this case, 0)
            statement.setInt(parameterIndex, 0);
        }
    }

    // Metodi di supporto per impostare i valori nullable nei PreparedStatement

    private static void setNullableString(PreparedStatement statement, int index, JSONObject jsonObject, String key) throws SQLException {
        if (jsonObject != null && jsonObject.has(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof String) {
                statement.setString(index, (String) value);
            } else {
                // Se il valore non è una stringa, puoi gestire la situazione di conseguenza
                statement.setNull(index, Types.VARCHAR);
                System.out.println("Warning: Value associated with key '" + key + "' is not a string. Setting NULL in the database.");
            }
        } else {
            statement.setNull(index, Types.VARCHAR);
        }
    }


    private static void setNullableInt(PreparedStatement statement, int index, JSONObject jsonObject, String key) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            Object value = jsonObject.get(key);
            if (value instanceof Integer) {
                statement.setInt(index, (Integer) value);
            } else if (value instanceof String && !((String) value).isEmpty()) {
                statement.setInt(index, Integer.parseInt((String) value));
            } else {
                // Se il valore non è un intero o una stringa vuota, puoi gestire la situazione di conseguenza
                statement.setNull(index, Types.INTEGER);
                System.out.println("Warning: Value associated with key '" + key + "' is not a valid integer. Setting NULL in the database.");
            }
        } else {
            statement.setNull(index, Types.INTEGER);
        }
    }


    private static void setNullableDouble(PreparedStatement statement, int index, JSONObject jsonObject, String key) throws SQLException {
        if (jsonObject.has(key) && !jsonObject.isNull(key)) {
            statement.setDouble(index, jsonObject.getDouble(key));
        } else {
            statement.setNull(index, Types.DOUBLE);
        }
    }

    private static void insertPlayerDetails(Connection connection, int playerId, int teamId, JSONObject playerObject) throws SQLException {
        String playerDetailsInsertQuery = "INSERT INTO player_details (id_player, id_team, id_season, pos, active, shirt_number) " +
                "VALUES (?, ?, ?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE" +
                "  id_team = VALUES(id_team)," +
                "  id_season = VALUES(id_season)," +
                "  pos = VALUES(pos)," +
                "  active = VALUES(active)," +
                "  shirt_number = VALUES(shirt_number); ";

        try (PreparedStatement playerDetailsStatement = connection.prepareStatement(playerDetailsInsertQuery)) {
            JSONObject leaguesObject = playerObject.optJSONObject("leagues");
            JSONObject standardObject = (leaguesObject != null) ? leaguesObject.optJSONObject("standard") : null;

            playerDetailsStatement.setInt(1, playerId);
            playerDetailsStatement.setInt(2, teamId);
            playerDetailsStatement.setInt(3, 9); // Assuming a default value for season
            setNullableString(playerDetailsStatement, 4, standardObject, "pos");

            if (standardObject != null) {
                playerDetailsStatement.setBoolean(5, standardObject.optBoolean("active"));
                playerDetailsStatement.setInt(6, standardObject.optInt("jersey", 0));
            } else {
                // Se la chiave "standard" non è presente, imposta i valori a null o a un valore predefinito
                playerDetailsStatement.setNull(5, Types.BOOLEAN);
                playerDetailsStatement.setNull(6, Types.INTEGER);
            }

            playerDetailsStatement.executeUpdate();
        }
    }



    private static boolean isPlayerExists(Connection connection, int playerId) throws SQLException {
        String query = "SELECT COUNT(*) FROM player WHERE id_player = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count > 0;
            }
        }
    }

    private static List<Integer> getTeamIdsFromDatabase(int offset, int batchSize) {
        List<Integer> teamIds = new ArrayList<>();

        String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
        String username = "backend";
        String password = "111";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Utilizza OFFSET e LIMIT per ottenere un batch di ID del team
            String query = "SELECT id_team FROM team WHERE id_league = 1 or id_league = 2 ORDER BY `id_team` ASC LIMIT ? OFFSET ?";
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


    private static List<Integer> getSeasonIdsFromDatabase() {
        // Sostituisci con la tua logica di accesso al database per ottenere gli anni delle stagioni
        List<Integer> data = Collections.singletonList(2023);
        return data;
    }
}
