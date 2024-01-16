package Powered_by.springboot.controller;

import Powered_by.springboot.payload.response.PlayerStatsGameResponse;
import Powered_by.springboot.payload.response.TeamClassificaResponse;
import Powered_by.springboot.service.PlayerGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@CrossOrigin
public class PlayerGameController {

    @Autowired
    private PlayerGameService playerGameService;

    @GetMapping("/stats/player/{idGame}")
    public List<PlayerStatsGameResponse> countWinsByTeamAndSeason(@PathVariable Long idGame) {
        return playerGameService.getPlayerStatsRepository(idGame);
    }

}
