package br.com.rd.projetoVelhoLuxo.model.entity;


import br.com.rd.projetoVelhoLuxo.model.embeddable.InventoryKey;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity(name = "tb_estoque")
@Data
public class Inventory {
    @EmbeddedId
    private InventoryKey inventoryKey;
    @Column(name = "cl_qty_products")
    private Integer qty_products;

}
