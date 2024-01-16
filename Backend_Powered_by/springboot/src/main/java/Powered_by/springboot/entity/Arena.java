package Powered_by.springboot.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "arena")
public class Arena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arena")
    private int idArena;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name_arena")
    private String nameArena;

    @Size(max = 25)
    @Column(name = "city")
    private String city;

    @Size(max = 25)
    @Column(name = "state")
    private String state;

    @Size(max = 20)
    @Column(name = "country")
    private String country;

    // Costruttore vuoto
    public Arena() {
        // Vuoto
    }

    // Getters

    public int getIdArena() {
        return idArena;
    }

    public String getNameArena() {
        return nameArena;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
