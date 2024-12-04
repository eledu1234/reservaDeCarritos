package ies.jandula.reservaCarritos.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ies.jandula.reservaCarritos.exception.ReservaException;
import ies.jandula.reservaCarritos.models.DiasSemana;
import ies.jandula.reservaCarritos.models.Profesor;
import ies.jandula.reservaCarritos.models.Recursos;
import ies.jandula.reservaCarritos.models.Reserva;
import ies.jandula.reservaCarritos.models.ReservaId;
import ies.jandula.reservaCarritos.models.TramosHorarios;
import ies.jandula.reservaCarritos.repository.DiasSemanaRepository;
import ies.jandula.reservaCarritos.repository.RecursosRepository;
import ies.jandula.reservaCarritos.repository.ReservaRepository;
import ies.jandula.reservaCarritos.repository.TramoHorarioRepository;
import ies.jandula.reservaCarritos.utils.Methods;
import lombok.extern.log4j.Log4j2;

@RequestMapping(value = "/incidenciasTic", produces = { "application/json" })
@RestController
@Log4j2
public class ReservaCarritosRestWeb 
{
	
	@Autowired
	private RecursosRepository recursosRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private DiasSemanaRepository diasSemanaRepository;
	
	@Autowired
	private TramoHorarioRepository tramoHorarioRepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/obtener_recurso")
	public ResponseEntity<?> obtenerRecurso()
	{
		
		try
		{
			// Creacion de una lista para almacenar los recursos
			List<Recursos> listaRecursos;
			
			// Encontramos todos los recursos y los introducimos en la lista
			listaRecursos = recursosRepository.findAll();
			
			return ResponseEntity.ok(listaRecursos);
		}
		catch (Exception exception) 
		{
			
			ReservaException reservaException = new ReservaException(1,"error al mostrar el recurso", exception);
			log.error("Error en mostrar el recurso", reservaException);
			
			return ResponseEntity.status(500).body(reservaException.getBodyMesagge());
		}		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/cancelar_recurso", consumes = {"application/json"})
	public ResponseEntity<?> cancelarRecurso(@RequestHeader(value = "email") String email,
											 @RequestHeader(value = "recurso") String aulaYCarritos,
											 @RequestHeader(value = "diaDeLaSemana") String diaDeLaSemana,
											 @RequestHeader(value = "tramoHorario") String tramoHorario){
		try {
			
			Reserva reservaABorrar = new Reserva();
			ReservaId reservaId = new ReservaId();
			
			
			if(reservaRepository.existsByProfesorEmail(email))
			{
				Profesor profesor = new Profesor();
				profesor.setEmail(email);
				reservaId.setEmail(profesor);
			}
			if(reservaRepository.existsByAulaYCarritos(aulaYCarritos)) 
			{
				Recursos recursoABorrar = new Recursos();
				recursoABorrar.setAulaYCarritos(aulaYCarritos);
				reservaId.setAulaYCarritos(recursoABorrar);
			}
			if(reservaRepository.existsByDiaDeLaSemana(diaDeLaSemana)) 
			{
				DiasSemana diasSemana = new DiasSemana();
				diasSemana.setDiasDeLaSemana(diaDeLaSemana);
				reservaId.setDiasDeLaSemana(diasSemana);
			}
			if(reservaRepository.existsByTramosHorarios(tramoHorario)) 
			{
				TramosHorarios tramosHorarios = new TramosHorarios();
				tramosHorarios.setTramos(tramoHorario);
				reservaId.setTramosHorarios(tramosHorarios);
			}
			
			reservaABorrar.setReservaId(reservaId);
			reservaRepository.delete(reservaABorrar);		
			
			return ResponseEntity.ok("Reserva borrada correctamente");
			
		}
		catch (Exception exception) 
		{
			
			ReservaException reservaException = new ReservaException(2,"error al borrar la reserva", exception);
			log.error("Error al borrar la reserva", reservaException);
			
			return ResponseEntity.status(500).body(reservaException.getBodyMesagge());
		}		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/obtener_DiasSemanas")
	public ResponseEntity<?> obtenerDiasSemana()
	{
        try 
        {
        	Methods method = new Methods();
        	
            List<DiasSemana> listaDias = diasSemanaRepository.findAll();

            if (listaDias.isEmpty()) 
            {
                listaDias = method.cargarDatosCSV();
                
                if (!listaDias.isEmpty())
                {
                    diasSemanaRepository.saveAll(listaDias);
                    log.info("Datos de los días de la semana cargados desde el CSV.");
                } 
                else 
                {
                    return ResponseEntity.status(404).body("No se pudo cargar ningún dato desde el archivo CSV.");
                }
            }
            
            return ResponseEntity.ok(listaDias);

        } 
        catch (Exception exception) 
        {
            ReservaException reservaException = new ReservaException(3, "Error al obtener los días de la semana", exception);
            log.error("Error al obtener los días de la semana", reservaException);

            return ResponseEntity.status(500).body(reservaException.getBodyMesagge());
        }
    }
	
	
	/*
	 * end point de tipo get para mostar una lista con los tramos horarios
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/obtener_recurso")
	public ResponseEntity<?> obtenerTramosHorarios() throws Exception
	{
		
		try
		{
			List<TramosHorarios> listaTramos;
			
			listaTramos = tramoHorarioRepository.findAll();
			
			return ResponseEntity.ok(listaTramos);
		}
		catch (Exception exception) 
		{
			
			ReservaException reservaException = new ReservaException(4,"error al mostrar los tramos horarios", exception);
			log.error("error al mostrar los tramos horarios", reservaException);
			
			return ResponseEntity.status(403).body(reservaException.getBodyMesagge());
		}
	}
	
	/**
	 * end point de tipo post para realizar una reserva con un correo de un profesor, un
	 * recurso, un día de la semana, un tramo horario, un profesor y un número
	 * de alumnos
	 */
	
	@RequestMapping(method = RequestMethod.POST, value = "/reservarRecurso", consumes = {"application/json"})
	public ResponseEntity<?> reservarRecurso(@RequestHeader(value = "email") String email,
											 @RequestHeader(value = "profesor") String nombreProfesor,
											 @RequestHeader(value = "recurso") String aulaYCarritos,
											 @RequestHeader(value = "diaDeLaSemana") String diaDeLaSemana,
											 @RequestHeader(value = "tramoHorario") String tramoHorario,
											 @RequestHeader(value = "nAlumnos") int nAlumnos) throws Exception
	{
		try {
			
			Reserva reserva = new Reserva();
			ReservaId reservaId = new ReservaId();
			
			
			if(!reservaRepository.existsByProfesorEmail(email))
			{
				Profesor profesor = new Profesor();
				profesor.setEmail(email);
				reservaId.setEmail(profesor);
			}else
			{
				ReservaException reservaException = new ReservaException(10,"error al regitrar el email");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(410).body(reservaException.getBodyMesagge());
			}
			
			if(!reservaRepository.existsByProfesorNombre(nombreProfesor))
			{
				Profesor profesor = new Profesor();
				profesor.setEmail(nombreProfesor);
				reservaId.setEmail(profesor);
			}else
			{
				ReservaException reservaException = new ReservaException(11,"error al regitrar el nombre");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(411).body(reservaException.getBodyMesagge());
			}
			
			if(!reservaRepository.existsByAulaYCarritos(aulaYCarritos)) 
			{
				Recursos recurso = new Recursos();
				recurso.setAulaYCarritos(aulaYCarritos);
				reservaId.setAulaYCarritos(recurso);
			}else
			{
				ReservaException reservaException = new ReservaException(12,"error al regitrar los recursos");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(412).body(reservaException.getBodyMesagge());
			}
			
			if(!reservaRepository.existsByDiaDeLaSemana(diaDeLaSemana)) 
			{
				DiasSemana diaSemana = new DiasSemana();
				diaSemana.setDiasDeLaSemana(diaDeLaSemana);
				reservaId.setDiasDeLaSemana(diaSemana);
			}else
			{
				ReservaException reservaException = new ReservaException(13,"error al regitrar el dia de la semana");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(413).body(reservaException.getBodyMesagge());
			}
			
			if(!reservaRepository.existsByTramosHorarios(tramoHorario)) 
			{
				TramosHorarios tramoHorarioo = new TramosHorarios();
				tramoHorarioo.setTramos(tramoHorario);
				reservaId.setTramosHorarios(tramoHorarioo);
			}else
			{
				ReservaException reservaException = new ReservaException(14,"error al regitrar el tramoHorario");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(414).body(reservaException.getBodyMesagge());
			}
			
			
			if(!reservaRepository.existsByReservaNumeroAlumnos(nAlumnos)) 
			{
				Reserva reservaAlumnos = new Reserva();
				reservaAlumnos.setTramos(nAlumnos);
				reservaId.setTramosHorarios(reservaAlumnos);
			}else
			{
				ReservaException reservaException = new ReservaException(15,"error al regitrar el numero de alumnos");
				log.error("error al hacer la reserva", reservaException);
				
				return ResponseEntity.status(415).body(reservaException.getBodyMesagge());
			}
			
			reserva.setReservaId(reservaId);
			reservaRepository.save(reserva);		
			
			return ResponseEntity.ok(reserva);
			
		}
		catch (Exception exception) 
		{
			
			ReservaException reservaException = new ReservaException(4,"error al hacer la reserva", exception);
			log.error("error al hacer la reserva", reservaException);
			
			return ResponseEntity.status(405).body(reservaException.getBodyMesagge());
		}		
		
	}
	

}
