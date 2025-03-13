package utez.edu.mx.basicauth8c.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import utez.edu.mx.basicauth8c.modules.user.UserRepository;
import utez.edu.mx.basicauth8c.modules.user.User;
import utez.edu.mx.basicauth8c.security.MainSecurity;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

// BA04: craer el filtro customizado para MainSecurity
@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository useRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
/*
        //Bloque de ejecucion antes del filtro
        System.out.println("Antes de ejecutar el filtro...");

        //Ejecucion del filtro
        filterChain.doFilter(request, response);


        //Bloque de ejecucion despues del filtro (este puede o no ejecutarse)
        System.out.println("Despues de ejecutar el filtro...");
*/
        final String AUTH_HEADER = request.getHeader("Authorization");
        Set<String> WHITE_LIST = Arrays.stream(MainSecurity.getWHITE_LIST()).collect(Collectors.toSet());
        String token;
        User user = null;

        if(!WHITE_LIST.contains(request.getRequestURI())) {
            System.out.println("Método de la solicitud: "+ request.getMethod());
            System.out.println("Ruta a la que se quiere acceder: "+request.getRequestURI());
            System.out.println("verificando los encabezados de la solicitud...");
            if (AUTH_HEADER != null && AUTH_HEADER.startsWith("Bearer ")) {
                token = AUTH_HEADER.substring(7);
                System.out.println("Token recibido: " + token);
                user = useRepository.findByUsername(token.split("\\.")[1]);
                System.out.println("Verificando que el usuario exista");
                if (user != null && token != null){
                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRol().getNombre()));
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUsername(), null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("El token de seguridad se ha registrado");
                }else{
                    System.out.println("El usuario no existe...");
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "El usuario no existe");
                    return;
                }
                System.out.println("Usuario encontrado: " + user);
            } else {
                System.out.println("El usuario no tiene autorización");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Sin autorización");
                return;
            }
        }else{
            System.out.println("La ruta solicitada está dentro de la white_list...");
        }
        filterChain.doFilter(request, response);
        System.out.println("Cierre del filtro AuthFilter");
    }
}

// siguiente -> Implementar el filtro en el objeto de seguridad en MainSecurity

