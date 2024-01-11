package Powered_by.springboot.repository;

import Powered_by.springboot.entity.FavPlayer;
import Powered_by.springboot.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavPlayerRepository extends JpaRepository<FavPlayer, Long> {

    @Query("SELECT fp.player.idPlayer, fp.player.firstname, fp.player.lastname, fp.player.height, fp.player.weight " +
            "FROM FavPlayer fp " +
            "WHERE fp.user.idUser = :tmpIdUser ")
    List<Object[]> getFavPlayer(Integer tmpIdUser);



    @Modifying
    @Query(value = "INSERT INTO fav_player (id_user, id_player) VALUES (:idUser, :idPlayer)", nativeQuery = true)
    void saveFavPlayer(@Param("idPlayer") int idTeam, @Param("idUser") int idUser);

    @Modifying
    @Query(value = "DELETE FROM fav_player WHERE id_user = :idUser AND id_player = :idPlayer", nativeQuery = true)
    void deleteFavPlayer( @Param("idPlayer") int idTeam,@Param("idUser") int idUser);
    @Query("SELECT fp " +
            "FROM FavPlayer fp " +
            "WHERE fp.player.idPlayer = :idPlayer and fp.user.idUser = :idUser")
    Optional<FavPlayer> findByidPlayerAndidUser(int idPlayer, int idUser);
}
