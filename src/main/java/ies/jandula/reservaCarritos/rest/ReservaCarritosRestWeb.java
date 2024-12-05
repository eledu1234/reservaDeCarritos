package ies.jandula.reservaCarritos.rest;

import java.util.List;
import java.util.Optional;

import javax.naming.ldap.Rdn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ies.jandula.reservaCarritos.exception.ReservaException;
import ies.jandula.reservaCarritos.models.DiasSemana;
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
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/cancelar_recurso")
	public ResponseEntity<?> cancelarRecurso(@RequestHeader(value = "email") String email,
											 @RequestHeader(value = "recurso") String aulaYCarritos,
											 @RequestHeader(value = "diaDeLaSemana") String diaDeLaSemana,
											 @RequestHeader(value = "tramoHorario") String tramoHorario){
		try {
			
			Recursos recurso = new Recursos();
			recurso.setAulaYCarritos(aulaYCarritos);
			
			
			DiasSemana diasSemana = new DiasSemana();
			diasSemana.setDiasDeLaSemana(diaDeLaSemana);
			
			
			TramosHorarios tramosHorarios = new TramosHorarios();
			tramosHorarios.setTramosHorarios(tramoHorario);

			
			ReservaId reservaId = new ReservaId();
			reservaId.setEmail(email);
			reservaId.setAulaYCarritos(recurso);
			reservaId.setDiasDeLaSemana(diasSemana);
			reservaId.setTramosHorarios(tramosHorarios);
			
			Reserva reservaABorrar = new Reserva();
			reservaABorrar.setReservaId(reservaId);
			
			// Verifica si existe la reserva 
			Optional<Reserva> optinalReserva = this.reservaRepository.encontrarReserva(email, aulaYCarritos, diaDeLaSemana, tramoHorario);
			
			if (!optinalReserva.isPresent()) {
				String mensajeError="El recurso no existe";
				log.error(mensajeError);
				throw new ReservaException(1, mensajeError);
	        }
			log.info("Se ha borrado correctamente");
			
			// Si la reserva existe la borra
			reservaRepository.delete(reservaABorrar);		
			
			return ResponseEntity.ok().build();
			
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
	@RequestMapping(method = RequestMethod.GET, value = "/obtener_tramos_horarios")
	public ResponseEntity<?> obtenerTramosHorarios() throws Exception
	{
		
		try
		{
			List<TramosHorarios> listaTramos;
			
			listaTramos = this.tramoHorarioRepository.findAll();
			
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/reservarRecurso")
	public ResponseEntity<?> reservarRecurso(@RequestHeader(value = "email") String email,
											 @RequestHeader(value = "profesor") String nombreProfesor,
											 @RequestHeader(value = "recurso") String aulaYCarritos,
											 @RequestHeader(value = "diaDeLaSemana") String diaDeLaSemana,
											 @RequestHeader(value = "tramosHorarios") String tramosHorarios,
											 @RequestHeader(value = "nAlumnos") int nAlumnos) throws Exception
	{
		try {
			
			Recursos recurso = new Recursos();
			recurso.setAulaYCarritos(aulaYCarritos);
			
			
			DiasSemana diasSemana = new DiasSemana();
			diasSemana.setDiasDeLaSemana(diaDeLaSemana);
			
			
			TramosHorarios tramos = new TramosHorarios();
			tramos.setTramosHorarios(tramosHorarios);

			
			ReservaId reservaId = new ReservaId();
			reservaId.setEmail(email);
			reservaId.setAulaYCarritos(recurso);
			reservaId.setDiasDeLaSemana(diasSemana);
			reservaId.setTramosHorarios(tramos);
			
			Reserva reserva = new Reserva();
			reserva.setReservaId(reservaId);
			reserva.setNombreProfesor(nombreProfesor);
			reserva.setNAlumnos(nAlumnos);
			
			// Verifica si existe la reserva 
			Optional<Reserva> optinalReserva = this.reservaRepository.encontrarReserva(email, aulaYCarritos, diaDeLaSemana, tramosHorarios);
			if (optinalReserva.isPresent()) {
				String mensajeError="Ya existe la reserva";
				log.error(mensajeError);
				throw new ReservaException(1, mensajeError);
	        }
			
			log.info("Se ha reservado correctamente");
			
			reserva.setReservaId(reservaId);
			this.reservaRepository.saveAndFlush(reserva);		
			
			return ResponseEntity.ok(reserva);
			
		}
		catch (ReservaException reservaException) 
		{
			
			return ResponseEntity.status(405).body(reservaException.getBodyMesagge());
		}		
		
	}
	

}
