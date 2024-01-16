package Powered_by.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_season")
    private int idSeason;

    @NotNull
    private Integer season;

    // Constructors, getters, and setters (if needed)

    public int getIdSeason() {
        return idSeason;
    }

    public Integer getSeason() {
        return season;
    }


}
