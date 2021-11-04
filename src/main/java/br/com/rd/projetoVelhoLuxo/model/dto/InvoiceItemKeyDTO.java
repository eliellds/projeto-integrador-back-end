package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class InvoiceItemKeyDTO {
    private Long id;
    private InvoiceDTO invoiceId;
}
