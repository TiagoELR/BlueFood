
package com.mycompany.bluefood.application.service;

import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.cliente.ClienteRepository;
import com.mycompany.bluefood.domain.restaurante.ItemCardapio;
import com.mycompany.bluefood.domain.restaurante.ItemCardapioRepository;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import com.mycompany.bluefood.domain.restaurante.RestauranteComparator;
import com.mycompany.bluefood.domain.restaurante.RestauranteRepository;
import com.mycompany.bluefood.domain.restaurante.SearchFilter;
import com.mycompany.bluefood.util.SecurityUtils;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ItemCardapioRepository itemCardapioRepository;
    
    @Autowired
    private ImageService imageService;
    
    @Transactional
    public void saveRestaurante(Restaurante restaurante) throws ValidateException{
        if(!validateEmail(restaurante.getEmail(), restaurante.getId())){
            throw new ValidateException("O e-mail está duplicado");
        }
        
        if(restaurante.getId() != null){
            Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();
            restaurante.setSenha(restauranteDB.getSenha());
            restaurante.setLogotipo(restauranteDB.getLogotipo());
            restauranteRepository.save(restaurante);
        }else{
            restaurante.encryptPassword();
            restaurante = restauranteRepository.save(restaurante);
            restaurante.setLogotipoFileName();
            imageService.uploadLogotipo(restaurante.getLogotipoFile(), restaurante.getLogotipo());
        } 
    }
    
    private boolean validateEmail(String email, Integer id){
        Cliente cliente = clienteRepository.findByEmail(email);
        Restaurante restaurante = restauranteRepository.findByEmail(email);
        if(cliente != null){
            return false;
        }
        if(restaurante != null){
           if(id == null){
               return false;
           } 
           if(!restaurante.getId().equals(id)){
               return false;
           }
        }
        return true;
    }
    
    public List<Restaurante> search(SearchFilter filter){
        List<Restaurante> restaurantes;
        if(filter.getSearchType() == SearchFilter.SearchType.Texto){
          restaurantes =  restauranteRepository.findByNomeIgnoreCaseContaining(filter.getTexto());
        }else if(filter.getSearchType() == SearchFilter.SearchType.Categoria){
            restaurantes =  restauranteRepository.findByCategorias_Id(filter.getCategoriaId());
        }else{
            throw new IllegalStateException("Tipo de busca" + filter.getSearchType() + "não é suprttado");
        }
        
        Iterator<Restaurante> it = restaurantes.iterator();
        
        while(it.hasNext()){
            Restaurante restaurante = it.next();
            double taxaEntrega = restaurante.getTaxaEntrega().doubleValue();
            if(filter.isEntregaGratis() && taxaEntrega > 0 || !filter.isEntregaGratis() && taxaEntrega == 0){
                it.remove();
            }
        }
        RestauranteComparator comparator = new RestauranteComparator(filter, SecurityUtils.loggedCliente().getCep());
        restaurantes.sort(comparator);
        return restaurantes;
    }
    
    @Transactional
    public void saveItemCardapio(ItemCardapio itemCardapio){
        itemCardapio = itemCardapioRepository.save(itemCardapio);
        itemCardapio.setImagemFileName();
        imageService.uploadComida(itemCardapio.getImagemFile(), itemCardapio.getImagem());
    }
}
