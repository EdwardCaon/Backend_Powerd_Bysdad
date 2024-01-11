package Powered_by.springboot.controller;

import Powered_by.springboot.payload.response.GameStatsResponse;
import Powered_by.springboot.service.TeamGameService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin
public class TeamGameController {

    @Autowired
    private TeamGameService teamGameService;

    @GetMapping("/stats/{idGame}")
    public ResponseEntity<List<GameStatsResponse>> getTeamGameStats(@PathVariable int idGame) {
        List<GameStatsResponse> teamGameStatsList = teamGameService.getTeamGameStats(idGame);
        if (!teamGameStatsList.isEmpty()) {
            return new ResponseEntity<>(teamGameStatsList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
