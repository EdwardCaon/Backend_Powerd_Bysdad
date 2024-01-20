package Powered_by.springboot.repository;

import Powered_by.springboot.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository  extends JpaRepository<Player, Integer> {

    @Query(value = "SELECT p.date_of_birthday, c.name_country , c.flag , p.height ,p.weight, pd.pos, pd.shirt_number, t.name_team , t.logo " +
    "FROM player p " +
    "JOIN country c on p.country = c.id_country " +
    "JOIN player_details pd on p.id_player = pd.id_player " +
            "JOIN team t on pd.id_team = t.id_team " +
   " WHERE pd.id_season = :tmpSeason and pd.id_player = :idPlayer", nativeQuery = true)
    List<Object[]> getPanoramicsPlayer(
            @Param("idPlayer") int idPlayer,
            @Param("tmpSeason") Integer tmpSeason);
}
