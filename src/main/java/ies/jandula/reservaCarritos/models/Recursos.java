package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Recursos {
	
	@EmbeddedId
	private RecursosId recursoId;
	
	@Column(nullable = false)
	private int nAlumnos;

}