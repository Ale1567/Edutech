package com.example.inscripciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inscripciones.models.dto.InscripcionDetalleDto;
import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.models.request.ProgresoRequest;
import com.example.inscripciones.services.InscripcionService;


@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    // GET: Listar todo (Pasamanos directo al servicio)
    @GetMapping
    public ResponseEntity<List<Inscripcion>> obtenerTodas() {
        return ResponseEntity.ok(inscripcionService.obtenerTodas());
    }

    // GET: Dashboard del alumno
    @GetMapping("/mis-cursos/{idEstudiante}")
    public ResponseEntity<List<InscripcionDetalleDto>> obtenerCursosDelEstudiante(@PathVariable int idEstudiante) {
        // El Controller no sabe qué es un curso ni cómo se busca. Solo delega.
        return ResponseEntity.ok(inscripcionService.obtenerCursosDeEstudiante(idEstudiante));
    }

    // POST: Crear inscripción
    @PostMapping("/crear")
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody AgregarInscripcion request) {
        // Validaciones de si "ya existe" o "si el curso es real" ocurren en el Service, no aquí.
        return ResponseEntity.ok(inscripcionService.registrarInscripcion(request));
    }

    // PUT: Actualizar progreso
    // Usamos ProgresoRequest para que el Controller no tenga que "extraer" el double manualmente.
    @PutMapping("/progreso/{idInscripcion}")
    public ResponseEntity<Inscripcion> actualizarProgreso(
            @PathVariable Long idInscripcion, 
            @RequestBody ProgresoRequest request) {
        
        return ResponseEntity.ok(
            inscripcionService.actualizarProgreso(idInscripcion, request.getPorcentaje())
        );
    }

    // DELETE: Eliminar
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        // El servicio se encarga de verificar si existe o lanzar excepción.
        return ResponseEntity.ok(inscripcionService.eliminar(id));
    }
}