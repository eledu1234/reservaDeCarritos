package ies.jandula.reservaCarritos.models;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
	
	@Column(length = 9)
	private String nif;
	
	@ManyToOne
	private Recursos recursos;
	
	@Column(length = 9)
	private String diasDeLaSemana;





}