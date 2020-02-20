
package com.mycompany.bluefood.infrastructure.web.controller;

import com.mycompany.bluefood.domain.pedido.Pedido;
import com.mycompany.bluefood.domain.pedido.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/cliente/pedido")
public class PedidoController {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    @GetMapping(path = "/view")
    private String viewPedido( @RequestParam("pedidoId") Integer pedidoId, Model model){
        
        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
        model.addAttribute("pedido", pedido);
        return "cliente-pedido";
    }
    
}
