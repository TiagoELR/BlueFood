
package com.mycompany.bluefood.domain.pedido;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class RelatorioItemFilter {
    
    private Integer itemId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicial;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFinal;
    
    
}
