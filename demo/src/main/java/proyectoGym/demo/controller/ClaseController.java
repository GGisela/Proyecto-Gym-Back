package proyectoGym.demo.controller;
import proyectoGym.demo.entidad.Clase;
import proyectoGym.demo.entidad.Usuario;
import proyectoGym.demo.service.ClaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clases")

public class ClaseController {
    private final ClaseService claseService;

    public ClaseController(ClaseService claseService) {
        this.claseService = claseService;
    }

    // 1. CREAR CLASE: POST http://localhost:8080/api/clases
    @PostMapping
    public ResponseEntity<Clase> crearClase(@RequestBody Clase clase) {
        return ResponseEntity.ok(claseService.guardarClase(clase));
    }

    // 2. VER TODAS LAS CLASES: GET http://localhost:8080/api/clases
    @GetMapping
    public ResponseEntity<List<Clase>> obtenerTodas() {
        return ResponseEntity.ok(claseService.obtenerTodas());
    }

    // 3. INSCRIBIR USUARIO A CLASE: POST http://localhost:8080/api/clases/{claseId}/inscribir/{usuarioId}
    @PostMapping("/{claseId}/inscribir/{usuarioId}")
    public ResponseEntity<?> inscribirUsuario(@PathVariable Long claseId, @PathVariable Long usuarioId) {
        try {
            Clase claseActualizada = claseService.inscribirUsuario(claseId, usuarioId);
            return ResponseEntity.ok(claseActualizada);
        } catch (RuntimeException e) {
            // Si salta el error de cupo lleno o ID no encontrado, devolvemos un error 400 (Bad Request) con el mensaje
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //--- se agregan metodos para eliminar la clase
    // 4. ELIMINAR CLASE: DELETE http://localhost:8080/api/clases/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarClase(@PathVariable Long id) {
        try {
            claseService.eliminarClase(id);
            return ResponseEntity.ok("Clase eliminada correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. VER ALUMNOS DE UNA CLASE: GET http://localhost:8080/api/clases/{claseId}/alumnos
    @GetMapping("/{claseId}/alumnos")
    public ResponseEntity<List<Usuario>> obtenerAlumnos(@PathVariable Long claseId) {
        try {
            return ResponseEntity.ok(claseService.obtenerAlumnosPorClase(claseId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
