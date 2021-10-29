package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.MyUser;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private String accessKeyNumber;
    private Double shipping;
    private LocalDate issueDate;
    private Boolean stateInvoice;
    private Double totalDiscount;
    private Double totalICMS;
    private Double totalIPI;
    private Double totalPrice;
    private UserDTO userId;
//    private tipoNF invoiceTypeId;
//    private Pedido salesId;
//    private Loja lojaid;
}

