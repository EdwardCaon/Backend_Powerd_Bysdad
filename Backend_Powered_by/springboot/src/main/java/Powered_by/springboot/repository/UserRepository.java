package Powered_by.springboot.repository;

import Powered_by.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * trova {@link User} tramite il suo username e password
     *
     * @param email          the email
     * @param encryptedPassword è l'algoritmo per criptare la password
     * @return the optional
     */
    Optional<User> findByEmailAndAndPassword(String email, String encryptedPassword);

    /**
     * trova {@link User} tramite il suo username
     *
     * @param email the username
     * @return the user
     */

    User findByEmail(String email);




}
