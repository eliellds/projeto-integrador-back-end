package br.com.rd.projetoVelhoLuxo.model.embeddable;

import br.com.rd.projetoVelhoLuxo.model.entity.Order;
import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CardOrderKey implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_pedido")
    Order order;

}
