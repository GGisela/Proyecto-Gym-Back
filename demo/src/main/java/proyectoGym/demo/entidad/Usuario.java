package proyectoGym.demo.entidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data // Genera getters, setters, toString, equals y hashCode automáticamente gracias a Lombok
@NoArgsConstructor // Genera el constructor vacío obligatorio para JPA
@AllArgsConstructor // Genera un constructor con todos los atributos

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental en MySQL
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private String password; // - Nueva columna requerida para el login y registro

    @Column(nullable = false)
    private Boolean activo = true; // Por defecto, el usuario arranca activo
}
