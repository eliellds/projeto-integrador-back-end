package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class InventoryDTO {
    private InventoryKeyDTO inventoryKey;
    private Integer qty_products;
}
