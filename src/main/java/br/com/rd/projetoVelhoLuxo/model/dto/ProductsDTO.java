package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class ProductsDTO {
    private Long id;
    private String product;
    private ConservationStateDTO conservationState;
    private String description;
    private String feature;
    private String year;
    private CategoryDTO categoryDTO;
    private Integer quantity;
}
