
package com.mycompany.bluefood.application.service;

import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.cliente.ClienteRepository;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import com.mycompany.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Transactional
    public void saveCliente(Cliente cliente) throws ValidateException{
        if(!validateEmail(cliente.getEmail(), cliente.getId())){
            throw new ValidateException("O e-mail está duplicado");
        }
        if(cliente.getId() != null){
            Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow();
            cliente.setSenha(clienteDB.getSenha());
        }else{
            cliente.encryptPassword();
        }
        clienteRepository.save(cliente);
    }
    
    private boolean validateEmail(String email, Integer id){
        Restaurante restaurante = restauranteRepository.findByEmail(email);
        Cliente cliente = clienteRepository.findByEmail(email);
        if(restaurante != null){
            return false;
        }
        if(cliente != null){
           if(id == null){
               return false;
           } 
           if(!cliente.getId().equals(id)){
               return false;
           }
        }
        return true;
    }
}
