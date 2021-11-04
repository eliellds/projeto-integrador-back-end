package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.embeddable.CardOrderKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "tb_pedido_cartao")
public class CardOrder {

    @EmbeddedId
    CardOrderKey compositeKey;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_nr_cartao")
    Card card;

}
