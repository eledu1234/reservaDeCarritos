package ies.jandula.reservaCarritos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ies.jandula.reservaCarritos.models.Reserva;
import ies.jandula.reservaCarritos.models.ReservaId;

public interface ReservaRepository extends JpaRepository<Reserva, ReservaId>
{

	@Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.reservaId.email = :email")
    boolean existsByProfesorEmail(@Param("email") String email);

	@Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.reservaId.aulaYCarritos = :aulaYCarritos")
	boolean existsByAulaYCarritos(@Param("aulaYCarritos") String aulaYCarritos);
	
	@Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.reservaId.diasDeLaSemana = :diasDeLaSemana")
	boolean existsByDiaDeLaSemana(@Param("diasDeLaSemana") String diasDeLaSemana);
	
	@Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.reservaId.tramosHorarios = :tramosHorarios")
	boolean existsByTramosHorarios(@Param("tramosHorarios") String tramosHorarios);

}
