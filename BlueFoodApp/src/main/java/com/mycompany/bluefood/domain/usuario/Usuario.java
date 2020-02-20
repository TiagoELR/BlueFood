
package com.mycompany.bluefood.domain.usuario;

import com.mycompany.bluefood.util.StringUtils;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public class Usuario implements Serializable {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue
    private Integer id;
    
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(max = 80, message = "O nome é muito grande")
    private String nome;
    
    @NotBlank(message = "O e-mail não pode ser vazio")
    @Size(max = 60, message = "O e-mail é muito grande")
    @Email(message = "O e-mail é invalido")
    private String email;
    
    @NotBlank(message = "A senha não pode ser vazia")
    @Size(max = 80, message = "A senha é muito grande")
    private String senha;
    
    @NotBlank(message = "O nome não pode ser vazio")
    @Size(max = 80, message = "O nome é muito grande")
    @Pattern(regexp = "[0-9]{10,11}", message = "O Telefone possue formato invalido")
    private String telefone;
    
    public void encryptPassword(){
        this.senha = StringUtils.encrypt(this.senha);
    }
}
