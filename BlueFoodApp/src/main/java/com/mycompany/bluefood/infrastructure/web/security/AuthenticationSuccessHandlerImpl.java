
package com.mycompany.bluefood.infrastructure.web.security;

import com.mycompany.bluefood.util.SecurityUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
      Role role = SecurityUtils.LoggedUser().getRole();
      
      if(null == role){
          throw new IllegalStateException("Erro na autenticação");
      }else switch (role) {
            case CLIENTE:
                response.sendRedirect("cliente/home");
                break;
            case RESTAURANTE:
                response.sendRedirect("restaurante/home");
                break;
            default:
                throw new IllegalStateException("Erro na autenticação");
        }
    }
    
}
