package ies.jandula.reservaCarritos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class TramosHorarios {
	
	
	//11:00/12:00
	 @Id
	 @Column
	 private String tramos;
	 
	 @ManyToOne
	 @JoinColumn(name = "dias_de_la_semana", nullable = false)
	 private DiasSemana diaSemana;
	 
}
