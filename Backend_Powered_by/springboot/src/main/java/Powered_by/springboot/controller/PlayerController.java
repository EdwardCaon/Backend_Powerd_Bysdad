package Powered_by.springboot.controller;

import Powered_by.springboot.payload.response.PlayerSearchBarResponse;
import Powered_by.springboot.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@CrossOrigin
public class PlayerController {

     @Autowired
    private  PlayerService playerService;

    @GetMapping("/all")
    public List<PlayerSearchBarResponse> getAllPlayer() {
        return playerService.getAllPlayer();
    }

    @GetMapping("/pan/{idPlayer}/{season}")
    public ResponseEntity<?> getPanoramicsPlayer(@PathVariable int idPlayer, @PathVariable int season) {
        return  playerService.getPanoramicsPlayer(idPlayer, season);
    }


}
