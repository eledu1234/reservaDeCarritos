package ies.jandula.reservaCarritos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import ies.jandula.reservaCarritos.interfaces.IGestroParseo;
import ies.jandula.reservaCarritos.repository.RecursosRepository;
import ies.jandula.reservaCarritos.repository.TramoHorarioRepository;
import ies.jandula.reservaCarritos.utils.Costantes;

@SpringBootApplication
public class ReservaCarritosApplication implements CommandLineRunner
{
	
	@Autowired
	private IGestroParseo iGestroParseo;
	@Autowired
	private RecursosRepository recursosRepository;
	@Autowired TramoHorarioRepository tramoHorarioRepository;

	public static void main(String[] args) 
	{
		SpringApplication.run(ReservaCarritosApplication.class, args);
	}

	@Transactional(readOnly = false)
	public void run(String... args) throws Exception 
	{
		// TODO Auto-generated method stub
		
		if(this.recursosRepository.findAll().isEmpty())
		{
			this.iGestroParseo.parseaFichero(Costantes.FICHERO_RECURSO);
		}
		
		if(this.tramoHorarioRepository.findAll().isEmpty())
		{
			this.iGestroParseo.parseaFichero(Costantes.FICHERO_TRAMOS_HORARIOS);
		}
	}

}
