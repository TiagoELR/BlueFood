package com.mycompany.bluefood.domain.pedido;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    public List<Pedido> findByCliente_Id(Integer clienteId);

    public List<Pedido> findByRestaurante_IdOrderByDataDesc(Integer restauranteId);

    public Pedido findByIdAndRestauranteId(Integer pedidoId, Integer restauranteId);

    @Query("SELECT p FROM Pedido p WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3")
    public List<Pedido> findByDataInterval(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT i.itemCardapio.nome, COUNT(i.itemCardapio.id), SUM(i.preco) FROM Pedido p INNER JOIN p.itens i "
            + "WHERE p.restaurante.id = ?1 AND i.itemCardapio.id = ?2 AND p.data BETWEEN ?3 AND ?4 GROUP BY i.itemCardapio.nome")
    public List<Object[]> findItensForFaturamento(Integer restauranteId, Integer itemCardapioId, LocalDateTime dataInicial, LocalDateTime dataFinal);

    @Query("SELECT i.itemCardapio.nome, SUM(i.quantidade), SUM(i.preco * i.quantidade) FROM Pedido p INNER JOIN p.itens i "
            + "WHERE p.restaurante.id = ?1 AND p.data BETWEEN ?2 AND ?3 GROUP BY i.itemCardapio.nome")
    public List<Object[]> findItensForFaturamento(Integer restauranteId, LocalDateTime dataInicial, LocalDateTime dataFinal);

}
