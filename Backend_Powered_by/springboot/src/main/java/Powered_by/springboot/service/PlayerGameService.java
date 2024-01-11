package Powered_by.springboot.service;

import Powered_by.springboot.payload.response.PlayerStatsGameResponse;
import Powered_by.springboot.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlayerGameService {

    @Autowired
    private PlayerStatsRepository playerStatsRepository;

    public List<PlayerStatsGameResponse> getPlayerStatsRepository(Long idGame) {
        List<Object[]> playerStatsObjects = playerStatsRepository.getPlayerStatsByIdGame(idGame);
        return mapToPlayerStatsGameResponse(playerStatsObjects);
    }

    private List<PlayerStatsGameResponse> mapToPlayerStatsGameResponse(List<Object[]> playerStatsObjects) {
        List<PlayerStatsGameResponse> playerStatsGameResponses = new ArrayList<>();

        for (Object[] playerStatsObject : playerStatsObjects) {
            Integer idTeam = (Integer) Objects.requireNonNull(playerStatsObject[0]);
            Integer idPlayer = (Integer) Objects.requireNonNull(playerStatsObject[1]);
            Integer points = (Integer) Objects.requireNonNull(playerStatsObject[2]);
            Integer assist = (Integer) Objects.requireNonNull(playerStatsObject[3]);
            Integer totalRebounds = (Integer) Objects.requireNonNull(playerStatsObject[4]);

            playerStatsGameResponses.add(new PlayerStatsGameResponse(idTeam, idPlayer, points, assist, totalRebounds));
        }

        return playerStatsGameResponses;
    }
}
