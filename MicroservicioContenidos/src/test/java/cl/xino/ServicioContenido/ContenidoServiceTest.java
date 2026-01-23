package cl.xino.ServicioContenido;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import cl.xino.ServicioContenido.models.entities.Contenido;
import cl.xino.ServicioContenido.models.requests.ContenidoRequest;
import cl.xino.ServicioContenido.repositories.ContenidoRepository;
import cl.xino.ServicioContenido.services.ContenidoService;

@ExtendWith(MockitoExtension.class)
public class ContenidoServiceTest {

    @Mock
    private ContenidoRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ContenidoService service;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "ubicacionMedia", tempDir.toString());
    }

    @Test
    void testGuardarContenido_Exito() throws IOException {

        Long idCurso = 1L;
        
        MockMultipartFile archivoFake = new MockMultipartFile(
            "archivo", 
            "tarea.pdf", 
            "application/pdf", 
            "Contenido de prueba".getBytes()
        );

        ContenidoRequest request = new ContenidoRequest();
        request.setIdCurso(idCurso);
        request.setTitulo("Tarea 1");
        request.setDescripcion("Descripcion prueba");
        request.setArchivo(archivoFake);


        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));


        when(repository.save(any(Contenido.class))).thenAnswer(i -> {
            Contenido c = (Contenido) i.getArguments()[0];
            c.setIdContenido(10L); 
            return c;
        });


        Contenido resultado = service.guardarContenido(request);


        assertNotNull(resultado);
        assertEquals("Tarea 1", resultado.getTitulo());
        

        String nombreArchivoGuardado = Paths.get(resultado.getRutaArchivo()).getFileName().toString();
        assertTrue(nombreArchivoGuardado.endsWith("tarea.pdf"));
        
        Path pathFisico = tempDir.resolve(nombreArchivoGuardado);
        assertTrue(Files.exists(pathFisico), "El archivo físico debe existir");
    }

    @Test
    void testGuardarContenido_FallaSiCursoNoExiste() {

        ContenidoRequest request = new ContenidoRequest();
        request.setIdCurso(99L);

        MockMultipartFile archivoFake = new MockMultipartFile("f", "t.txt", "text/plain", "b".getBytes());
        request.setArchivo(archivoFake);

        HttpClientErrorException excepcionExacta = HttpClientErrorException.create(
            HttpStatus.NOT_FOUND, 
            "Not Found", 
            org.springframework.http.HttpHeaders.EMPTY, 
            null, 
            null
        );

        when(restTemplate.getForEntity(anyString(), eq(Object.class)))
            .thenThrow(excepcionExacta);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardarContenido(request);
        });

        System.out.println("Mensaje de error recibido: " + exception.getMessage());

        assertTrue(exception.getMessage().contains("no existe"), "El mensaje debería decir que el curso no existe");
        
        verify(repository, never()).save(any());
    }

    @Test
    void testEliminarContenido_Exito() throws IOException {

        Long idContenido = 5L;
        String nombreArchivo = "borrar.mp4";
        

        Path archivoFisico = tempDir.resolve(nombreArchivo);
        Files.createFile(archivoFisico);

        Contenido contenidoSimulado = new Contenido();
        contenidoSimulado.setIdContenido(idContenido);
        contenidoSimulado.setRutaArchivo(archivoFisico.toString());

        when(repository.findById(idContenido)).thenReturn(Optional.of(contenidoSimulado));


        service.eliminarContenido(idContenido);


        verify(repository).deleteById(idContenido);
        assertFalse(Files.exists(archivoFisico), "El archivo debió ser borrado");
    }
}