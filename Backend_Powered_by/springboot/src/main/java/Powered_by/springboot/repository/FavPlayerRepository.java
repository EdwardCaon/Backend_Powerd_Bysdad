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
    /**
     * Metodo per trovare la lista di preferiti associata all utente
     * @param tmpIdUser id dell utente temporaneo per trovare i suoi preferiti
     * @return la lista di preferiti associata all utente
     */
    @Query("SELECT fp.player.idPlayer, fp.player.firstname, fp.player.lastname, fp.player.height, fp.player.weight " +
            "FROM FavPlayer fp " +
            "WHERE fp.user.idUser = :tmpIdUser ")
    List<Object[]> getFavPlayer(Integer tmpIdUser);


    /**
     * Metodo per salvare un nuovo preferito
     * @param idTeam id del team da salvare
     * @param idUser id dell utente a cui associare il preferito
     */
    @Modifying
    @Query(value = "INSERT INTO fav_player (id_user, id_player) VALUES (:idUser, :idPlayer)", nativeQuery = true)
    void saveFavPlayer(@Param("idPlayer") int idTeam, @Param("idUser") int idUser);

    /**
     * Metodo per trogliere un nuovo preferito
     * @param idTeam id del team da rimuovere
     * @param idUser id dell utente da cui rimuovere il preferito
     */
    @Modifying
    @Query(value = "DELETE FROM fav_player WHERE id_user = :idUser AND id_player = :idPlayer", nativeQuery = true)
    void deleteFavPlayer( @Param("idPlayer") int idTeam,@Param("idUser") int idUser);

    /**
     * Metodo per trovare i preferiti del player
     * @param idPlayer id del player da cercare
     * @param idUser id del utente dalla quale cercare i preferiti
     * @return lista dei preferiti
     */
    @Query("SELECT fp " +
            "FROM FavPlayer fp " +
            "WHERE fp.player.idPlayer = :idPlayer and fp.user.idUser = :idUser")
    Optional<FavPlayer> findByidPlayerAndidUser(int idPlayer, int idUser);
    @Query("SELECT " +
            "fp.player.idPlayer " +
            "FROM FavPlayer fp " +
            "WHERE fp.user.idUser = :tmp_idUser")
    List<Object[]> getIdPlayerTeam(@Param("tmp_idUser") int tmp_idUser);


}
