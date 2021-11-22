package br.com.rd.projetoVelhoLuxo.model.dto.response;

import lombok.Data;

@Data
public class UserHeaderDTO {

    private final Long id;
    private final String name;
    private final String lastname;
    private final String cpf;
    private final String email;
    private final String telephone;

    public UserHeaderDTO(Long id, String name, String lastname, String cpf, String email, String telephone) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.cpf = cpf;
        this.email = email;
        this.telephone = telephone;
    }

}
