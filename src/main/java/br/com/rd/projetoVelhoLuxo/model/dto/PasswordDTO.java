package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    private Long myUserId;
    private String currentPassword;
    private String password;
}
