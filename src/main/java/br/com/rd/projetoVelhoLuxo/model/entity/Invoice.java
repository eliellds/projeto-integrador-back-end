package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "tb_nota_fiscal")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_nr_nf", length = 10)
    private String invoiceNumber;

    @Column(nullable = false, name = "cl_nr_chave_acesso", length = 35)
    private String accessKeyNumber;

    @Column(nullable = false, name = "cl_frete")
    private Double shipping;

    @Column(nullable = false, name = "cl_data_emissao")
    private LocalDate issueDate;

    @Column(nullable = false, name = "cl_flag_nota_estadual")
    private Boolean stateInvoice;

    @Column(name = "cl_desconto_total")
    private Double totalDiscount;

    @Column(name = "cl_icms_total")
    private Double totalICMS;

    @Column(name = "cl_ipi_total")
    private Double totalIPI;

    @Column(nullable = false, name = "cl_valor_total")
    private Double totalPrice;

    //    ID_USUARIO (FK) INT NOT NULL
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_user")
    private MyUser userId;

//    ID_TIPO_NF (FK) TINY INT NOT NULL
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_tipo_nf")
    private TipoNf invoiceTypeId;

////    ID_PEDIDO (UK, FK) INT NOT NULL
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false, name = "id_pedido")
//    private Pedido salesId;

//    ID_LOJA (FK) TINY INT NOT NULL
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(nullable = true, name = "id_loja")
//    private Store storeId;


}
