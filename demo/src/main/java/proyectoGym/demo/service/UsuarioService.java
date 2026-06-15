package proyectoGym.demo.service;
import proyectoGym.demo.entidad.Usuario;
import proyectoGym.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    // Inyección de dependencias por constructor (Buena práctica de ingeniería)
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Crear o Guardar Usuario (C)
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // 2. Obtener todos los usuarios (R)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // 3. Obtener usuario por ID (R)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // 4. Actualizar Usuario (U)
    public Usuario actualizarUsuario(Long id, Usuario datosNuevos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(datosNuevos.getNombre());
            usuario.setApellido(datosNuevos.getApellido());
            usuario.setEmail(datosNuevos.getEmail());
            usuario.setTelefono(datosNuevos.getTelefono());
            usuario.setActivo(datosNuevos.getActivo());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // 5. Borrado Lógico (D) - En vez de borrarlo de la base de datos, lo inactivamos
    public void eliminarUsuario(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setActivo(false); // Cambiamos el boolean a false
            usuarioRepository.save(usuario);
        });
    }
}
