package utez.edu.mx.basicauth8c.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import utez.edu.mx.basicauth8c.security.filters.AuthFilter;
import utez.edu.mx.basicauth8c.security.interceptors.CustomInterceptorVBO;
import utez.edu.mx.basicauth8c.security.token.JwtAuthentication;
import utez.edu.mx.basicauth8c.security.token.UserDetailsServiceImpl;

//BA03: Crear objeto de configuracion de seguridad para proteger rutas
@Configuration
@EnableWebSecurity
public class MainSecurity implements WebMvcConfigurer {

    // Bloque para ejecutar repos, filtros, interceptores, etc
    @Autowired
    private AuthFilter authFilter;

    private final UserDetailsServiceImpl service;

    public MainSecurity(UserDetailsServiceImpl service) {
        this.service = service;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(service);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthentication filter(){
        return new JwtAuthentication();
    }


    @Autowired
    private CustomInterceptorVBO customInterceptor;

    private final static String[] WHITE_LIST ={
            "/api/test",
            "/api/auth/login",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
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
            "/api/bitacora/**",
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

        http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST).permitAll()
                        .requestMatchers(MERGE_LIST).hasAnyAuthority("ADMIN", "RESPONSABLE")
                        .requestMatchers(ADMIN_LIST).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                .logout(out -> out.logoutUrl("/api/auth/logout").clearAuthentication(true));
        return http.build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor).addPathPatterns("/api/test/secured");
    }
}

// Siguiente -> crear nuestro filtro personalizado
