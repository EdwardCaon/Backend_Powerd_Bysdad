package Powered_by.springboot.service;

import Powered_by.springboot.controller.UpdateEmailResponse;
import Powered_by.springboot.entity.User;
import Powered_by.springboot.payload.request.*;
import Powered_by.springboot.payload.response.AuthResponse;
import Powered_by.springboot.payload.response.FavTeamResponse;
import Powered_by.springboot.payload.response.UpdateFirstnameResponse;
import Powered_by.springboot.payload.response.UpdateLastnameResponse;
import Powered_by.springboot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private  final TokenService tokenService;


    /**
     * Trasforma la request in un entita per controllare se l'utente esiste gia
     * @param request
     * @return classe User
     */
    private User fromRequestToEntity(SignupRequest request) {
        User u = new User();

        // Calcola l'hash SHA-256 della password
        String encryptedPassword = DigestUtils.sha256Hex(request.getPassword());

        // Crea un nuovo oggetto User con i dati dalla richiesta e la password hashata
        u = new User(request.getFirstname(), request.getLastname(), request.getEmail(), encryptedPassword,request.getAdmin());

        return u;
    }

    /**
     * Metodo utilizzato per la registrazione
     * @param request contiene i dati utili alla registrazione quale firstname, lastname, email, password di almeno sei caratteri
     * @return ritorna l'entita appeana creata qual ora avvenisse la registrazione,  qual ora cio non avviene viene ritornata un entita la quale comunica
     *          l esito negativo della registrazione
     */
    public ResponseEntity<?> save (SignupRequest request){
        User newuser=fromRequestToEntity(request);
        User olduser=userRepository.findByEmail(request.getEmail());
        if(olduser== null || !newuser.getEmail().equals(olduser.getEmail())  ) { //se il nome utente non esiste già creo l'utente
            return new ResponseEntity<>(userRepository.save(newuser), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("utente già esistente",HttpStatus.BAD_REQUEST);
    }

    /**
     * Metodo per l accesso da parte di un utente registrato
     * @param request contiene i dati utili all accessi quali mail e password
     * @return AuthResponse qual ora l accesso avvenga correttamente e contiene i dati dell utente piu il token per la sessione , qual ora non lo fosse viene ritornata un entita la quale comunica
     *      * l esito negativo dell accesso
     */
    public ResponseEntity<?> signin(SigninRequest request) {
        Optional<User> u = userRepository.findByEmailAndAndPassword(request.getEmail(), DigestUtils.sha256Hex(request.getPassword()));
        if (u.isPresent()) {
            String token = tokenService.createToken(u.get().getIdUser());
            AuthResponse authenticatedUser = new AuthResponse(u.get().getIdUser(),  u.get().getFirstname(), u.get().getLastname(),request.getEmail(), token, u.get().getAdmin());
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("username o password errati", HttpStatus.BAD_REQUEST);
        }
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
    @Transactional
    public UpdateFirstnameResponse updateFirstname(UpdateFirstnameRequest request) {
        userRepository.updateFirstname(findIdUser(request.getToken()), request.getFirstname());
        UpdateFirstnameResponse updateFirstnameResponse = new UpdateFirstnameResponse( request.getFirstname());
        return updateFirstnameResponse;
    }
    @Transactional
    public UpdateLastnameResponse updateLastname(UpdateLastnameRequest request) {
        userRepository.updateLastname(findIdUser(request.getToken()), request.getLastname());
        UpdateLastnameResponse updateLastnameResponse = new UpdateLastnameResponse( request.getLastname());
        return updateLastnameResponse;
    }
    @Transactional
    public UpdateEmailResponse updateEmail(UpdateEmailRequest request) {
        userRepository.updateEmail(findIdUser(request.getToken()), request.getEmail());
        UpdateEmailResponse updateEmailResponse = new UpdateEmailResponse( request.getEmail());
        return updateEmailResponse;
    }
}
