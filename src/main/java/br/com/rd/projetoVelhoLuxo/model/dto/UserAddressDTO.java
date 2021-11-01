package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class UserAddressDTO {
    private UserAddressCompositeKeyDTO id;
    private String description;
    private AddressDTO address;
}
