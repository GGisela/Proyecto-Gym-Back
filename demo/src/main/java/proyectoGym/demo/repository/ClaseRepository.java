package proyectoGym.demo.repository;
import proyectoGym.demo.entidad.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    // Ya tenemos el CRUD básico heredado
    List<Clase> findByUsuariosInscritosId(Long usuarioId);
}