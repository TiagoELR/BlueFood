
package com.mycompany.bluefood.infrastructure.web.controller;

import com.mycompany.bluefood.domain.restaurante.CategoriaRestaurante;
import com.mycompany.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

public class ControllerHelp {
    public static void setEditMode(Model model, boolean isEdit){
        model.addAttribute("editMode", isEdit);
    }
    
    public static void  addCategoriasToRequest(CategoriaRestauranteRepository repository, Model model){
        
        List<CategoriaRestaurante> categorias = repository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
    }
}
