package ies.jandula.reservaCarritos.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Reserva {

	@EmbeddedId
	private ReservaId reservaId;
	
	@ManyToOne
	@JoinColumn(name = "nif_profesor")
	@MapsId("nif")
	private Profesor nifProfesor;
	
	
	@ManyToOne
	@JoinColumn(name = "dia_de_la_semana")
	@MapsId("diasDeLaSemana")
	private DiasSemana diasDeLaSemana;
	
	}