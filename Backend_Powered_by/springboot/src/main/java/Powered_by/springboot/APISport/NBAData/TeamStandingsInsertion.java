package Powered_by.springboot.APISport.NBAData;

import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class TeamStandingsInsertion {

    // Definisci le tue variabili di connessione al database
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
    private static final String username = "backend";
    private static final String password = "111";

    public static void main(String[] args) {
        APIClient apiClient = new APIClient();

        try {
            // Ottieni tutti gli anni delle stagioni dal database
            List<Integer> seasonYears = getSeasonYearsFromDatabase();

            // Itera su tutte le stagioni
            for (Integer seasonYear : seasonYears) {
                // Effettua una chiamata per ottenere i dati degli standings
                String standingsResponse = apiClient.getData("standings?league=standard&season=" + seasonYear + "&conference=East");

                // Processa i dati degli standings e inseriscili o aggiornali nel database
                processStandingsData(standingsResponse, seasonYear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processStandingsData(String standingsJson, int seasonYear) {
        try {
            JSONArray standingsArray = new JSONObject(standingsJson).getJSONArray("response");

            for (int i = 0; i < standingsArray.length(); i++) {
                JSONObject standingsObject = standingsArray.getJSONObject(i);
                if (standingsObject.has("team") && standingsObject.has("win")) {
                    // Ottieni i dati specifici per ogni team dalla risposta API
                    int teamId = standingsObject.getJSONObject("team").getInt("id");
                    int rank = standingsObject.getJSONObject("conference").getInt("rank");
                    int win = standingsObject.getJSONObject("win").getInt("total");
                    int lose = standingsObject.getJSONObject("loss").getInt("total");
                    double winPercentage = Double.parseDouble(standingsObject.getJSONObject("win").getString("percentage"));

                    // Inserisci o aggiorna i dati degli standings nel database
                    insertOrUpdateStandingsInDatabase(teamId, seasonYear, rank, win, lose, winPercentage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertOrUpdateStandingsInDatabase(int teamId, int seasonYear, int rank, int win, int lose, double winPercentage) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "UPDATE team_statistic SET rank=?, win=?, lose=?, winPercentage=? " +
                    "WHERE id_team=? AND id_season=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, rank);
                preparedStatement.setInt(2, win);
                preparedStatement.setInt(3, lose);
                preparedStatement.setDouble(4, winPercentage);

                preparedStatement.setInt(5, teamId);
                preparedStatement.setInt(6, determineDbSeason(seasonYear));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Integer> getSeasonYearsFromDatabase() {
        // Implementa la logica per ottenere gli anni delle stagioni dal database
        // ...
        List<Integer> data = Collections.singletonList(2022); // Esempio, sostituisci con la tua logica di accesso al database
        return data;
    }

    private static int determineDbSeason(int apiSeasonYear) {
        // Mappa l'anno della stagione API all'anno della stagione nel tuo database
        // Modifica questa logica in base ai tuoi requisiti
        int dbSeason;
        switch (apiSeasonYear) {
            case 2023:
                dbSeason = 9;
                break;
            case 2022:
                dbSeason = 8;
                break;
            case 2021:
                dbSeason = 7;
                break;
            default:
                // Gestisci altri casi se necessario
                dbSeason = -1;
        }
        return dbSeason;
    }
}
