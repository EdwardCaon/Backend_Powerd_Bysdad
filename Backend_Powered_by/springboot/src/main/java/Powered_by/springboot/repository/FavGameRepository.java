package Powered_by.springboot.repository;

import Powered_by.springboot.entity.FavGame;
import Powered_by.springboot.payload.response.FavGameResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavGameRepository extends JpaRepository<FavGame, Integer> {

    /**
     * Metodo per trovare i preferiti del player
     * @param idGame id del player da cercare
     * @param idUser id del utente dalla quale cercare i preferiti
     * @return lista dei preferiti
     */
    @Query("SELECT fg " +
            "FROM FavGame fg " +
            "WHERE fg.game.idGame = :idGame and fg.user.idUser = :idUser")
    Optional<FavGame> findByidGameAndidUser( @Param("idGame")  Integer idGame,@Param("idUser")  int idUser);




    /**
     * Metodo per salvare un nuovo preferito
     * @param idGame id del team da salvare
     * @param idUser id dell utente a cui associare il preferito
     */
    @Modifying
    @Query(value = "INSERT INTO fav_game (id_user, id_game) VALUES (:idUser, :idGame)", nativeQuery = true)
    void saveFavGame(@Param("idGame") Integer idGame, @Param("idUser")  int idUser);

    /**
     * Metodo per trogliere un nuovo preferito
     * @param idGame id del team da rimuovere
     * @param idUser id dell utente da cui rimuovere il preferito
     */
    @Modifying
    @Query(value = "DELETE FROM fav_game WHERE id_user = :idUser AND id_game = :idGame", nativeQuery = true)
    void deleteFavGame( @Param("idGame") int idGame , @Param("idUser") int idUser);

    @Query("SELECT " +
            "g.idGame, g.start, " +
            "t.nameTeam, t.logo, t.colour, s.p1, s.p2, s.p3, s.p4, s.p5, " +
            "t_e.nameTeam, t_e.logo, t_e.colour ,s_e.p1, s_e.p2, s_e.p3, s_e.p4, s_e.p5 " +
            "FROM FavGame fg " +
            "JOIN Game g on fg.game.idGame = g.idGame " +
            "JOIN g.home t " +
            "JOIN g.visitors t_e " +
            "JOIN Score s ON s.team.idTeam = t.idTeam " +
            "JOIN Score s_e ON s_e.team.idTeam = t_e.idTeam " +
            "WHERE fg.user.idUser = :tmp_idUser and s.game.idGame = g.idGame and s_e.game.idGame = g.idGame " +
            "group by  t.nameTeam")
    List<Object[]> getFavGame(
            @Param("tmp_idUser") int tmp_idUser);

    @Query("SELECT " +
            "fg.game.idGame " +
            "FROM FavGame fg " +
            "WHERE fg.user.idUser = :tmp_idUser")
    List<Object[]> getIdFavGame(
            @Param("tmp_idUser") int tmp_idUser);

}
