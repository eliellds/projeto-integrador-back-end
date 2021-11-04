package br.com.rd.projetoVelhoLuxo.model.embeddable;


import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class InventoryKey implements Serializable {
    @Column(nullable = false, name = "cl_id_store")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "cl_id_product")
    private Products products;
}
