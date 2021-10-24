package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.embeddable.PriceProductKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity (name = "tb_preco_produto")
public class PriceProduct {

    @EmbeddedId
    private PriceProductKey priceProductKey;

    @Column(nullable = false, name = "cl_preco")
    private Double price;

    @Column(nullable = true, name = "cl_preco_promocional")
    private Double salePrice;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "id_promocao")
    private Sales sales;

}
