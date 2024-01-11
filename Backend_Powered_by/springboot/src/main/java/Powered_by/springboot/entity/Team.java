package Powered_by.springboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "team")
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_team")
    private int idTeam;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_league")
    private League league;

    @NotBlank
    @Size(max = 255)
    @Column(name = "name_team")

    private String nameTeam;

    @Size(max = 255)

    private String nickname;

    @Size(max = 10)
    @Column(name = "code_team")

    private String codeTeam;

    @Size(max = 50)
    private String colour;

    @Size(max = 50)
    private String city;

    @Size(max = 255)
    private String logo;

    private Boolean allStar;

    private Boolean nbaFranchise;

    public Team() {

    }

    // Constructors, getters, and setters (if needed)

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public League getLeague() {
        return league;
    }

    

    public String getNameTeam() {
        return nameTeam;
    }



    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCodeTeam() {
        return codeTeam;
    }

    public String getColour(){return  colour;}
    public String getCity() {
        return city;
    }



    public String getLogo() {
        return logo;
    }



    public Boolean getAllStar() {
        return allStar;
    }



    public Boolean getNbaFranchise() {
        return nbaFranchise;
    }


}
