package proyectoGym.demo.controller;
import proyectoGym.demo.entidad.Usuario;
import proyectoGym.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios") // URL base para todos los endpoints de este controlado
public class UsuarioController {
    private final UsuarioService usuarioService;

    // Inyección del servicio por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 1. CREAR: POST http://localhost:8080/api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    // 2. LEER TODOS: GET http://localhost:8080/api/usuarios
  @GetMapping("http://localhost:8080/api/usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // 3. LEER UNO POR ID: GET http://localhost:8080/api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 4. ACTUALIZAR: PUT http://localhost:8080/api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario datosNuevos) {
        try {
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(id, datosNuevos);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. BORRAR (Lógico): DELETE http://localhost:8080/api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build(); // Devuelve un estado 204 (Sin contenido)
    }
}
