package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "tb_status_contato")
@Data
public class ContactStatus {

    @Id
    @Column(name = "cl_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    @Column(nullable = false, name = "cl_descricao_contato", length = 50)
    private String contactDescription;

}
