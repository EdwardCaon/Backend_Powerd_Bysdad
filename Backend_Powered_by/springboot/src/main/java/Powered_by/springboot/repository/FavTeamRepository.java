package Powered_by.springboot.repository;

import Powered_by.springboot.entity.FavTeam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavTeamRepository extends JpaRepository<FavTeam, Long> {
    @Query("SELECT ft.team.idTeam, ft.team.nameTeam, ft.team.nickname, ft.team.colour, ft.team.logo, ft.team.league.nameLeague " +
            "FROM FavTeam ft " +
            "WHERE  ft.user.idUser = :tmpIdUser")
    List<Object[]> getFavTeam(int tmpIdUser);


    @Query("SELECT ft " +
            "FROM FavTeam ft " +
            "WHERE ft.team.idTeam = :idTeam and ft.user.idUser = :idUser")
    Optional<FavTeam> findByidTeamAndidUser(int idTeam, int idUser);


    @Modifying
    @Query(value = "INSERT INTO fav_team (id_user, id_team) VALUES (:idUser, :idTeam)", nativeQuery = true)
    void saveFavTeam( @Param("idTeam") int idTeam,@Param("idUser") int idUser);

    @Modifying
    @Query(value = "DELETE FROM fav_team WHERE id_user = :idUser AND id_team = :idTeam", nativeQuery = true)
    void deleteFavTeam( @Param("idTeam") int idTeam,@Param("idUser") int idUser);

}
