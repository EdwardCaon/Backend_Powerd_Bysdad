package Powered_by.springboot.repository;

import Powered_by.springboot.entity.PlayerDetails;
import Powered_by.springboot.payload.response.PlayerSearchBarResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerDetailsRepository extends JpaRepository<PlayerDetails, Integer> {

    @Query(value = "SELECT p.id_player, p.firstname, p.lastname, pd.img " +
            "FROM player_details pd " +
            "JOIN player p on pd.id_player = p.id_player " +
            "WHERE pd.id_season = 9"
            , nativeQuery = true)
    List<Object[]> getAllPlayer();
}
