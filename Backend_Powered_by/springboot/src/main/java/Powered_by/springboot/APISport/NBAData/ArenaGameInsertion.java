package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ArenaGameInsertion {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/nba";
    private static final String USERNAME = "backend";
    private static final String PASSWORD = "111";

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            List<Integer> seasonYears = getSeasonYearsFromDatabase();

            for (Integer seasonYear : seasonYears) {
                String responseData = apiClient.getData("games/?season=" + seasonYear);
                insertData(responseData);
                System.out.println(responseData);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> getSeasonYearsFromDatabase() throws SQLException {
        // Sostituisci con la tua logica di accesso al database per ottenere gli anni delle stagioni
        List<Integer> data = Collections.singletonList(2023);
        return data;
    }

    private static void insertData(String json) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            JSONArray gamesArray = new JSONObject(json).getJSONArray("response");

            Set<Integer> insertedGames = new HashSet<>();

            String arenaInsertQuery = "INSERT INTO arena (name_arena, city, state, country) VALUES (?, ?, ?, ?)";
            String gameInsertQuery = "INSERT INTO game (id_game, home, visitors, start, end, duration, current_period, " +
                    "total_period, status, arena, season, official) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                    "ON DUPLICATE KEY UPDATE 'start' = VALUE('start')," +
                    " 'end' = VALUE('end'), " +
                    "duration = VALUE(duration), " +
                    "current_period = VALUES(current_period), " +
            "total_period = VALUES(total_period), " +
                    "`status` = VALUES(`status`), " +
                    "official = VALUES(official)";

            try (PreparedStatement arenaStatement = connection.prepareStatement(arenaInsertQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement gameStatement = connection.prepareStatement(gameInsertQuery)) {
                 System.out.println(gameStatement);
                for (int i = 0; i < gamesArray.length(); i++) {
                    JSONObject gameObject = gamesArray.getJSONObject(i);

                    int homeTeamId = getTeamIdFromDatabase(gameObject.getJSONObject("teams").getJSONObject("home").getString("code"));
                    int visitorsTeamId = getTeamIdFromDatabase(gameObject.getJSONObject("teams").getJSONObject("visitors").getString("code"));

                    int statusId = getStatusId(gameObject.getJSONObject("status").getString("long"));

                    int arenaId = getOrCreateArenaId(gameObject.getJSONObject("arena"));

                    int gameId = gameObject.getInt("id");
                    if (!insertedGames.contains(gameId)) {
                        setGameStatementValues(gameStatement, gameObject, homeTeamId, visitorsTeamId, statusId, arenaId);

                        try {
                            gameStatement.executeUpdate();
                            insertedGames.add(gameId);
                        } catch (SQLIntegrityConstraintViolationException e) {
                            System.out.println("Duplicate game: " + gameId);
                            continue;
                        }
                    }
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static int getOrCreateArenaId(JSONObject arenaObject) throws SQLException {
        String arenaName = getStringOrNull(arenaObject, "name", "Default Arena");
        String arenaCity = getStringOrNull(arenaObject, "city", "Default City");
        String arenaState = getStringOrNull(arenaObject, "state", "Default State");
        String arenaCountry = getStringOrNull(arenaObject, "country", "Default Country");

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO arena (name_arena, city, state, country) " +
                             "SELECT ?, ?, ?, ? FROM DUAL " +
                             "WHERE NOT EXISTS (SELECT 1 FROM arena WHERE name_arena = ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, arenaName);
            statement.setString(2, arenaCity);
            statement.setString(3, arenaState);
            statement.setString(4, arenaCountry);
            statement.setString(5, arenaName);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                // Se almeno una riga è stata inserita, recupera l'ID generato automaticamente
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated arena ID.");
                    }
                }
            } else {
                // L'arena esiste già nel database, restituisci l'ID esistente
                return getArenaIdFromDatabase(arenaName);
            }
        }
    }

    private static void setGameStatementValues(PreparedStatement gameStatement, JSONObject gameObject, int homeTeamId, int visitorsTeamId, int statusId, int arenaId) throws SQLException, ParseException {
        gameStatement.setInt(1, gameObject.getInt("id"));
        gameStatement.setInt(2, homeTeamId);
        gameStatement.setInt(3, visitorsTeamId);
        gameStatement.setTimestamp(4, getTimestampOrNull(gameObject.getJSONObject("date").optString("start")));
        gameStatement.setTimestamp(5, getTimestampOrNull(gameObject.getJSONObject("date").optString("end")));
        gameStatement.setObject(6, isValidDuration(gameObject.getJSONObject("date").optString("duration")) ? gameObject.getJSONObject("date").optString("duration") : null);
        gameStatement.setObject(7, gameObject.getJSONObject("periods").optInt("current", 0));
        gameStatement.setObject(8, gameObject.getJSONObject("periods").optInt("total", 0));
        gameStatement.setInt(9, statusId);
        gameStatement.setInt(10, arenaId);
        gameStatement.setInt(11, 9); // Placeholder per l'ID della stagione (modifica se necessario)
        gameStatement.setObject(12, getOfficialsAsString(gameObject.getJSONArray("officials")));
    }

    private static boolean isValidDuration(String duration) {
        if (duration == null || duration.isEmpty()) {
            return false;
        }

        String regexPattern = "\\d{2}:\\d{2}:\\d{2}";
        return duration.matches(regexPattern);
    }


    private static int getStatusId(String statusLong) {
        switch (statusLong) {
            case "Scheduled":
                return 1;
            case "Live":
                return 2;
            case "Finished":
                return 3;
            default:
                return 1;
        }
    }

    private static Timestamp getTimestampOrNull(String dateTime) throws ParseException {
        if (dateTime != null && !dateTime.isEmpty()) {
            return convertToTimestamp(convertToItalianTimeZone(dateTime));
        } else {
            return null;
        }
    }

    private static String getStringOrNull(JSONObject jsonObject, String key, String defaultValue) {
        if (jsonObject.has(key) && jsonObject.get(key) instanceof String) {
            return jsonObject.getString(key);
        } else {
            return defaultValue;
        }
    }

    static int getTeamIdFromDatabase(String teamCode) throws SQLException {
        String query = "SELECT id_team FROM team WHERE code_team = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, teamCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_team");
                }
            }
        }

        return -1;
    }

    private static int getArenaIdFromDatabase(String arenaName) throws SQLException {
        String query = "SELECT id_arena FROM arena WHERE name_arena = ?";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, arenaName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_arena");
                }
            }
        }

        return -1;
    }

    private static Timestamp convertToTimestamp(String dateTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date parsedDate = dateFormat.parse(dateTime);
        return new Timestamp(parsedDate.getTime());
    }
    private static String convertToItalianTimeZone(String dateTime) throws ParseException {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        inputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Rome"));

        // Controllo se l'ora è presente nella stringa di data
        if (!dateTime.contains("T")) {
            // Se non è presente, imposto l'ora su "00:00:00"
            dateTime += "T00:00:00";
        }

        Date parsedDate = inputDateFormat.parse(dateTime);
        return outputDateFormat.format(parsedDate);
    }



    private static String getOfficialsAsString(JSONArray officials) {
        if (officials != null) {
            return officials.toString();
        } else {
            return "";
        }
    }
}
