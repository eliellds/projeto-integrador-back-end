package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

import javax.persistence.Column;
@Data
public class UserAddressCompositeKeyDTO {
    private Long idUser;

    private Long idAddress;
}
