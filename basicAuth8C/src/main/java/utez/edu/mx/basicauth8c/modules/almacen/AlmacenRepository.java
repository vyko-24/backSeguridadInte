package utez.edu.mx.basicauth8c.modules.almacen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.basicauth8c.modules.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    Optional<Almacen> findByIdentificador(String identificador);
    List<Almacen> findByEncargado(User encargado);
}
