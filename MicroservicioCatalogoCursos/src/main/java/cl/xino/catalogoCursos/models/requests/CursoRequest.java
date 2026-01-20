package cl.xino.catalogoCursos.models.requests;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CursoRequest {
    
    private String titulo;
    private String descripcion;
    private String categoria;
    private int precio;
    private int instructorId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}