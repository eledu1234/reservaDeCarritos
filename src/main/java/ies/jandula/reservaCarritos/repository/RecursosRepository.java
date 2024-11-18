package ies.jandula.reservaCarritos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ies.jandula.reservaCarritos.models.Recursos;
import ies.jandula.reservaCarritos.models.RecursosId;

public interface RecursosRepository extends JpaRepository<Recursos, RecursosId>{

}
