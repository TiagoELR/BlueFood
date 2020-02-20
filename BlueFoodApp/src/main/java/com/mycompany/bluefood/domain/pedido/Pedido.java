
package com.mycompany.bluefood.domain.pedido;

import com.mycompany.bluefood.domain.cliente.Cliente;
import com.mycompany.bluefood.domain.pagamento.Pagamento;
import com.mycompany.bluefood.domain.restaurante.Restaurante;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido_bluefood")
public class Pedido implements Serializable{
    
    public enum Status{
    
        Producao(1, "Em produção", false),
        Entrega(2, "Saiu para entrega", false),
        Concluido(3, "Concluído", true);
        
        int ordem;
        String descricao;
        boolean ultimo;

        private Status(int ordem, String descricao, boolean ultimo) {
            this.ordem = ordem;
            this.descricao = descricao;
            this.ultimo = ultimo;
        }

        public int getOrdem() {
            return ordem;
        }

        public String getDescricao() {
            return descricao;
        }

        public boolean isUltimo() {
            return ultimo;
        }
        
        public static Status fromOrdem(int ordem){
            for(Status status : Status.values()){
                if(status.getOrdem() == ordem){
                    return status;
                }
            }
            return null;
        }
        
}
    
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotNull
    @Column(name = "data_pedido")
    private LocalDateTime data;
    
    @NotNull
    private Status status;
    
    @NotNull
    @ManyToOne
    private Cliente cliente;
    
    @NotNull
    @ManyToOne
    private Restaurante restaurante;
    
    @NotNull
    private BigDecimal subtotal;
    
    @NotNull
    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;
    
    @NotNull
    private BigDecimal total;
    
    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
    private Set<ItemPedido> itens;
    
    @OneToOne(mappedBy = "pedido")
    private Pagamento pagamento;
    
    public String getFormattedId(){
        return String.format("#%04d", id);
    }
    
    public void definirProximoStatus(){
        int ordem = status.getOrdem();
        
        Status newStatus = Status.fromOrdem(ordem + 1);
        if(newStatus != null){
            this.status = newStatus;
        }
    }
}
