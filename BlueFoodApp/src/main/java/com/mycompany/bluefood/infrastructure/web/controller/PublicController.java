package com.mycompany.bluefood.infrastructure.web.controller;

import com.mycompany.bluefood.application.service.ClienteService;
import com.mycompany.bluefood.application.service.RestauranteService;
import com.mycompany.bluefood.application.service.ValidateException;
import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private RestauranteService restauranteService;
    
    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @GetMapping("/cliente/new")
    public String newCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        ControllerHelp.setEditMode(model, false);
        return "cliente-cadastro";
    }
    @GetMapping("/restaurante/new")
    public String newRestaurante(Model model) {
        model.addAttribute("restaurante", new Restaurante());
        ControllerHelp.setEditMode(model, false);
        ControllerHelp.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }

    @PostMapping(path = "/cliente/save")
    public String saveCliente(@ModelAttribute("cliente") @Valid Cliente cliente,
            Errors error, Model model) {
        if (!error.hasErrors()) {
            try {
                clienteService.saveCliente(cliente);
                model.addAttribute("msg", "Cliente gravado com sucésso");
            } catch (ValidateException e) {
                error.rejectValue("email", null, e.getMessage());
            }
        }
        ControllerHelp.setEditMode(model, false);
        return "cliente-cadastro";
    }
    
    @PostMapping(path = "/restaurante/save")
    public String saveRestaurante(@ModelAttribute("restaurante") @Valid Restaurante restaurante,
            Errors error, Model model) {
        if (!error.hasErrors()) {
            try {
                restauranteService.saveRestaurante(restaurante);
                model.addAttribute("msg", "Restaurante gravado com sucésso");
            } catch (ValidateException e) {
                error.rejectValue("email", null, e.getMessage());
            }
        }
        ControllerHelp.setEditMode(model, false);
        ControllerHelp.addCategoriasToRequest(categoriaRestauranteRepository, model);
        return "restaurante-cadastro";
    }
}
