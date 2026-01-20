package cl.xino.catalogoCursos.controller;



import cl.xino.catalogoCursos.models.entities.Curso;
import cl.xino.catalogoCursos.models.requests.CursoCompletoRequest;
import cl.xino.catalogoCursos.models.requests.CursoRequest;
import cl.xino.catalogoCursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // GET: Listar todos
    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.obtenerTodos();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> obtenerCurso(@PathVariable Long id) {
        return cursoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Crear curso
    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody CursoRequest request){
        Curso cursoNuevo = new Curso();

        cursoNuevo.setTitulo(request.getTitulo());
        cursoNuevo.setDescripcion(request.getDescripcion());
        cursoNuevo.setCategoria(request.getCategoria());
        cursoNuevo.setPrecio(request.getPrecio());
        cursoNuevo.setInstructorId(request.getInstructorId());
        cursoNuevo.setFechaInicio(request.getFechaInicio());
        cursoNuevo.setFechaFin(request.getFechaFin());

        Curso cursoGuardado = cursoService.guardarCurso(cursoNuevo);
        return ResponseEntity.ok(cursoGuardado);


    }

    // DELETE: Eliminar curso
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }


    // GET: Obtener curso completo con contenidos
    @GetMapping("/completo/{id}")
    public ResponseEntity<?> obtenerCursoCompleto(@PathVariable Long id) {
        try {
            CursoCompletoRequest resultado = cursoService.obtenerCursoConContenidos(id);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error de comunicación: " + e.getMessage());
        }
    }
}