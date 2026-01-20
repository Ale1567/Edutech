package cl.xino.ServicioContenido.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Entity
@Data
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContenido;

    private String titulo;
    private String descripcion;
    
    private String rutaArchivo;
    private String nombreOriginal;
    private String tipoContenido;
    private Long tamanioBytes;

    private Long idCurso;
    private LocalDateTime fechaSubida;

    @PrePersist
    void prePersist(){
        this.fechaSubida = LocalDateTime.now();
    }
}
