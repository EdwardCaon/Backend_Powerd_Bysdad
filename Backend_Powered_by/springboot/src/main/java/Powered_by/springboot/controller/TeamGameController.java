package Powered_by.springboot.controller;

import Powered_by.springboot.payload.response.GameDayResponse;
import Powered_by.springboot.payload.response.GameStatsResponse;
import Powered_by.springboot.service.GameService;
import Powered_by.springboot.service.TeamGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin
public class TeamGameController {

    @Autowired
    private TeamGameService teamGameService;

    @Autowired
    private GameService gameService;

    @GetMapping("/stats/{idGame}")
    public ResponseEntity<List<GameStatsResponse>> getTeamGameStats(@PathVariable int idGame) {
        List<GameStatsResponse> teamGameStatsList = teamGameService.getTeamGameStats(idGame);
        if (!teamGameStatsList.isEmpty()) {
            return new ResponseEntity<>(teamGameStatsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/team/pass/{idTeam}")
    public List<GameDayResponse>getGameForTeam(@PathVariable int idTeam) {
        LocalDateTime startOfDay = LocalDateTime.now();
        //cambiare i mesi
        LocalDateTime endOfDay = startOfDay.plusMonths(3);
        return gameService.getGameForTeam(idTeam,startOfDay, endOfDay);
    }
    @GetMapping("/team/prog/{idTeam}")
    public List<GameDayResponse>getProgrammate(@PathVariable int idTeam) {
        LocalDateTime startOfDay = LocalDateTime.now();
        return gameService.getProgrammate(idTeam,startOfDay);
    }

   /*
    *
    * La lista delle statistiche per partita dei team
    * @GetMapping("/stats/team/{idGame}")
    * public ResponseEntity<List<TeamGameStatsResponse>>getStatsTeam(@PathVariable int idGame){
    *   List<TeamGameStatsResponse> teamStatsList = teamGameService.getStatsTeam(idGame);
    *  if (!teamStatsList.isEmpty()) {
    *     return new ResponseEntity<>(teamStatsList, HttpStatus.OK);
    *   } else {
    *       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    *     }
    }
    */
}
