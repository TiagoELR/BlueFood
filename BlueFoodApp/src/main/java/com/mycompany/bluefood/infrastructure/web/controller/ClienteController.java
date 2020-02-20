
package com.mycompany.bluefood.infrastructure.web.controller;

import com.mycompany.bluefood.application.service.ClienteService;
import com.mycompany.bluefood.application.service.RestauranteService;
import com.mycompany.bluefood.application.service.ValidateException;
import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.cliente.ClienteRepository;
import com.mycompany.bluefood.domain.pedido.Pedido;
import com.mycompany.bluefood.domain.pedido.PedidoRepository;
import com.mycompany.bluefood.domain.restaurante.CategoriaRestaurante;
import com.mycompany.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.mycompany.bluefood.domain.restaurante.ItemCardapio;
import com.mycompany.bluefood.domain.restaurante.ItemCardapioRepository;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import com.mycompany.bluefood.domain.restaurante.RestauranteRepository;
import com.mycompany.bluefood.domain.restaurante.SearchFilter;
import com.mycompany.bluefood.util.SecurityUtils;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private ItemCardapioRepository itemCardapioRepository;
    
    @Autowired
    private RestauranteService restauranteService;
    
    @Autowired
    private ClienteService  clienteService;
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @GetMapping(path = "/home")
    public String home(Model model){
        
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        model.addAttribute("searchFilter", new SearchFilter());
        
        List<Pedido> pedidos = pedidoRepository.findByCliente_Id(SecurityUtils.loggedCliente().getId());
        model.addAttribute("pedidos", pedidos);
        
        return "cliente-home";
    }
    
    @GetMapping(path = "/edit")
    public String edit(Model model){
        Integer clenteId = SecurityUtils.loggedCliente().getId();
        Cliente cliente = clienteRepository.findById(clenteId).orElseThrow();
        model.addAttribute("cliente", cliente);
        ControllerHelp.setEditMode(model, true);
        
        return "cliente-cadastro";
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute("cliente") @Valid Cliente cliente,
            Errors error, Model model){ 
        if (!error.hasErrors()) {
            try {
                clienteService.saveCliente(cliente);
                model.addAttribute("msg", "Cliente gravado com sucésso");
            } catch (ValidateException e) {
                error.rejectValue("email", null, e.getMessage());
            }
        }
        ControllerHelp.setEditMode(model, true);
        return "cliente-cadastro";
    }
    
    @GetMapping(path = "/search")
    public String search(@ModelAttribute("searchFilter") SearchFilter filter,
            Model model,
            @RequestParam(value = "cmd",required = false) String command){
        
        filter.processFilter(command);
        List<Restaurante> restaurantes = restauranteService.search(filter);
        model.addAttribute("restaurantes", restaurantes);
        ControllerHelp.addCategoriasToRequest(categoriaRestauranteRepository, model);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
        model.addAttribute("searchFilter", filter);
        return "cliente-busca";
    }
    
    @GetMapping(path = "/restaurante")
    public String viewRestaurante(@RequestParam("restauranteId") Integer restauranteId,
            @RequestParam(value = "categoria", required = false) String categoria,Model model){
        
        Restaurante restaurante = restauranteRepository.findById(restauranteId).orElseThrow();
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());
        List<String> categorias = itemCardapioRepository.findCategoria(restauranteId);
        model.addAttribute("categorias", categorias);
        
        List<ItemCardapio> itemCardapioDestaque;
        List<ItemCardapio> itemCardapioNaoDestaque;
        
        if(categoria == null){
            itemCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, true);
            itemCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueOrderByNome(restauranteId, false);
        }else{
           itemCardapioDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, true, categoria);
            itemCardapioNaoDestaque = itemCardapioRepository.findByRestaurante_IdAndDestaqueAndCategoriaOrderByNome(restauranteId, false, categoria);
        }
        
        model.addAttribute("itemCardapioDestaque",itemCardapioDestaque);
        model.addAttribute("itemCardapioNaoDestaque",itemCardapioNaoDestaque);
        model.addAttribute("categoriaSelecionada",categoria);
        return "cliente-restaurante";
    }
}
