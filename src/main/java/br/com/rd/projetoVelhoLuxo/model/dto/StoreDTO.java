package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class StoreDTO {

    private Long id;
    private String name;
    private String cnpj;
    private String stateRegistration;
    private String email;
    private TelephoneDTO telephoneID;
    private AddressDTO addressID;

}
