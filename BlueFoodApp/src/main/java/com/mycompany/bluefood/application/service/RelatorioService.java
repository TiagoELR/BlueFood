
package com.mycompany.bluefood.application.service;

import com.mycompany.bluefood.domain.pedido.Pedido;
import com.mycompany.bluefood.domain.pedido.PedidoRepository;
import com.mycompany.bluefood.domain.pedido.RelatorioItemFilter;
import com.mycompany.bluefood.domain.pedido.RelatorioItemFaturamento;
import com.mycompany.bluefood.domain.pedido.RelatorioPedidoFilter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RelatorioService {
    
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public List<Pedido> listPedidos(Integer restauranteId, RelatorioPedidoFilter filter){
       
        Integer pedidoId  = filter.getPedidoId();
        
        if(pedidoId != null){
           Pedido pedido = pedidoRepository.findByIdAndRestauranteId(pedidoId, restauranteId);
           return List.of(pedido);
        }
        LocalDate dataInicial = filter.getDataInicial();
        LocalDate dataFinal = filter.getDataFinal();
        
        if(dataInicial == null){
            return List.of();
        }
        if(dataFinal == null){
            dataFinal = LocalDate.now();
        }
        
        return pedidoRepository.findByDataInterval(restauranteId, dataInicial.atStartOfDay(), dataFinal.atTime(23,59,59));
    }
    
    public List<RelatorioItemFaturamento> calcularFaturamentoItens(Integer restauranteId, RelatorioItemFilter filter){
        List<Object[]> itensObj;
        Integer itemId = filter.getItemId();
        LocalDate dataInicial = filter.getDataInicial();
        LocalDate dataFinal = filter.getDataFinal();
        
        if(dataInicial == null){
            return List.of();
        }
        if(dataFinal == null){
            dataFinal = LocalDate.now();
        }
        LocalDateTime dataHoraInicial = dataInicial.atStartOfDay();
	LocalDateTime dataHoraFinal = dataFinal.atTime(23, 59, 59);
        
        if(itemId != 0){
           itensObj = pedidoRepository.findItensForFaturamento(restauranteId, itemId, dataHoraInicial, dataHoraFinal);
        }else{
           itensObj = pedidoRepository.findItensForFaturamento(restauranteId, dataHoraInicial, dataHoraFinal);
        }
        List<RelatorioItemFaturamento> itens = new ArrayList<>();
        
        for(Object[] item : itensObj){
            String nome = (String) item[0];
            Long quantidade = (Long) item[1];
            BigDecimal valor = (BigDecimal) item[2];
            
           itens.add( new RelatorioItemFaturamento(nome, quantidade, valor));
        }
        
        return itens;
    }
}
