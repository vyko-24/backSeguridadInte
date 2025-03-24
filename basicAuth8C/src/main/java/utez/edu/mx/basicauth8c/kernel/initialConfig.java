package utez.edu.mx.basicauth8c.kernel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.basicauth8c.modules.almacen.Almacen;
import utez.edu.mx.basicauth8c.modules.almacen.AlmacenRepository;
import utez.edu.mx.basicauth8c.modules.articulo.Articulo;
import utez.edu.mx.basicauth8c.modules.articulo.ArticuloRepository;
import utez.edu.mx.basicauth8c.modules.categoria.Categoria;
import utez.edu.mx.basicauth8c.modules.categoria.CategoriaRepository;
import utez.edu.mx.basicauth8c.modules.rol.Rol;
import utez.edu.mx.basicauth8c.modules.rol.RolRepository;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

@Configuration
public class initialConfig implements CommandLineRunner {
    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final ArticuloRepository articuloRepository;
    private final AlmacenRepository almacenRepository;
    private final CategoriaRepository categoriaRepository;
    private final PasswordEncoder encoder;

    public initialConfig(RolRepository rolRepository, UserRepository userRepository, ArticuloRepository articuloRepository, AlmacenRepository almacenRepository, CategoriaRepository categoriaRepository, PasswordEncoder encoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.articuloRepository = articuloRepository;
        this.almacenRepository = almacenRepository;
        this.categoriaRepository = categoriaRepository;
        this.encoder = encoder;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public User getOrSaveUser(User user){
        User user1 = userRepository.findByUsername(user.getUsername());
        if(user1 == null){
            user1 = userRepository.save(user);
        }
        return user1;
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Rol getOrSaveRol(Rol rol){
        Optional<Rol> foundRol = rolRepository.findByNombre(rol.getNombre());
        return foundRol.orElseGet(() -> rolRepository.save(rol));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Articulo getOrSaveArticulo(Articulo articulo){
        Optional<Articulo> foundArticulo = articuloRepository.findByNombre(articulo.getNombre());
        return foundArticulo.orElseGet(() -> articuloRepository.save(articulo));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Categoria getOrSaveCategoria(Categoria categoria){
        Optional<Categoria> foundCategoria = categoriaRepository.findByNombre(categoria.getNombre());
        return foundCategoria.orElseGet(() -> categoriaRepository.save(categoria));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public Almacen getOrSaveAlmacen(Almacen almacen){
        Optional<Almacen> foundAlmacen = almacenRepository.findByIdentificador(almacen.getIdentificador());
        return foundAlmacen.orElseGet(() -> almacenRepository.save(almacen));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public void saveAlmacenAndArticulo(Long idAlmacen, Long idArticulo){
        Optional<Almacen> almacen = almacenRepository.findById(idAlmacen);
        Optional<Articulo> articulo = articuloRepository.findById(idArticulo);
        if(almacen.isPresent() && articulo.isPresent()){
            Almacen almacen1 = almacen.get();
            Articulo articulo1 = articulo.get();
            if (almacen1.getArticulos().contains(articulo1)) {
                return;
            }
            almacen1.getArticulos().add(articulo.get());
            almacenRepository.save(almacen1);
        }
    }


    @Override
    @Transactional(rollbackFor = {SQLException.class})
    public void run(String... args) throws Exception {
        Rol admin = getOrSaveRol(new Rol("ADMIN"));
        Rol responsable = getOrSaveRol(new Rol("RESPONSABLE"));

        User userAdmin = getOrSaveUser(new User("admin@gmail.com","Víctor","Barrera","Viko",encoder.encode("1234"),admin));
        User userResponsable = getOrSaveUser(new User( "responsable@gmail.com","Vale","Hernandez","Titian",encoder.encode("1234"),responsable));

        Categoria categoria = getOrSaveCategoria(new Categoria("Electronica"));
        Articulo articulo1 = getOrSaveArticulo(new Articulo("Laptop","Laptop HP",categoria));
        Articulo articulo2 = getOrSaveArticulo(new Articulo("Tablet","Tablet HP",categoria));
        Articulo articulo3 = getOrSaveArticulo(new Articulo("Iphone","El diavle",categoria));
        Almacen almacen = getOrSaveAlmacen(new Almacen(categoria, userResponsable, "A1"));

        saveAlmacenAndArticulo(almacen.getId(), articulo1.getId());
        saveAlmacenAndArticulo(almacen.getId(), articulo2.getId());
        saveAlmacenAndArticulo(almacen.getId(), articulo3.getId());
    }
}
