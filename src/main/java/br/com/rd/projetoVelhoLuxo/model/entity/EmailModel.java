package br.com.rd.projetoVelhoLuxo.model.entity;


import br.com.rd.projetoVelhoLuxo.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_email")
public class EmailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long emailId;

    @Column(name = "cl_referencia")
    private Long ownerRef;

    @Column(name = "cl_email_de")
    private String emailFrom;

    @Column(name = "cl_email_para")
    private String emailTo;

    @Column(name = "assunto")
    private String subject;

    @Column(columnDefinition = "TEXT", name="cl_mensagem")
    private String text;

    @Column(name = "cl_data_envio")
    private LocalDateTime sendDateEmail;

    @Column(name = "cl_status")
    private StatusEmail statusEmail;
}
