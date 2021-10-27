package br.com.rd.projetoVelhoLuxo.model.embeddable;

import br.com.rd.projetoVelhoLuxo.model.entity.Invoice;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Embeddable
public class InvoiceItemKey implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "cl_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_NF")
    private Invoice invoiceId;

}
