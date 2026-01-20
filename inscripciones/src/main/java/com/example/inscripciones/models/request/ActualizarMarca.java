package com.example.inscripciones.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

//optiene la id incripcion para poder actualzar
public class ActualizarMarca {

    @NotNull(message = "Ingrese Id inscrion para actualizar")
    private Integer id_inscripcion;

    private Integer id_estudiante;
    private Integer id_curso;
    
}
