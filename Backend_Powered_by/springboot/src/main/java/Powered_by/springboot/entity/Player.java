package Powered_by.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_player")
    private int idPlayer;

    @NotBlank
    @Size(max = 50)
    @Column(name = "firstname")

    private String firstname;

    @NotBlank
    @Size(max = 50)
    @Column(name = "lastname")
    private String lastname;

    @Past
    @Column(name = "date_of_birthday")
    private LocalDate dateOfBirth;

    @Size(max = 50)
    @Column(name = "country")
    private String country;
    @Column(name = "started_year")
    private Integer startedYear;
    @Column(name = "pro")
    private Integer pro;
    @Column(name = "height")
    private Double height;
    @Column(name = "weight")
    private Double weight;

    @Size(max = 50)
    @Column(name = "college")
    private String college;

    @Size(max = 50)
    @Column(name = "affiliation")
    private String affiliation;

    // Solo metodi getter

    public int getIdPlayer() {
        return idPlayer;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public Integer getStartedYear() {
        return startedYear;
    }

    public Integer getPro() {
        return pro;
    }

    public Double getHeight() {
        return height;
    }

    public Double getWeight() {
        return weight;
    }

    public String getCollege() {
        return college;
    }

    public String getAffiliation() {
        return affiliation;
    }
}
