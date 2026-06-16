package proyectoGym.demo.service;
import proyectoGym.demo.entidad.Clase;
import proyectoGym.demo.entidad.Usuario;
import proyectoGym.demo.repository.ClaseRepository;
import proyectoGym.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public class ClaseService {
    private final ClaseRepository claseRepository;
    private final UsuarioRepository usuarioRepository;

    // Inyección de ambos repositorios por constructor
    public ClaseService(ClaseRepository claseRepository, UsuarioRepository usuarioRepository) {
        this.claseRepository = claseRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Crear una nueva clase (Yoga/Spinning)
    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    // 2. Obtener todas las clases
    public List<Clase> obtenerTodas() {
        return claseRepository.findAll();
    }

    // 3. LOGICA CENTRAL: Inscribir un usuario a una clase
    public Clase inscribirUsuario(Long claseId, Long usuarioId) {
        // Buscamos la clase, si no existe lanzamos error
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));

        // Buscamos al usuario, si no existe lanzamos error
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        // Regla de negocio: Validar si hay cupo disponible
        if (clase.getUsuariosInscritos().size() >= clase.getCupoMaximo()) {
            throw new RuntimeException("Lo sentimos, la clase de " + clase.getNombre() + " ya está llena.");
        }

        // Si pasa la validación, lo agregamos a la lista
        clase.getUsuariosInscritos().add(usuario);

        // Guardamos la clase actualizada (Hibernate se encarga de llenar la tabla intermedia)
        return claseRepository.save(clase);
    }

    // 4. Eliminar una clase por ID
    public void eliminarClase(Long id) {
        if (!claseRepository.existsById(id)) {
            throw new RuntimeException("No se encontró la clase con ID: " + id);
        }
        claseRepository.deleteById(id);
    }

    // 5. Ver los alumnos inscritos en una clase específica
    public List<Usuario> obtenerAlumnosPorClase(Long claseId) {
        Clase clase = claseRepository.findById(claseId)
                .orElseThrow(() -> new RuntimeException("Clase no encontrada con ID: " + claseId));
        return clase.getUsuariosInscritos();
    }
}
