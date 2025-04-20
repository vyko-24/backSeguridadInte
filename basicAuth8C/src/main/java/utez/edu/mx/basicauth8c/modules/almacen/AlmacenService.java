package utez.edu.mx.basicauth8c.modules.almacen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.kernel.CustomResponse;
import utez.edu.mx.basicauth8c.modules.articulo.Articulo;
import utez.edu.mx.basicauth8c.modules.articulo.ArticuloRepository;
import utez.edu.mx.basicauth8c.modules.bitacora.Bitacora;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraRepository;
import utez.edu.mx.basicauth8c.modules.bitacora.BitacoraService;
import utez.edu.mx.basicauth8c.modules.categoria.Categoria;
import utez.edu.mx.basicauth8c.modules.categoria.CategoriaRepository;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AlmacenService {
    @Autowired
    private AlmacenRepository repository;

    @Autowired
    private CustomResponse customResponse;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getAll(){
        bitacoraService.registrarBitacora("GET", "almacen", null, null);
        return customResponse.getJSONResponse(repository.findAll());
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getById(Long id){
        bitacoraService.registrarBitacora("GET", "almacen", null, null);
        if(!repository.existsById(id))
            return customResponse.getBadRequest("Almacen no encontrado");
        return customResponse.getJSONResponse(repository.findById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getByEncargado(Long id){
        bitacoraService.registrarBitacora("GET", "almacen", null, null);
        Optional<User> encargado = userRepository.findById(id);
        if(encargado.isEmpty())
            return customResponse.getBadRequest("Usuario no encontrado");
        User encargadoAux = encargado.get();
        Optional<Almacen> foundAlmacen = repository.findByEncargado(encargadoAux);
        if(foundAlmacen.isEmpty())
            return customResponse.getBadRequest("El usuario no cuenta con ningún alamacen");
        return customResponse.getJSONResponse(repository.findByEncargado(encargadoAux));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> save(Almacen almacen){
        if (repository.findByIdentificador(almacen.getIdentificador()).isPresent())
            return customResponse.getBadRequest("Almacen ya registrado");
        User encargado = userRepository.findById(almacen.getEncargado().getId()).get();
        if (encargado == null)
            return customResponse.getBadRequest("Usuario no encontrado");
        Categoria categoria = categoriaRepository.findById(almacen.getCategoria().getId()).get();
        if (categoria == null)
            return customResponse.getBadRequest("Categoria no encontrada");
        bitacoraService.registrarBitacora("POST", "almacen", null, almacen);
        return customResponse.getJSONResponse(repository.save(almacen));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> update(Almacen almacen, Long id){
        Optional<Almacen> almacenFound = repository.findById(id);
        if(almacenFound.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Optional<User> encargadoFound = userRepository.findById(almacen.getEncargado().getId());
        if (encargadoFound.isEmpty())
            return customResponse.getBadRequest("Usuario no encontrado");
        if(encargadoFound.get().getAlmacen()!= null)
            return customResponse.getBadRequest("El usuario ya tiene un almacen asignado");
        Optional<Categoria> categoria = categoriaRepository.findById(almacen.getCategoria().getId());
        if (categoria.isEmpty())
            return customResponse.getBadRequest("Categoria no encontrada");
        Almacen almacen1 = almacenFound.get();
        Almacen copiaAntes = almacenFound.get();
        almacen1.setIdentificador(almacen.getIdentificador());
        almacen1.setEncargado(almacen.getEncargado());
        almacen1.setCategoria(almacen.getCategoria());
        repository.save(almacen1);

        bitacoraService.registrarBitacora(
                "PUT",
                "almacen",
                copiaAntes,
                almacen1
        );
        return customResponse.getJSONResponse(almacen1);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> delete(Long id){
        Optional<Almacen> almacenFound = repository.findById(id);
        if(almacenFound.isEmpty())
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacen = almacenFound.get();
        if (almacen.getArticulos() != null && !almacen.getArticulos().isEmpty())
            return customResponse.getBadRequest("El almacén tiene artículos asignados. Por favor elimine los artículos o cambie de almacén");
        repository.deleteById(id);
        // Registrar la eliminación en la bitácora
        bitacoraService.registrarBitacora(
                "DELETE",
                "almacen",
                almacen,
                null
        );
        return customResponse.getJSONResponse("Almacen eliminado");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createArticulo(Articulo articulo, Long idAlmacen) {
        Optional<Articulo> foundArticulo = articuloRepository.findByNombre(articulo.getNombre());
        if (foundArticulo.isPresent() && articuloRepository.existsById(foundArticulo.get().getId()))
            return customResponse.getBadRequest("Artículo ya registrado");
        Optional<Almacen> almacenFound = repository.findById(idAlmacen);
        if (almacenFound.isEmpty())
            return customResponse.getBadRequest("Almacén no encontrado");

        Almacen almacen = almacenFound.get();

        // Asignar la categoría del almacén al artículo (si es necesario)
        articulo.setCategoria(almacen.getCategoria());

        // Guardar el artículo primero para obtener su ID generado
        articulo = articuloRepository.save(articulo);

        // Agregar el artículo a la lista del almacén y guardar
        almacen.getArticulos().add(articulo);
        repository.save(almacen);
        // Registrar la creación del artículo en la bitácora
        bitacoraService.registrarBitacora(
                "POST",
                "articulo",
                null,
                articulo
        );
        return customResponse.getJSONResponse(almacen);
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> removeArticulo(Long idAlmacen, Long idArticulo) {
        if (!repository.existsById(idAlmacen))
            return customResponse.getBadRequest("Almacen no encontrado");
        Almacen almacen = repository.findById(idAlmacen).get();
        Optional<Articulo> articuloFound = articuloRepository.findById(idArticulo);
        if (articuloFound.isEmpty())
            return customResponse.getBadRequest("Articulo no encontrado");
        Articulo articulo = articuloFound.get();
        almacen.getArticulos().remove(articulo);

        // Registrar la eliminación del artículo en la bitácora
        bitacoraService.registrarBitacora(
                "DELETE",
                "articulo",
                articulo,
                null
        );
        return customResponse.getJSONResponse(repository.save(almacen));
    }

}
