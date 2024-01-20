package Powered_by.springboot.controller;

import Powered_by.springboot.entity.Team;
import Powered_by.springboot.payload.response.PlayerStatsByIdPlayerResponse;
import Powered_by.springboot.payload.response.TeamClassificaResponse;
import Powered_by.springboot.payload.response.TeamSeasonResponse;
import Powered_by.springboot.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class TeamController {

    private final TeamService teamService;


    @GetMapping("/all")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @GetMapping("/classifica/{season}/{idLeague}")
    public ResponseEntity<?> countWinsByTeamAndSeason(@PathVariable int season,  @PathVariable String idLeague) {
        return teamService.getClassifica(season,  idLeague);
    }

    @GetMapping("/season/{id_team}/{season}")
    public ResponseEntity<?> getPlayerByTeamAndSeason(@PathVariable int id_team, @PathVariable int  season){
        return  teamService.getPlayerByTeamAndSeason(id_team, season);
    }

    // Aggiungi altri metodi del controller in base alle tue esigenze
    @GetMapping("/season/player/{idPlayer}/{season}")
    public ResponseEntity<?> getPlayerStatsTeamByIdPlayer(@PathVariable int idPlayer, @PathVariable int season){
        return  teamService.getPlayerStatsTeamByIdPlayer(idPlayer, season);
    }

    /**
     * Restituisce le informazioni di uno specifico  team
     * @param nameTeam nome del team Stringa per cercare il team
     * @return l'oggetto del team ricercato
     */
    @GetMapping("/teamById/{nameTeam}")
    public ResponseEntity<?> getTeamById (@PathVariable String nameTeam) {
        return teamService.getTeamById(nameTeam);
    }

    /**
     * restituisce le statistiche stagionali di un team
     * @param idTeam identificativo del team
     * @param season anno della stagione con la quale cercare l id della season
     * @return un entita in base all esito della query, le statistiche se va a buon fine altrimenti degli avvisi di errore come l inserimento sbagliato della season
     */
    @GetMapping("/season/stats/{idTeam}/{season}")
    public ResponseEntity<?> getTeamStatsSeason(@PathVariable int idTeam, @PathVariable int season){
        return  teamService.getTeamStatsSeason( idTeam, season);
    }

}
