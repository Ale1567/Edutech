package com.example.inscripciones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.inscripciones.models.dto.InscripcionDetalleDto;
import com.example.inscripciones.models.entities.Inscripcion;
import com.example.inscripciones.models.request.AgregarInscripcion;
import com.example.inscripciones.models.request.ProgresoRequest;
import com.example.inscripciones.services.InscripcionService;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @GetMapping
    public ResponseEntity<List<Inscripcion>> obtenerTodas() {
        List<Inscripcion> lista = inscripcionService.obtenerTodas();
        return ResponseEntity.ok(lista);
    }


    @GetMapping("/mis-cursos/{idEstudiante}")
    public ResponseEntity<List<InscripcionDetalleDto>> obtenerCursosDelEstudiante(@PathVariable int idEstudiante) {
        return ResponseEntity.ok(inscripcionService.obtenerCursosDeEstudiante(idEstudiante));
    }


    @PostMapping("/crear")
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody AgregarInscripcion request) {
        Inscripcion nueva = inscripcionService.registrarInscripcion(request);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }


    @PutMapping("/progreso/{idInscripcion}")
    public ResponseEntity<Inscripcion> actualizarProgreso(
            @PathVariable Long idInscripcion, 
            @RequestBody ProgresoRequest request) {
        
        Inscripcion actualizada = inscripcionService.actualizarProgreso(idInscripcion, request.getPorcentaje());
        return ResponseEntity.ok(actualizada);
    }

    // --- 5. ELIMINAR ---
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarInscripcion(@PathVariable Long id) {
        String mensaje = inscripcionService.eliminar(id);
        return ResponseEntity.ok(mensaje);
    }
}