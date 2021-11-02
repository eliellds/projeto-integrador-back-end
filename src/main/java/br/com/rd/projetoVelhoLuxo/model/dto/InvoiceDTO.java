package br.com.rd.projetoVelhoLuxo.model.dto;

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
    private MyUserDTO userId;
    private TipoNfDTO invoiceTypeId;
    private OrderDTO orderId;
//    private Loja lojaid;
}

