package br.com.rd.projetoVelhoLuxo.model.entity;

import br.com.rd.projetoVelhoLuxo.model.embeddable.InvoiceItemKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name ="tb_itens_nf")
public class InvoiceItem {

    @EmbeddedId
    private InvoiceItemKey invoiceItemKey;

    @Column(nullable = false, name = "cl_qtd_produto")
    private Long productQuantity;

    @Column(name = "cl_aliquota_icms")
    private Double rateICMS;

    @Column(name = "cl_calculo_icms")
    private Double calculationICMS;

    @Column(name = "cl_aliquota_ipi")
    private Double rateIPI;

    @Column(name = "cl_calculo_ipi")
    private Double calculationIPI;

    @Column(name = "cl_desconto_unitario")
    private Double discountUnity;

    @Column(name = "cl_desconto_total")
    private Double discountTotal;

    @Column(nullable = false, name = "cl_valor_total")
    private Double total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    private Products productID;

}
