package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recursos {
	@Id
	@Column
	private String aula;
	
	@Column
	private int carritos;
	
	@Column 
	private int nAlumnos;
	
}
