package ies.jandula.reservaCarritos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ies.jandula.reservaCarritos.dto.ReservaDto;
import ies.jandula.reservaCarritos.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public List<ReservaDto> obtenerReservasPorRecurso(String recurso) 
    {
        return reservaRepository.findReservasByRecurso(recurso);
    }
}

