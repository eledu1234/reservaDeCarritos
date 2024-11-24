package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Profesor {
	
	@Id
	@Column(length = 50, nullable = false)
	private String email;
	
	@Column(length = 100, nullable = false)
	private String nombre;
	
}
