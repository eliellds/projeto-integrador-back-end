package br.com.rd.projetoVelhoLuxo.model.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {

    private final String jwt;
    private final String email;

    public AuthenticationResponseDTO(String jwt, String email) {
        this.jwt = jwt;
        this.email = email;
    }

}
