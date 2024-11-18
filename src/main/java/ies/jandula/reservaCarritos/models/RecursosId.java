package ies.jandula.reservaCarritos.models;

import java.io.Serializable;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RecursosId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099506005992566764L;
	
	private String aula;
	private int carritos;
	
	

}
