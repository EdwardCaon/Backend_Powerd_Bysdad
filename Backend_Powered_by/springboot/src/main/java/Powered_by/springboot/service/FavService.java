package Powered_by.springboot.service;

import Powered_by.springboot.entity.FavPlayer;
import Powered_by.springboot.entity.FavTeam;
import Powered_by.springboot.entity.User;
import Powered_by.springboot.payload.request.TokenUserRequest;
import Powered_by.springboot.payload.request.NewFavPlayerRequest;
import Powered_by.springboot.payload.request.NewFavTeamRequest;
import Powered_by.springboot.payload.response.FavPlayerResponse;
import Powered_by.springboot.payload.response.FavTeamResponse;
import Powered_by.springboot.repository.FavPlayerRepository;
import Powered_by.springboot.repository.FavTeamRepository;
import Powered_by.springboot.repository.TeamRepository;
import Powered_by.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FavService {
    private  FavTeamRepository favTeamRepository;
    private  FavPlayerRepository favPlayerRepository;
    private  UserRepository userRepository;
    private  TeamRepository teamRepository;
    private   TokenService tokenService;

    @Autowired
    public FavService (FavTeamRepository favTeamRepository, FavPlayerRepository favPlayerRepository, UserRepository userRepository, TeamRepository teamRepository, TokenService tokenService){
        this.favTeamRepository = favTeamRepository;
        this.favPlayerRepository = favPlayerRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.tokenService = tokenService;
    }

    /**
     * Trova un utente tramite token
     * @param token token per individuare l'utente
     * @return ritorna l'id dell utente associato al token
     */
    public int findIdUser(String token){
        int userId = tokenService.getUserIdFromToken(token).getId();
        return userId;
    }

    /**
     * Trova l id del team grazie al nome ad esso associato
     * @param nameTeam
     * @return
     */
    public int findidTeam(String nameTeam){
        int idTeam = teamRepository.getIdTeamByNameTeam(nameTeam);
        return idTeam;
    }

    /**
     * Deprecato - Metodo per trovare l'utente tramite email
     * @param email mail per cercare l'utente
     * @return ritorna null se non trova un utente associato alla mail altrimenti restituisce l 'id utente
     */
    public Integer getIdUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null || Objects.isNull(user.getIdUser()))  {
            // Se l'utente non è stato trovato o l'ID utente è null, restituisci null
            return null;
        }


        return user.getIdUser();
    }

    /**
     * Metodo utilizzato per aggiungere o rimuovere un team dai preferiti dell utente
     * @param request contiene il token per ricercare l'id utente e l id dep team
     * @return ritorna un messaggio di conferma di aggiunta o rimozione del preferito
     */
    @Transactional
    public ResponseEntity<?> saveOrDeleteFavTeam(NewFavTeamRequest request) {
        // Check if the FavTeam already exists in the database
        System.out.println(findidTeam(request.getNameTeam()));
        System.out.println(findIdUser(request.getToken()));
        Optional<FavTeam> existingFavTeam = favTeamRepository.findByidTeamAndidUser(findidTeam(request.getNameTeam()), findIdUser(request.getToken()));
        if (existingFavTeam.isEmpty()) {
            // FavTeam does not exist, so save it
            favTeamRepository.saveFavTeam(findidTeam(request.getNameTeam()), findIdUser(request.getToken()));
            return new ResponseEntity<>("FavTeam saved successfully", HttpStatus.CREATED);
        } else {
            // FavTeam already exists, so delete it
            favTeamRepository.deleteFavTeam(findidTeam(request.getNameTeam()), findIdUser(request.getToken()));
            return new ResponseEntity<>("FavTeam deleted successfully", HttpStatus.OK);
        }
    }


    /**
     * Metodo utilizzato per aggiungere o rimuovere un player dai preferiti dell utente
     * @param request contiene il token per ricercare l'id utente e l id del player
     * @return ritorna un messaggio di conferma di aggiunta o rimozione del preferito
     */
    @Transactional
    public ResponseEntity<?> saveFavPlayer(NewFavPlayerRequest request) {
        Optional<FavPlayer> existingFavPlayer = favPlayerRepository.findByidPlayerAndidUser(request.getIdPlayer(), findIdUser(request.getToken()));
        if (existingFavPlayer.isEmpty()) {
            // FavTeam does not exist, so save it
            favPlayerRepository.saveFavPlayer(request.getIdPlayer(), findIdUser(request.getToken()));
            return new ResponseEntity<>("FavPlayer saved successfully", HttpStatus.CREATED);
        } else {
            // FavTeam already exists, so delete it
            favPlayerRepository.deleteFavPlayer(request.getIdPlayer(), findIdUser(request.getToken()));
            return new ResponseEntity<>("FavPlayer deleted successfully", HttpStatus.OK);
        }
    }

    /**
     * Ritorna una lista di Team pregeriti dall'utente
     * @param request contiene il token per individuare l'utente e ricercare i suoi preferiti
     *  @return lista di team favoriti
     */
    public ResponseEntity<?> getFavTeam(TokenUserRequest request) {
      Integer tmp_idUser = findIdUser(request.getToken());
        List<Object[]> resultList = favTeamRepository.getFavTeam(tmp_idUser);
        List<FavTeamResponse> favTeamResponses = mapToFavTeamResponse(resultList);
        return ResponseEntity.ok(favTeamResponses);
    }

    /**
     Mappa i risultati della repository sulla response poiche il converitore non è in grado di farlo da se
     * @param resultList contiene i risultati della query ovvero i team preferiti da un user
     * @return list di oggetti FavTeam da restituire al controller
     */
    private List<FavTeamResponse> mapToFavTeamResponse(List<Object[]> resultList) {
        List<FavTeamResponse> favTeamResults = new ArrayList<>();
        for (Object[] result : resultList) {
            int idTeam = (int) result[0];
            String nameTeam = (String) result[1];
            String nickname = (String) result[2];
            String colour = (String) result[3];
            String logo = (String) result[4];
            String league = (String) result[5];

            FavTeamResponse favTeamResponse = new FavTeamResponse(idTeam, nameTeam, nickname, colour, logo, league);
            favTeamResults.add(favTeamResponse);
        }
        return favTeamResults;
    }

    /**
     * Restituisce una lista di player preferiti tramite il token dell utente
     * @param request contiene il token per individuare l'utente e ricercare i suoi preferiti
     * @return lista di player favoriti
     */
    public ResponseEntity<?> getFavPlayer(TokenUserRequest request) {
        Integer tmp_idUser = findIdUser(request.getToken());
        List<Object[]> resultList = favPlayerRepository.getFavPlayer(tmp_idUser);
        List<FavPlayerResponse> favPlayerResponses = mapToFavPlayerResponse(resultList);
        return ResponseEntity.ok(favPlayerResponses);
    }

    /**
     * Mappa i risultati della repository sulla response poiche il converitore non è in grado di farlo da se
     * @param resultList contiene i risultati della query ovvero i giocatori preferiti da un user
     * @return list di oggetti FavPlayer da restituire al controller
     */
    private List<FavPlayerResponse> mapToFavPlayerResponse(List<Object[]> resultList) {
        List<FavPlayerResponse> favTeamResults = new ArrayList<>();
        for (Object[] result : resultList) {
            int idPlayer = (int) result[0];
            String firsname = (String) result[1];
            String lastname = (String) result[2];
            Double height = (Double) result[3];
            Double weight = (Double) result[4];
            FavPlayerResponse favPlayerResponses = new FavPlayerResponse(idPlayer, firsname, lastname, height, weight);
            favTeamResults.add(favPlayerResponses);
        }
        return favTeamResults;
    }

}
