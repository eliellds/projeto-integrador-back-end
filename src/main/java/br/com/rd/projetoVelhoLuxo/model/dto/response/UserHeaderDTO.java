package br.com.rd.projetoVelhoLuxo.model.dto.response;

import lombok.Data;

@Data
public class UserHeaderDTO {

    private final Long id;
    private final String name;
    private final String lastname;
    private final String email;

    public UserHeaderDTO(Long id, String name, String lastname, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
    }

}
