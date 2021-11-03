package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class ItemsOrderDTO {

    private ItemsOrderKeyDTO compositeKey;
    private ProductsDTO productsDTO;
    private Long quantity;
    private Double unityDiscount;
    private Double totalDiscount;
    private Double totalPrice;

}
