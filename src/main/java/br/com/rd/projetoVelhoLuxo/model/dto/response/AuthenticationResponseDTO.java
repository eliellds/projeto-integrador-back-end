package br.com.rd.projetoVelhoLuxo.model.dto.response;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {

    private final String jwt;

    public AuthenticationResponseDTO(String jwt) {
        this.jwt = jwt;
    }

}
