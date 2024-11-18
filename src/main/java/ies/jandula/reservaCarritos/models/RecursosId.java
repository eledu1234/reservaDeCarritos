package ies.jandula.reservaCarritos.models;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class RecursosId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099506005992566764L;
	
	private String aula;
	
	private int carritos;



}