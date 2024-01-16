package Powered_by.springboot.controller;

import Powered_by.springboot.payload.response.GameDayResponse;
import Powered_by.springboot.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
@CrossOrigin
public class GameController {

    private final GameService gameService;

    @GetMapping("/gameday/{data}")
    public List<GameDayResponse> getGameForDay(@PathVariable String data) {
        LocalDateTime startOfDay = LocalDateTime.parse(data + "T00:00:00");
        LocalDateTime endOfDay = LocalDateTime.parse(data + "T23:59:59");

        return gameService.getGamesForDayRange(startOfDay, endOfDay);
    }
}
