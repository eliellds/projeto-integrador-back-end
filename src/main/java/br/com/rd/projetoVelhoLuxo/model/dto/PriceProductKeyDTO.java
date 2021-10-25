package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PriceProductKeyDTO {
    private ProductsDTO product;
    private LocalDate effectiveDate;
}
