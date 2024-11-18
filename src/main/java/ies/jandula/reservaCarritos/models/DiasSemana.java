package ies.jandula.reservaCarritos.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DiasSemana {

	@Id
	@Column
	private String DiasDeLaSemana;
	@OneToMany(mappedBy = "diaSemana" )
	private List<DiasSemana> listaDeDias;
	
	
	
	

	
	

}
