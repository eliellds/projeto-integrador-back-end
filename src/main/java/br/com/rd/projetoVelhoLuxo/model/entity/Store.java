package br.com.rd.projetoVelhoLuxo.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "tb_loja")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "cl_nm_loja", length = 100)
    private String name;

    @Column(nullable = false, name = "cl_cnpj", length = 30)
    private String cnpj;

    @Column(nullable = false, name = "cl_inscricao_estadual", length = 12)
    private String stateRegistration;

    @Column(nullable = false, name = "cl_email", length = 100)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_telefone")
    private Telephone telephoneID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_endereco")
    private Address addressID;

}
