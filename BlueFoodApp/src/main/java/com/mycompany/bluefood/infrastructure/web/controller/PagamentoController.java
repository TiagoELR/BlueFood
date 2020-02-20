
package com.mycompany.bluefood.infrastructure.web.controller;

import com.mycompany.bluefood.application.service.PagamentoException;
import com.mycompany.bluefood.application.service.PedidoService;
import com.mycompany.bluefood.domain.pedido.Carrinho;
import com.mycompany.bluefood.domain.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;



@Controller
@RequestMapping(path = "/cliente/pagamento")
@SessionAttributes("carrinho")
public class PagamentoController {
    
    @Autowired
    private PedidoService pedidoService;
    
    @PostMapping(path = "/pagar")
    public String pagar(
            @RequestParam("numCartao") String numCartao,
            @ModelAttribute("carrinho") Carrinho carrinho,
            SessionStatus sessionStatus,
            Model model){
        
        Pedido pedido;
        try {
            pedido = pedidoService.criarAndPagar(carrinho, numCartao);
            sessionStatus.setComplete();
            return "redirect:/cliente/pedido/view?pedidoId=" + pedido.getId();
        } catch (PagamentoException ex) {
            model.addAttribute("msg", ex.getMessage());
            return "cliente-carrinho";
        }
        
    }
}
