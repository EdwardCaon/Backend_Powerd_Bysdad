
package Powered_by.springboot.controller;


import Powered_by.springboot.entity.FavGame;
import Powered_by.springboot.payload.request.NewFavGameRequest;
import Powered_by.springboot.payload.request.TokenUserRequest;
import Powered_by.springboot.payload.request.NewFavPlayerRequest;
import Powered_by.springboot.payload.request.NewFavTeamRequest;
import Powered_by.springboot.payload.response.FavGameIdResponse;
import Powered_by.springboot.payload.response.FavGameResponse;
import Powered_by.springboot.payload.response.FavPlayerResponse;
import Powered_by.springboot.payload.response.FavTeamResponse;
import Powered_by.springboot.service.FavService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fav")
@RequiredArgsConstructor
@CrossOrigin
public class FavController {

    private final FavService favService;

    @PostMapping("/new/team")
    public ResponseEntity<?> saveFavTeam(@RequestBody @Valid NewFavTeamRequest request) {
        return favService.saveOrDeleteFavTeam(request);
    }

    @PostMapping("/new/player")
    public ResponseEntity<?> saveFavPlayer(@RequestBody @Valid NewFavPlayerRequest request) {
        return favService.saveFavPlayer(request);
    }



    @PostMapping("/get/team")
    public List<FavTeamResponse> getFavTeam(@RequestBody @Valid TokenUserRequest request) {
        return favService.getFavTeam(request);
    }

    @PostMapping("/get/player")
    public List<FavPlayerResponse> getFavPlayer(@RequestBody @Valid TokenUserRequest request) {
        return favService.getFavPlayer(request);
    }

    @PostMapping("/new/game")
    public ResponseEntity<?> saveFavGame(@RequestBody @Valid NewFavGameRequest request) {
        return favService.saveFavGame(request);
    }

    @PostMapping("/get/game")
    public List<FavGameResponse> getFavGame(@RequestBody @Valid TokenUserRequest request) {
        return favService.getFavGame(request);
    }
    @PostMapping("/get/idgame")
    public List<FavGameIdResponse> getIdFavGame(@RequestBody @Valid TokenUserRequest request) {
        return favService.getIdFavGame(request);
    }

/*
    @GetMapping("/season/stats/{idPlayer}")
    public ResponseEntity<?> getSasonPlayerStats(@PathVariable Long idPlayer) {
        return favService.getFavPlayerStats(idPlayer);
    }
*/


}
