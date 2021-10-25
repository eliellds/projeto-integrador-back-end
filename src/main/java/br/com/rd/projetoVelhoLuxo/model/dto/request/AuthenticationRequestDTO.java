package br.com.rd.projetoVelhoLuxo.model.dto.request;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {

    private String username;
    private String password;

}
