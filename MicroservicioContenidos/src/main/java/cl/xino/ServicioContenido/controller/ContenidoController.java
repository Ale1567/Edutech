package cl.xino.ServicioContenido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import java.nio.file.Files;

import java.io.IOException;
import java.util.List;

import cl.xino.ServicioContenido.models.requests.ContenidoRequest;
import cl.xino.ServicioContenido.models.entities.Contenido;
import cl.xino.ServicioContenido.repositories.ContenidoRepository;
import cl.xino.ServicioContenido.services.ContenidoService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/contenidos")
@CrossOrigin(origins = "*")
public class ContenidoController {
    
    @Autowired
    private ContenidoService contenidoService;

    @Autowired
    private ContenidoRepository contenidoRepository;

    @PostMapping("/subir")
    public ResponseEntity<?> subirContenido(@ModelAttribute ContenidoRequest request){
        try {
            Contenido nuevoContenido = contenidoService.guardarContenido(request);
            return ResponseEntity.ok(nuevoContenido);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar el contenido: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error en la solicitud: " + e.getMessage());
        }
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<List<Contenido>> listarPorCurso(@PathVariable Long cursoId) {
        
        List<Contenido> lista = contenidoRepository.findByIdCurso(cursoId);
        
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            contenidoService.eliminarContenido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al borrar el archivo físico: " + e.getMessage());
        }
    }

    @GetMapping("/ver/{nombreArchivo:.+}")
    public ResponseEntity<Resource> verArchivo(@PathVariable String nombreArchivo) throws IOException {
        
        Resource recurso = contenidoService.cargarArchivoAsResource(nombreArchivo);

        String contentType = Files.probeContentType(recurso.getFile().toPath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }
    
}
