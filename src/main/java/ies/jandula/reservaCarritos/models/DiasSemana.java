package ies.jandula.reservaCarritos.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class DiasSemana {

	@Id
	@Column(length = 9)
	private String DiasDeLaSemana;
	
	@OneToMany(mappedBy = "diaSemana" )
	private List<TramosHorarios> listaDeDias;
	
	
	
	

	
	

}
