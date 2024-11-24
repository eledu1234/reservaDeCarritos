package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Reserva {

	@EmbeddedId
	@Column(length = 50)
	private ReservaId reservaId;
	
	@Column(nullable = false)
	private int nAlumnos;
	
}