
package com.mycompany.bluefood.domain.restaurante;

import com.mycompany.bluefood.domain.usuario.Usuario;
import com.mycompany.bluefood.infrastructure.web.validator.UploadConstraint;
import com.mycompany.bluefood.util.FileType;
import com.mycompany.bluefood.util.StringUtils;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Restaurante extends Usuario{
    
    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "[0-9]{14}", message = "O CNPJ possue formato invalido")
    private String cnpj;
    
    @Size(max = 80)
    private String logotipo;
    
    @UploadConstraint(acceptedTypes = FileType.PNG)
    private transient MultipartFile logotipoFile;
    
    @NotNull(message = "A taxa de entrega não pode ser vazia")
    private BigDecimal taxaEntrega;
    
    @NotNull(message = "A tempo de entrega não pode ser vazio")
    @Min(0)
    @Max(120)
    private Integer tempoEntregaBase;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "restaurante_has_categoria",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
    )
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
    
    @OneToMany(mappedBy = "restaurante")
    private Set<ItemCardapio> itemCardapios = new HashSet<>(0);
    
    public void setLogotipoFileName(){
        if(getId() == null){
            throw new IllegalStateException("Epreciso primeiro gravar o registro");
        }
        this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtencion());
    }
    
    public String getCategoriaAsText(){
        Set<String> strings = new LinkedHashSet<>();
        
        for (CategoriaRestaurante categoria : categorias) {
            strings.add(categoria.getNome());
        }
        return StringUtils.concatenate(strings);
    }
    
    public Integer calcularTempoEntrega(String cep){
        int soma  = 0;
        
        for (char c : cep.toCharArray()) {
            int v = Character.getNumericValue(c);
            if(v > 0){
                soma += v;
            }
        }
        soma /= 2;
        return tempoEntregaBase + soma;
    }
}