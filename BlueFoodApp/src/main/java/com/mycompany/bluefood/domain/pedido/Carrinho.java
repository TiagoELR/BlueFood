package com.mycompany.bluefood.domain.pedido;

import com.mycompany.bluefood.domain.restaurante.ItemCardapio;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;

@Getter
public class Carrinho implements Serializable{

    public Carrinho() {
    }
    
    

    private List<ItemPedido> itens = new ArrayList<>();

    private Restaurante restaurante;

    public void adicionarItem(ItemCardapio itemCardapio, Integer quantidade, String observacoes) throws RestauranteDiferenteException {

        if (itens.size() == 0) {
            restaurante = itemCardapio.getRestaurante();
        } else if (!itemCardapio.getRestaurante().getId().equals(restaurante.getId())) {
            throw new RestauranteDiferenteException();
        }

        if (!exists(itemCardapio)) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setItemCardapio(itemCardapio);
            itemPedido.setQuantidade(quantidade);
            itemPedido.setObservacao(observacoes);
            itemPedido.setPreco(itemCardapio.getPreco());
            itens.add(itemPedido);
        }

    }

    public void adicionarItem(ItemPedido itemPedido){
        try {
            adicionarItem(itemPedido.getItemCardapio(), itemPedido.getQuantidade(), itemPedido.getObservacao());
        } catch (RestauranteDiferenteException e) {
        }
    }
    
    public void removerItem(ItemCardapio itemCardapio){
        for (Iterator<ItemPedido> iterator = itens.iterator(); iterator.hasNext();) {
            ItemPedido itemPedido = iterator.next();
            if(itemPedido.getItemCardapio().getId().equals(itemCardapio.getId())){
                iterator.remove();
                break;
            }
        }
        if(itens.size() == 0){
            restaurante = null;
        }
    }
    
    private boolean exists(ItemCardapio itemCardapio) {
        for (ItemPedido itenPedido : itens) {
            if (itenPedido.getItemCardapio().getId().equals(itemCardapio.getId())) {
                return true;
            }
        }
        return false;
    }
    
    public BigDecimal getPrecoTotal(boolean adicionarTaxaEntrega){
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemPedido iten : itens) {
           soma = soma.add(iten.getPrecoCalculado());
        }
        if(adicionarTaxaEntrega){
            soma = soma.add(restaurante.getTaxaEntrega());
        }
        return soma;
    }
    
    public void limpar(){
        itens.clear();
        restaurante = null;
    }
    public boolean vazio(){
        return itens.size() == 0;
    }
}
