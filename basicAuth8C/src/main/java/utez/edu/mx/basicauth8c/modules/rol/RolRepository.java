package utez.edu.mx.basicauth8c.modules.rol;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends CrudRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}
