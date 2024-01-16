package Powered_by.springboot.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@Setter
public class GameDayResponse {

    private int gameId;
    private LocalDateTime start;
    private String homeTeamName;
    private String homeTeamLogo;
    private String homeColour;
    private int p1Home;
    private int p2Home;
    private int p3Home;
    private int p4Home;
    private int p5Home;
    private String visitorTeamName;
    private String visitorTeamLogo;
    private String visitorsColour;
    private int p1Visitor;
    private int p2Visitor;
    private int p3Visitor;
    private int p4Visitor;
    private int p5Visitor;
    private String risultato;
    // No-argument constructor
    public GameDayResponse() {
    }

    // Constructors, getters, and setters
    // ...

    // Costruttore che prende i punteggi come argomenti
    public GameDayResponse(int gameId, LocalDateTime start, String homeTeamName, String homeTeamLogo,String homeColour ,
                           int p1Home, int p2Home, int p3Home, int p4Home, int p5Home,
                           String visitorTeamName, String visitorTeamLogo, String visitorsColour,
                           int p1Visitor, int p2Visitor, int p3Visitor, int p4Visitor, int p5Visitor) {
        // Inizializza i campi con i valori forniti
        this.gameId = gameId;
        this.start = start;
        this.homeTeamName = homeTeamName;
        this.homeTeamLogo = homeTeamLogo;
        this.homeColour = homeColour;
        this.p1Home = p1Home;
        this.p2Home = p2Home;
        this.p3Home = p3Home;
        this.p4Home = p4Home;
        this.p5Home = p5Home;
        this.visitorTeamName = visitorTeamName;
        this.visitorTeamLogo = visitorTeamLogo;
        this.visitorsColour = visitorsColour;
        this.p1Visitor = p1Visitor;
        this.p2Visitor = p2Visitor;
        this.p3Visitor = p3Visitor;
        this.p4Visitor = p4Visitor;
        this.p5Visitor = p5Visitor;

        // Calcola e imposta il risultato utilizzando il metodo getRisultato
        this.risultato = getRisultato();
    }



    // Getter per il campo risultato
    public String getRisultato() {
       int pHome =  this.p1Home+  this.p2Home+  this.p3Home+  this.p4Home + this.p5Home ;
        int pVisitors = this.p1Visitor+  this.p2Visitor+  this.p3Visitor+  this.p4Visitor+ this.p5Visitor ;
        String stringaPHome = String.valueOf(pHome);
        String stringaPVisitors = String.valueOf(pVisitors);
        return  stringaPHome + " - " + stringaPVisitors ;
    }
}