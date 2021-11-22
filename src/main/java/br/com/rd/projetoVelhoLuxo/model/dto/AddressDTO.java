package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class  AddressDTO {

    private Long id;

    private String cep;

    private String city;

    private String state;

    private String district;

    private String street;

    private Integer number;

    private String complement;

    private String reference;
}
