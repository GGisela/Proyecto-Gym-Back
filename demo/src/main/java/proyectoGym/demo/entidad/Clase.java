package proyectoGym.demo.entidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre; // "Yoga" o "Spinning"

    @Column(nullable = false, length = 50)
    private String horario;

    @Column(nullable = false)
    private Integer cupoMaximo;


    @ManyToMany
    private List<Usuario> usuariosInscritos;
}
