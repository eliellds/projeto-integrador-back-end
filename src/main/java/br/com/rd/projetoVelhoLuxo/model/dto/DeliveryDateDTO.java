package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class DeliveryDateDTO {

    private Long id;
    private String state;
    private Long addDate;
    private Double deliveryPrice;

}
