package Powered_by.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter @AllArgsConstructor
@NoArgsConstructor
@Table(name= "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    //email login
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "admin")
    private int admin;


    public User (int idUser,String password) {
        this.idUser = idUser;
        this.password=password;
    }


    /**
     * costruttore
     *
     * @param firstname        the nome
     * @param lastname     the cognome
     * @param email        the email
     * @param password    the password
     */
    public User(String firstname, String lastname, String email, String password, int  admin) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }


}
