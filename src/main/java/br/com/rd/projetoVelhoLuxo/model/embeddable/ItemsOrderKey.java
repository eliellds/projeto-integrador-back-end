package br.com.rd.projetoVelhoLuxo.model.embeddable;

import br.com.rd.projetoVelhoLuxo.model.entity.Order;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class ItemsOrderKey implements Serializable {

    @Column(name = "cl_id_item")
    private Long idItem;

    @ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_pedido", nullable = false)
    private Order order;

}
