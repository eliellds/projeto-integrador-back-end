package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.Address;
import br.com.rd.projetoVelhoLuxo.model.entity.UserAddress;
import lombok.Data;

@Data
public class UserAddressViewDTO {
    private UserAddressCompositeKeyDTO id;
    private String description;
    private AddressDTO address;
}
