package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_contato")
@Data
public class Contact {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_assunto", nullable = false)
    private Subject subject;

    @Column(name = "cl_nr_telefone", length = 11)
    private String phoneNumber;
    @Column(nullable = false, name = "cl_email", length = 100)
    private String email;
    @Column(nullable = false, name = "cl_conteudo", length = 300)
    private String content;
    @Column(nullable = false, name = "cl_data_contato")
    private LocalDateTime contactDate;
    @Column(name = "cl_data_resposta")
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_status", nullable = false)
    private ContactStatus status;

}
