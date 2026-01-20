package cl.xino.ServicioContenido.models.requests;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ContenidoRequest {

    private String titulo;
    private String descripcion;
    private Long idCurso;

    private MultipartFile archivo;
    
}
