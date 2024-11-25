package ies.jandula.reservaCarritos.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;

import ies.jandula.reservaCarritos.exception.ReservaException;
import ies.jandula.reservaCarritos.models.DiasSemana;

public class Methods 
{
	
	// Método para cargar los datos desde un archivo CSV utilizando Scanner
    public List<DiasSemana> cargarDatosCSV() 
    {
        List<DiasSemana> diasSemanaList = new ArrayList<>();

        try 
        {
            File file = new File("src/main/resources/dias_semana.csv");

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                
                if (!line.isEmpty()) 
                { 
                    DiasSemana diasSemana = new DiasSemana();
                    
                    diasSemana.setDiasDeLaSemana(line); 
                    
                    diasSemanaList.add(diasSemana);
                }
            }
            scanner.close();
            
        } 
        catch (FileNotFoundException fileNotFoundException)
        {
        	ReservaException reservaException = new ReservaException(3, "Error al obtener los días de la semana", fileNotFoundException);
            
            return (List<DiasSemana>) ResponseEntity.status(400).body(reservaException.getBodyMesagge());
        }

        return diasSemanaList;
    }

}
