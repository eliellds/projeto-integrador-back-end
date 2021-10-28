package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data

public class PaymentMethodsDTO {
    private Long id;
    private String description;
    private String installments;
}
