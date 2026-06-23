
package proyectoGym.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proyectoGym.demo.entidad.Clase;
import proyectoGym.demo.entidad.Usuario;
import proyectoGym.demo.repository.ClaseRepository;
import proyectoGym.demo.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    // Atributos finales (Inmutabilidad gracias a la inyección por constructor)
    private final UsuarioRepository usuarioRepository;
    private final ClaseRepository claseRepository;

    // Constructor único para la inyección de dependencias
    public UsuarioService(UsuarioRepository usuarioRepository, ClaseRepository claseRepository) {
        this.usuarioRepository = usuarioRepository;
        this.claseRepository = claseRepository;
    }

    // 1. Crear o Guardar Usuario (C) - Guarda directo lo que viene del Front (incluyendo password)
    @Transactional
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

    // 4. Actualizar Usuario (U) - Ahora actualiza también la contraseña si viene una nueva
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario datosNuevos) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(datosNuevos.getNombre());
            usuario.setApellido(datosNuevos.getApellido());
            usuario.setEmail(datosNuevos.getEmail());
            usuario.setTelefono(datosNuevos.getTelefono());
            usuario.setActivo(datosNuevos.getActivo());

            // Si el front manda una contraseña, la actualizamos
            if (datosNuevos.getPassword() != null && !datosNuevos.getPassword().isEmpty()) {
                usuario.setPassword(datosNuevos.getPassword());
            }

            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    // 5. Borrado Lógico (D) - Cambiamos el estado a false sin eliminar de la BD
    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
        });
    }

    // 6. Obtener las clases del Socio para su perfil
    public List<Clase> obtenerMisClases(Long usuarioId) {
        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + usuarioId);
        }
        return claseRepository.findByUsuariosInscritosId(usuarioId);
    }
}