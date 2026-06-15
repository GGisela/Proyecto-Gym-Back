package proyectoGym.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoGym.demo.entidad.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
// Al heredar de JpaRepository, ya tenemos gratis los métodos:
// save(), findById(), findAll(), deleteById(), etc.
}