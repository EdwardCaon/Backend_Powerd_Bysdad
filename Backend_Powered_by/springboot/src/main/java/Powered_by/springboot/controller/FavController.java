
package Powered_by.springboot.controller;


import Powered_by.springboot.payload.request.NewFavPlayerRequest;
import Powered_by.springboot.payload.request.NewFavTeamRequest;
import Powered_by.springboot.service.FavService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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



    @GetMapping("/get/team/{email}")
    public ResponseEntity<?> getFavTeam(@PathVariable String email) {
        return favService.getFavTeam(email);
    }

    @GetMapping("/get/player/{email}")
    public ResponseEntity<?> getFavPlayer(@PathVariable String email) {
        return favService.getFavPlayer(email);
    }



}
