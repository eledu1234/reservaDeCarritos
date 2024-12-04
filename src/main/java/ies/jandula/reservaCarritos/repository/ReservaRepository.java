package ies.jandula.reservaCarritos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ies.jandula.reservaCarritos.models.Reserva;
import ies.jandula.reservaCarritos.models.ReservaId;

public interface ReservaRepository extends JpaRepository<Reserva, ReservaId>
{

	@Query("SELECT r FROM Reserva r WHERE r.reservaId.email = :email AND "
			+ "r.reservaId.aulaYCarritos.aulaYCarritos = :aulaYCarritos AND "
			+ "r.reservaId.diasDeLaSemana.diasDeLaSemana = :diasDeLaSemana AND "
			+ "r.reservaId.tramosHorarios.tramosHorarios = :tramosHorarios")
    Optional<Reserva> encontrarReserva(@Param("email") String email, 
    						 @Param("aulaYCarritos") String aulaYCarritos,
    						 @Param("diasDeLaSemana") String diasDeLaSemana,
    						 @Param("tramosHorarios") String tramosHorarios);

}
