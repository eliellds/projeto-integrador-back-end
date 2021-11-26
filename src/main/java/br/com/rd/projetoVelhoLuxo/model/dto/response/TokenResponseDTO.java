package br.com.rd.projetoVelhoLuxo.model.dto.response;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private final Long expiration;

    public TokenResponseDTO(Long expiration) {
        this.expiration = expiration;
    }

}
