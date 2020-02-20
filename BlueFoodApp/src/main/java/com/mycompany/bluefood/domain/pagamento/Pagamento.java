
package com.mycompany.bluefood.domain.pagamento;

import com.mycompany.bluefood.domain.pedido.Pedido;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bluefood_pagamento")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pagamento implements Serializable{
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;
    
    @NotNull
    @OneToOne
    private Pedido pedido;
    
    @NotNull
    private LocalDateTime dataPagamento;
    
    @Column(name = "num_cartao_final")
    @NotNull
    @Size(min = 4, max = 4)
    private String numCartaoFinal;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private BandeiraCartão bandeiraCartao;
    
    public void definirNumeroBandeira(String numCartao){
        numCartaoFinal = numCartao.substring(12);
        bandeiraCartao = getBandeira(numCartao);
    }
    
    private BandeiraCartão getBandeira(String numCartao){
        if(numCartao.startsWith("0000")){
            return BandeiraCartão.AMEX;
        }
        if(numCartao.startsWith("1111")){
            return BandeiraCartão.ELO;
        }
        if(numCartao.startsWith("2222")){
            return BandeiraCartão.MASTER;
        }
            return BandeiraCartão.VISA;
        
    }
}