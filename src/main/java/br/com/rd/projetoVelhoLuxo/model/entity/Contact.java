package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_contact")
@Data
public class Contact {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_subject", nullable = false)
    private Subject subject;

    @Column(name = "cl_phone_number", length = 11)
    private String phoneNumber;
    @Column(nullable = false, name = "cl_email", length = 100)
    private String email;
    @Column(nullable = false, name = "cl_content", length = 300)
    private String content;
    @Column(nullable = false, name = "cl_contact_date")
    private LocalDateTime contactDate;
    @Column(name = "cl_reply_date")
    private LocalDateTime replyDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cl_id_status", nullable = false)
    private ContactStatus status;

}
