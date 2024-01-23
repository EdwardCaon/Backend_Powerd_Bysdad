package Powered_by.springboot.APISport.NBAData;
import Powered_by.springboot.APISport.APIClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.Collections;
import java.util.List;

@Component
public class TeamStandingsInsertion {

    // Definisci le tue variabili di connessione al database
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/nba";
    private static final String username = "backend";
    private static final String password = "111";

    @Scheduled(cron = "0 5 4 * * *") // Esegui alle 4:5 ogni giorno
    public void teamStandingsEastInsertionAtScheduledTime() {
        processStandingsDataForConference("East");
    }

    @Scheduled(cron = "0 7 4 * * *") // Esegui alle 4:7 ogni giorno
    public void teamStandingsWestInsertionAtScheduledTime() {
        processStandingsDataForConference("West");
    }

    private void processStandingsDataForConference(String conference) {
        APIClient apiClient = new APIClient();

        try {
            List<Integer> seasonYears = getSeasonYearsFromDatabase();

            System.out.println("Aggiornamento classifica " + conference + " in corso");


            for (Integer seasonYear : seasonYears) {
                String standingsResponse = apiClient.getData("standings?league=standard&season=" + seasonYear + "&conference=" + conference);
                processStandingsData(standingsResponse, seasonYear);
                System.out.println("Aggiornamento classifica " + conference + " termitano con successo");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processStandingsData(String standingsJson, int seasonYear) {
        try {
            JSONArray standingsArray = new JSONObject(standingsJson).getJSONArray("response");

            for (int i = 0; i < standingsArray.length(); i++) {
                JSONObject standingsObject = standingsArray.getJSONObject(i);
                if (standingsObject.has("team") && standingsObject.has("win")) {
                    int teamId = standingsObject.getJSONObject("team").getInt("id");
                    int rank = standingsObject.getJSONObject("conference").getInt("rank");
                    int win = standingsObject.getJSONObject("win").getInt("total");
                    int lose = standingsObject.getJSONObject("loss").getInt("total");
                    double winPercentage = Double.parseDouble(standingsObject.getJSONObject("win").getString("percentage"));

                    insertOrUpdateStandingsInDatabase(teamId, seasonYear, rank, win, lose, winPercentage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertOrUpdateStandingsInDatabase(int teamId, int seasonYear, int rank, int win, int lose, double winPercentage) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "UPDATE team_statistic SET rank=?, win=?, lose=?, win_percentage=? " +
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

    private List<Integer> getSeasonYearsFromDatabase() {
        List<Integer> data = Collections.singletonList(2023);
        return data;
    }

    private int determineDbSeason(int apiSeasonYear) {
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
                dbSeason = -1;
        }
        return dbSeason;
    }
}
