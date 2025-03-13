package utez.edu.mx.basicauth8c.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import utez.edu.mx.basicauth8c.security.filters.AuthFilter;
import utez.edu.mx.basicauth8c.security.interceptors.CustomInterceptorVBO;

//BA03: Crear objeto de configuracion de seguridad para proteger rutas
@Configuration
@EnableWebSecurity
public class MainSecurity implements WebMvcConfigurer {

    // Bloque para ejecutar repos, filtros, interceptores, etc
    @Autowired
    private AuthFilter authFilter;

    @Autowired
    private CustomInterceptorVBO customInterceptor;

    private final static String[] WHITE_LIST ={
            "/api/test",
            "/api/auth/login",
    };

    private final static String[] ADMIN_LIST ={
            "/api/user/**",
            "/api/categoria/**",
            "/api/articulo/**",
            "/api/almacen/get/**",
            "/api/almacen/save/",
            "/api/almacen/update/**",
            "/api/almacen/delete/**",
            "/api/auth/regresarContrasena/**",
    };
    private final static String[] MERGE_LIST ={
            "/api/almacen/createArticulo/**",
            "/api/articulo/**",
            "/api/auth/updatePassword/**",
    };

    public static String[] getMERGE_LIST() {
        return MERGE_LIST;
    }

    public static String[] getADMIN_LIST() {
        return ADMIN_LIST;
    }

    public static String[] getWHITE_LIST() {
        return WHITE_LIST;
    }

    public void setCustomFilter(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    // Bloque del objeto de configuración de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth

                        // BA07: Crear la configuracion de las rutas
                                .requestMatchers(WHITE_LIST).permitAll()
                                .requestMatchers(MERGE_LIST).hasAnyRole("ADMIN", "RESPONSABLE")
                                .requestMatchers(ADMIN_LIST).hasRole("ADMIN")
                                .anyRequest().authenticated()


                // BA05: Implementar el filtro en el objeto de seguridad
                ).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
                /* /\ Aqui van los filtros (ya sean after o before)*/
                /* |*/

                // Siguiente -> Crear un controller para testear el objeto de seguridad

        return http.build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor).addPathPatterns("/api/test/secured");
    }
}

// Siguiente -> crear nuestro filtro personalizado
