package ies.jandula.reservaCarritos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.reservaCarritos.models.Reserva;
import ies.jandula.reservaCarritos.models.ReservaId;

public interface ReservaRepository extends JpaRepository<Reserva, ReservaId>
{

}
