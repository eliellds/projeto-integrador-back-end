package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.Sales;
import lombok.Data;

@Data
public class PriceProductDTO {
    private PriceProductKeyDTO priceProductKey;
    private Double price;
    private Double salePrice;
    private Sales sales;
}