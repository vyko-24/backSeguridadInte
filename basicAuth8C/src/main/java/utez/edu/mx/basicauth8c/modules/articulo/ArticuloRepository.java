package utez.edu.mx.basicauth8c.modules.articulo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    Optional<Articulo> findByNombre(String nombre);
    List<Articulo> findByAlmacenes(Almacen almacen);
}
