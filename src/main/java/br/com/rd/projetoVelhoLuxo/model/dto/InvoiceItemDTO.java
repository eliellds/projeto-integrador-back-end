package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.Products;
import lombok.Data;

@Data
public class InvoiceItemDTO {
    private InvoiceItemKeyDTO invoiceItemKey;
    private Products productID;
    private Long productQuantity;
    private Double rateICMS;
    private Double calculationICMS;
    private Double rateIPI;
    private Double calculationIPI;
    private Double discountUnity;
    private Double discountTotal;
    private Double total;
}
