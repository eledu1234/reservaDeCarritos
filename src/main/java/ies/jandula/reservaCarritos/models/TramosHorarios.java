package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TramosHorarios {
	
	
	//11:00/12:00
	 @Id
	 @Column
	 private String tramos;
	 
	 @ManyToOne
	 private DiasSemana diaSemana;
	 
}
