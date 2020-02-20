
package com.mycompany.bluefood.util;

import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import com.mycompany.bluefood.infrastructure.web.security.LoggedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    
    public static LoggedUser LoggedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            return null;
        }
        return (LoggedUser) authentication.getPrincipal();
    }
    
    public static Cliente loggedCliente(){
        LoggedUser loggedUser = LoggedUser();
        
        if(loggedUser == null){
            throw new IllegalStateException("Não existe um usuário logado");
        }
        if(!(loggedUser.getUsuario() instanceof Cliente)){
            throw new IllegalStateException("O usuário logado não é um cliente");
        }
        return (Cliente) loggedUser.getUsuario();
    }
    
    public static Restaurante loggedRestaurante(){
        LoggedUser loggedUser = LoggedUser();
        
        if(loggedUser == null){
            throw new IllegalStateException("Não existe um usuário logado");
        }
        if(!(loggedUser.getUsuario() instanceof Restaurante)){
            throw new IllegalStateException("O usuário logado não é um restaurante");
        }
        return (Restaurante) loggedUser.getUsuario();
    }
}
