package utez.edu.mx.basicauth8c.modules.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user WHERE username= :username AND password = :password", nativeQuery = true)
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query(value = "SELECT * FROM user WHERE username= :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);
}
