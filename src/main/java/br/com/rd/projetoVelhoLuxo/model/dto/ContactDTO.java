package br.com.rd.projetoVelhoLuxo.model.dto;

import br.com.rd.projetoVelhoLuxo.model.entity.ContactStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactDTO {

    private String phoneNumber;
    private String email;
    private String content;
    private LocalDateTime contactDate;
    private LocalDateTime replyDate;
    private ContactStatus status;

}
