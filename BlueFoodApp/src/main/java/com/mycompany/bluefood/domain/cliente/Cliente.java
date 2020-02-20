
package com.mycompany.bluefood.domain.cliente;

import com.mycompany.bluefood.domain.usuario.Usuario;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "clienteblue")
public class Cliente extends Usuario {
    
    @NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{10,11}", message = "O CPF possue formato invalido")
    private String cpf;
    
    @NotBlank(message = "O CEP não pode ser vazio")
    @Pattern(regexp = "[0-9]{8}", message = "O CEP possue formato invalido")
    private String cep;
    
    public String getFormattedCep(){
        return cep.substring(0, 5) + "-" + cep.substring(5);
    }
}
