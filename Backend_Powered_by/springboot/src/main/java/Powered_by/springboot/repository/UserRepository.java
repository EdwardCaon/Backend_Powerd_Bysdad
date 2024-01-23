package Powered_by.springboot.repository;

import Powered_by.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Modifying
    @Query("UPDATE User u SET u.firstname = :firstname WHERE u.idUser = :idUser")
    void updateFirstname(
            @Param("idUser") int idUser,
            @Param("firstname") String firstname);
    @Modifying
    @Query("UPDATE User u SET u.lastname = :lastname WHERE u.idUser = :idUser")
    void updateLastname(
            @Param("idUser") int idUser,
            @Param("lastname") String lastname);
    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.idUser = :idUser")
    void updateEmail(
            @Param("idUser") int idUser,
            @Param("email") String email);


}
