package Powered_by.springboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;


/**
 * The type Token service.
 */
@Service
@Slf4j //genera il logger
public class TokenService {


    /**
         * The constant TOKEN_SECRET.
         */
        public static  final String TOKEN_SECRET="AlbertoTola2024";
        /**
         * The constant EXPIRE_AFTER.
         */
        public static  final int EXPIRE_AFTER=3600; //tempo di scadenza in secondi

        /**
         * Metodo per implementare il token<br>
         * Utiliziamo un algorimto per il token, dove li passiamo la nostra chiave per la criptazione e decriptazione<br>
         * Quando vieni creato il token noi ci mettiamo al suo interno l'id dell'utente<br>
         * Il token ha una durata, e scade dopo un tot
         *
         * @param userId che diventerà il token
         * @return the string
         */
        public String createToken(int userId){
            try {
                Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
                String token = JWT.create()
                        .withClaim("userId",userId) //claim personalizzato

                        .withIssuedAt(Instant.now()) //claim iat
                        .withExpiresAt(Instant.now().plus(EXPIRE_AFTER, ChronoUnit.SECONDS)) //claim scadenza
                        .sign(algorithm);
                return  token;
            }catch (Exception e){
                log.error(e.getMessage());
            }
            return  null;
        }


        /**
         * Metodo per decriptare il token e lo trasformiano in id<br>
         *
         * @param token che diventerà l'id dell'utente
         * @return the user token
         */
        public UserToken getUserIdFromToken(String token){
            try{
                Algorithm algorithm=Algorithm.HMAC256(TOKEN_SECRET);
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT jwt = verifier.verify(token);
                UserToken ut = new UserToken(jwt.getClaim("userId").asInt());
                return  ut;
            }catch (Exception e){
                log.error(e.getMessage());
            }
            return null;
        }




}
