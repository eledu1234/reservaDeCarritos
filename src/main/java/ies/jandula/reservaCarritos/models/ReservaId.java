package ies.jandula.reservaCarritos.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ReservaId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4705657948307458266L;
	
	@ManyToOne
	@JoinColumn(name = "email_profesor", referencedColumnName = "email")
	private Profesor email;
	
	@ManyToOne
	@JoinColumn(name = "recursos_aula_y_carritos", referencedColumnName = "aulaYCarritos")
	private Recursos aulaYCarritos;
	
	@ManyToOne
	@JoinColumn(name = "dia_de_la_semana", referencedColumnName = "diasDeLaSemana")
	private DiasSemana diasDeLaSemana;

	@ManyToOne
	@JoinColumn(name = "tramos_horarios", referencedColumnName = "tramos")
	private TramosHorarios tramosHorarios;

}