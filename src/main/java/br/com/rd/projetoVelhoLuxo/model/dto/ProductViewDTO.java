package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.Sales;
import lombok.Data;

@Data
public class ProductViewDTO {
    private ProductsDTO product;
    private Double price;
    private Double salePrice;
    private Integer qty;
}
