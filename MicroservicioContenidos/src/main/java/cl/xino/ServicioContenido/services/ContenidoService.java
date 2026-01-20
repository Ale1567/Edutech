package cl.xino.ServicioContenido.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import cl.xino.ServicioContenido.repositories.ContenidoRepository;
import cl.xino.ServicioContenido.models.requests.ContenidoRequest;
import cl.xino.ServicioContenido.models.entities.Contenido;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository repositorio;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${media.location}")
    private String ubicacionMedia;

    public Contenido guardarContenido(ContenidoRequest request) throws IOException {
        

        Long cursoId = request.getIdCurso();
        String urlCursos = "http://localhost:8080/api/cursos/" + cursoId;
        // Validar existencia del curso
        try {
            restTemplate.getForEntity(urlCursos, Object.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Error: El curso con ID " + cursoId + " no existe. Cancelando subida.");
        } catch (Exception e) {
            throw new RuntimeException("Error de conexión: No se pudo verificar la existencia del curso.");
        }

        MultipartFile archivo = request.getArchivo();

        // Validar que no venga vacío
        if (archivo.isEmpty()) {
            throw new RuntimeException("El archivo está vacío");
        }


        // Generar nombre unico para el archivo subido
        String nombreUnico = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();
        
        Path rutaBase = Paths.get(ubicacionMedia);
        if (!Files.exists(rutaBase)) {
            Files.createDirectories(rutaBase); 
        }
        
        Path rutaFinal = rutaBase.resolve(nombreUnico);

        Files.copy(archivo.getInputStream(), rutaFinal);

        Contenido contenidoNuevo = new Contenido();
        contenidoNuevo.setTitulo(request.getTitulo());
        contenidoNuevo.setDescripcion(request.getDescripcion());
        contenidoNuevo.setIdCurso(request.getIdCurso());
        
        contenidoNuevo.setRutaArchivo(rutaFinal.toString());
        contenidoNuevo.setNombreOriginal(archivo.getOriginalFilename());
        contenidoNuevo.setTipoContenido(archivo.getContentType());
        contenidoNuevo.setTamanioBytes(archivo.getSize());

        return repositorio.save(contenidoNuevo);
    }

    public void eliminarContenido(Long id) throws IOException {
        Contenido contenido = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("El contenido con ID " + id + " no existe"));

        Path rutaArchivo = Paths.get(contenido.getRutaArchivo());
        try {
            Files.deleteIfExists(rutaArchivo);
        } catch (IOException e) {
            System.err.println("No se pudo borrar el archivo físico: " + e.getMessage());
        }

        repositorio.deleteById(id);
    }

    public Resource cargarArchivoAsResource(String nombreArchivo) throws MalformedURLException {
        Path rutaArchivo = Paths.get(ubicacionMedia).resolve(nombreArchivo);
        Resource recurso = new UrlResource(rutaArchivo.toUri());

        if (recurso.exists() || recurso.isReadable()) {
            return recurso;
        } else {
            throw new RuntimeException("No se puede leer el archivo: " + nombreArchivo);
        }
    }
}