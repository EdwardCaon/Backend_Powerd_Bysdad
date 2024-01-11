package Powered_by.springboot.service;

import Powered_by.springboot.entity.User;
import Powered_by.springboot.payload.request.SigninRequest;
import Powered_by.springboot.payload.request.SignupRequest;
import Powered_by.springboot.payload.response.AuthResponse;
import Powered_by.springboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private  final TokenService tokenService;

    private User fromRequestToEntity(SignupRequest request) {
        User u = new User();

        // Calcola l'hash SHA-256 della password
        String encryptedPassword = DigestUtils.sha256Hex(request.getPassword());

        // Crea un nuovo oggetto User con i dati dalla richiesta e la password hashata
        u = new User(request.getFirstname(), request.getLastname(), request.getEmail(), encryptedPassword,request.getAdmin());

        return u;
    }

    public ResponseEntity<?> save (SignupRequest request){
        User newuser=fromRequestToEntity(request);
        User olduser=userRepository.findByEmail(request.getEmail());
        if(olduser== null || !newuser.getEmail().equals(olduser.getEmail())  ) { //se il nome utente non esiste già creo l'utente
            return new ResponseEntity<>(userRepository.save(newuser), HttpStatus.CREATED);
        }
        return new ResponseEntity<>("utente già esistente",HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<?> signin(SigninRequest request) {
        Optional<User> u = userRepository.findByEmailAndAndPassword(request.getEmail(), DigestUtils.sha256Hex(request.getPassword()));
        if (u.isPresent()) {
            String token = tokenService.createToken(u.get().getIdUser());
            AuthResponse authenticatedUser = new AuthResponse(u.get().getIdUser(), request.getEmail(), u.get().getFirstname(), u.get().getLastname(), token, u.get().getAdmin());
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("username o password errati", HttpStatus.BAD_REQUEST);
        }
    }

}
