package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.embeddable.ItemsOrderKey;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_itens_pedido")
@Data
public class ItemsOrder {

    @EmbeddedId
    private ItemsOrderKey compositeKey;

    @ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_produto", nullable = false)
    private Products product;

    @Column(name = "cl_qtd_produto", nullable = false)
    private Long quantity;
    @Column(name = "cl_desconto_unitario", precision = 8, scale = 4)
    private Double unitDiscount;
    @Column(name = "cl_desconto_total", precision = 10, scale = 4)
    private Double totalDicount;
    @Column(name = "cl_valor_total", nullable = false, precision = 10, scale = 4)
    private Double totalPrice;

}
