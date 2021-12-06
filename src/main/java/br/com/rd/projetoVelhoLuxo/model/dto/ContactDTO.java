package br.com.rd.projetoVelhoLuxo.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
public class ContactDTO {

    private Long id;
    private String name;
    private SubjectDTO subject;
    private String phoneNumber;
    private String email;
    private String content;
    private LocalDateTime contactDate;
    private LocalDateTime replyDate;
    private ContactStatusDTO status;

}
