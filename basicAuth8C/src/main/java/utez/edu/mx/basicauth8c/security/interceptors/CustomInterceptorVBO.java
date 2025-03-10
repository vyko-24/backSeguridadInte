package utez.edu.mx.basicauth8c.security.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class CustomInterceptorVBO implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = convertIPv6toIv4(request.getRemoteAddr());
        System.out.println("Revisando la direccion IP de la solicitud");
        System.out.println(ip);
        if(ip.startsWith("192.168")){
            System.out.println("La direccion esta bloqueada...");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "La direccion esta bloqueada.");
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("Cierre del interceptor");
    }

    private String convertIPv6toIv4(String ip) {
        try{
            InetAddress inetAddress = InetAddress.getByName(ip);
            byte[] address = inetAddress.getAddress();

            if(address.length == 4){
                return ip;
            }{
                if("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)){
                    return "127.0.0.1";
                }
            }
        }catch (UnknownHostException e){
            System.out.println("la direccion del host es desconocida");
            e.printStackTrace();
        }
        return ip;
    }
}